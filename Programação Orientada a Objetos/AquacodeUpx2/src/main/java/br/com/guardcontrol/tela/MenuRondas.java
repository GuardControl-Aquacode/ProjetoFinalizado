package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.*;
import java.util.Scanner;

/** Ronda pertence a uma empresa indiretamente pelo local (ronda -> local -> cliente). */
public class MenuRondas {
    private final Scanner sc;
    private final Cliente empresa;
    private final RondaDao dao = new RondaDao();
    private final VigilanteDao vigilantes = new VigilanteDao();
    private final LocalDao locais = new LocalDao();

    public MenuRondas(Scanner sc, Cliente empresa) { this.sc = sc; this.empresa = empresa; }
    private boolean pertence(Ronda r) {
        if (r == null) return false;
        Local l = locais.consultar(r.getIdLocal());
        return l != null && empresa.getIdCliente().equals(l.getIdCliente());
    }
    public void exibir() {
        while (true) {
            System.out.println("\n=== RONDAS ===");
            System.out.println("1 - Listar | 2 - Buscar | 3 - Cadastrar | 4 - Alterar status | 5 - Excluir | 0 - Voltar");
            try {
                switch (Entrada.texto(sc, "Escolha")) {
                    case "1": listar(); break;
                    case "2": buscar(); break;
                    case "3": cadastrar(); break;
                    case "4": alterar(); break;
                    case "5": excluir(); break;
                    case "0": return;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }
    private void listar() {
        boolean achou = false;
        for (Ronda r : dao.consultar()) if (pertence(r)) { achou = true; System.out.println(r); }
        if (!achou) System.out.println("Nenhuma ronda desta empresa.");
    }
    private Ronda localizar() {
        Ronda r = dao.consultar(Entrada.inteiro(sc, "ID da ronda"));
        if (!pertence(r)) { System.out.println("Ronda nao encontrada nesta empresa."); return null; }
        return r;
    }
    private void buscar() { Ronda r = localizar(); if (r != null) System.out.println(r); }
    private void cadastrar() {
        Ronda r = new Ronda();
        r.setCpfVigilante(Entrada.somenteDigitos(sc, "CPF do vigilante", 11));
        if (vigilantes.consultar(r.getCpfVigilante()) == null)
            throw new IllegalArgumentException("Vigilante não cadastrado.");
        for (Local l : locais.consultar()) if (empresa.getIdCliente().equals(l.getIdCliente()))
            System.out.println(l.getIdLocal() + " - " + l.getNomeLocal());
        r.setIdLocal(Entrada.inteiro(sc, "ID do local desta empresa"));
        if (!pertence(r)) throw new IllegalArgumentException("Local não pertence a esta empresa.");
        r.setStatus("EM_ANDAMENTO");
        r.setDataHoraInicio(Agora.brasil());
        dao.cadastrar(r);
        System.out.println("Ronda iniciada: " + r.getIdRonda());
    }
    private void alterar() {
        Ronda r = localizar(); if (r == null) return;
        r.setStatus(Entrada.enumAlterar(sc, "Status", r.getStatus(), "EM_ANDAMENTO", "FINALIZADA", "CANCELADA"));
        if ("EM_ANDAMENTO".equals(r.getStatus())) r.setDataHoraFim(null);
        else if (r.getDataHoraFim() == null) r.setDataHoraFim(Agora.brasil());
        System.out.println(dao.alterar(r, r.getIdRonda()) ? "Ronda atualizada." : "Nenhuma alteracao.");
    }
    private void excluir() {
        Ronda r = localizar();
        if (r != null && Entrada.confirmar(sc, "Confirmar exclusao?"))
            System.out.println(dao.excluir(r.getIdRonda()) ? "Ronda excluida." : "Nenhuma exclusao.");
    }
}
