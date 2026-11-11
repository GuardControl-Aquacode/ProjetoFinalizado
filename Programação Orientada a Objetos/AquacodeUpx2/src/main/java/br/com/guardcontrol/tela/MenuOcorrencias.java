package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.*;
import java.util.Scanner;

/** Ocorrências com ronda podem ser vinculadas à empresa via ronda -> local. */
public class MenuOcorrencias {
    private final Scanner sc;
    private final Cliente empresa;
    private final OcorrenciaDao dao = new OcorrenciaDao();
    private final RondaDao rondas = new RondaDao();
    private final LocalDao locais = new LocalDao();

    public MenuOcorrencias(Scanner sc, Cliente empresa) { this.sc = sc; this.empresa = empresa; }
    private boolean rondaDaEmpresa(Integer id) {
        if (id == null) return false;
        Ronda r = rondas.consultar(id);
        if (r == null) return false;
        Local l = locais.consultar(r.getIdLocal());
        return l != null && empresa.getIdCliente().equals(l.getIdCliente());
    }
    private boolean pertence(Ocorrencia o) { return o != null && rondaDaEmpresa(o.getIdRonda()); }
    public void exibir() {
        System.out.println("Ocorrencias sem ronda não são exibidas aqui.");
        while (true) {
            System.out.println("\n=== OCORRÊNCIAS ===");
            System.out.println("1 - Listar | 2 - Buscar | 3 - Cadastrar | 4 - Alterar | 5 - Excluir | 0 - Voltar");
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
        for (Ocorrencia o : dao.consultar()) if (pertence(o)) { achou = true; System.out.println(o); }
        if (!achou) System.out.println("Nenhuma ocorrencia vinculada a rondas desta empresa.");
    }
    private Ocorrencia localizar() {
        Ocorrencia o = dao.consultar(Entrada.inteiro(sc, "ID da ocorrência"));
        if (!pertence(o)) { System.out.println("Ocorrencia não localizada em ronda desta empresa."); return null; }
        return o;
    }
    private void buscar() { Ocorrencia o = localizar(); if (o != null) System.out.println(o); }
    private void cadastrar() {
        Ocorrencia o = new Ocorrencia();
        o.setIdRonda(Entrada.inteiro(sc, "ID da ronda desta empresa"));
        if (!rondaDaEmpresa(o.getIdRonda())) throw new IllegalArgumentException("Ronda invalida para esta empresa.");
        o.setTitulo(Entrada.obrigatorio(sc, "Titulo"));
        o.setDescricao(Entrada.obrigatorio(sc, "Descricao"));
        o.setTipoOcorrencia(Entrada.texto(sc, "Tipo (opcional)"));
        o.setUrlFoto(Entrada.texto(sc, "URL da foto (opcional)"));
        o.setDataHora(Agora.brasil());
        dao.cadastrar(o);
        System.out.println("Ocorrencia cadastrada: " + o.getIdOcorrencia());
    }
    private void alterar() {
        Ocorrencia o = localizar(); if (o == null) return;
        o.setTitulo(Entrada.alterar(sc, "Titulo", o.getTitulo(), false));
        o.setDescricao(Entrada.alterar(sc, "Descricao", o.getDescricao(), true));
        o.setTipoOcorrencia(Entrada.alterar(sc, "Tipo", o.getTipoOcorrencia(), false));
        o.setUrlFoto(Entrada.alterar(sc, "URL da foto", o.getUrlFoto(), false));
        System.out.println(dao.alterar(o, o.getIdOcorrencia()) ? "Ocorrência atualizada." : "Nenhuma alteração.");
    }
    private void excluir() {
        Ocorrencia o = localizar();
        if (o != null && Entrada.confirmar(sc, "Confirmar exclusão?"))
            System.out.println(dao.excluir(o.getIdOcorrencia()) ? "Ocorrência excluída." : "Nenhuma exclusão.");
    }
}
