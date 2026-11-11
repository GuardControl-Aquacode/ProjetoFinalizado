package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.Entrada;
import java.util.Scanner;

/** Painel da empresa selecionada; sem senha nem autorização real. */
public class MenuGestor {
    private final Scanner sc;
    private final Cliente empresa;
    private final ClienteDao clientes = new ClienteDao();

    public MenuGestor(Scanner sc, Cliente empresa) { this.sc = sc; this.empresa = empresa; }

    public void exibir() {
        System.out.println("\nEmpresa selecionada: " + empresa.getNome() + " (demonstracao sem login)");
        while (true) {
            System.out.println("\n=== Guard Control - TELA DA EMPRESA ===");
            System.out.println("1 - Resumo | 2 - Vigilantes | 3 - Locais | 4 - Gestores");
            System.out.println("5 - Rondas | 6 - Ocorrencias | 7 - Dados da empresa | 0 - Voltar");
            try {
                switch (Entrada.texto(sc, "Escolha")) {
                    case "1": resumo(); break;
                    case "2": new MenuVigilantes(sc).exibir(); break;
                    case "3": new MenuLocais(sc, empresa).exibir(); break;
                    case "4": new MenuGestores(sc, empresa).exibir(); break;
                    case "5": new MenuRondas(sc, empresa).exibir(); break;
                    case "6": new MenuOcorrencias(sc, empresa).exibir(); break;
                    case "7": if (dadosEmpresa()) return; break;
                    case "0": return;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (IllegalStateException | IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    private void resumo() {
        LocalDao locais = new LocalDao();
        RondaDao rondas = new RondaDao();
        OcorrenciaDao ocorrencias = new OcorrenciaDao();
        int quantLocais = 0, quantRondas = 0, quantOcorrencias = 0;
        for (Local l : locais.consultar()) if (empresa.getIdCliente().equals(l.getIdCliente())) quantLocais++;
        for (Ronda r : rondas.consultar()) {
            Local l = locais.consultar(r.getIdLocal());
            if (l != null && empresa.getIdCliente().equals(l.getIdCliente())) quantRondas++;
        }
        for (Ocorrencia o : ocorrencias.consultar()) {
            if (o.getIdRonda() == null) continue;
            Ronda r = rondas.consultar(o.getIdRonda());
            if (r == null) continue;
            Local l = locais.consultar(r.getIdLocal());
            if (l != null && empresa.getIdCliente().equals(l.getIdCliente())) quantOcorrencias++;
        }
        System.out.println("Empresa: " + empresa.getNome());
        System.out.println("Locais: " + quantLocais + " | Rondas: " + quantRondas + " | Ocorrencias com ronda: " + quantOcorrencias);
        System.out.println("Vigilantes não possuem empresa vinculada diretamente no DER.");
    }

    private boolean dadosEmpresa() {
        System.out.println("1 - Ver | 2 - Alterar | 3 - Excluir | 0 - Voltar");
        switch (Entrada.texto(sc, "Escolha")) {
            case "1": System.out.println(empresa); break;
            case "2":
                empresa.setNome(Entrada.alterar(sc, "Nome", empresa.getNome(), true));
                empresa.setTelefone(Entrada.alterar(sc, "Telefone", empresa.getTelefone(), true));
                empresa.setEmail(Entrada.alterar(sc, "E-mail", empresa.getEmail(), false));
                clientes.alterar(empresa, empresa.getIdCliente());
                System.out.println("Empresa atualizada."); break;
            case "3":
                System.out.println("Exclusao somente quando nao ha registros vinculados.");
                if (Entrada.confirmar(sc, "Confirmar exclusao da empresa?")) {
                    if (clientes.excluir(empresa.getIdCliente())) { System.out.println("Empresa excluida."); return true; }
                } break;
            default: break;
        }
        return false;
    }
}
