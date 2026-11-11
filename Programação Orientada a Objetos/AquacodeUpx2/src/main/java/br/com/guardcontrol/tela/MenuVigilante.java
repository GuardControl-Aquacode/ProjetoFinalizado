package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.*;
import java.util.Scanner;

/** Demonstração por seleção de matrícula (sem autenticação). */
public class MenuVigilante {
    private final Scanner sc;
    private final Vigilante vigilante;
    private final RondaDao rondas = new RondaDao();
    private final LocalDao locais = new LocalDao();
    private final OcorrenciaDao ocorrencias = new OcorrenciaDao();

    public MenuVigilante(Scanner sc, Vigilante v) { this.sc = sc; this.vigilante = v; }
    private boolean minha(Ronda r) { return r != null && vigilante.getCpf().equals(r.getCpfVigilante()); }
    private Ronda ativa() {
        for (Ronda r : rondas.consultar())
            if (minha(r) && "EM_ANDAMENTO".equals(r.getStatus())) return r;
        return null;
    }
    public void exibir() {
        System.out.println("\nVigilante selecionado: " + vigilante.getNomeCompleto());
        while (true) {
            System.out.println("\n=== PAINEL DO VIGILANTE ===");
            System.out.println("1 - Iniciar ronda | 2 - Finalizar ronda | 3 - Registrar ocorrência | 4 - Minhas rondas | 5 - Ocorrências das minhas rondas | 0 - Voltar");
            try {
                switch (Entrada.texto(sc, "Escolha")) {
                    case "1": iniciar(); break;
                    case "2": finalizar(); break;
                    case "3": registrar(); break;
                    case "4": historicoRondas(); break;
                    case "5": historicoOcorrencias(); break;
                    case "0": return;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (IllegalStateException | IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }
    private void iniciar() {
        if (ativa() != null) { System.out.println("Ja existe uma ronda em andamento."); return; }
        System.out.println("Locais cadastrados:");
        for (Local l : locais.consultar()) System.out.println(l.getIdLocal() + " - " + l.getNomeLocal());
        int id = Entrada.inteiro(sc, "ID do local");
        if (locais.consultar(id) == null) { System.out.println("Local não encontrado."); return; }
        Ronda r = new Ronda();
        r.setCpfVigilante(vigilante.getCpf());
        r.setIdLocal(id);
        r.setStatus("EM_ANDAMENTO");
        r.setDataHoraInicio(Agora.brasil());
        rondas.cadastrar(r);
        System.out.println("Ronda iniciada. ID: " + r.getIdRonda());
    }
    private void finalizar() {
        Ronda r = ativa();
        if (r == null) { System.out.println("Nenhuma ronda em andamento."); return; }
        r.setStatus("FINALIZADA");
        r.setDataHoraFim(Agora.brasil());
        rondas.alterar(r, r.getIdRonda());
        System.out.println("Ronda finalizada.");
    }
    private void registrar() {
        Ronda r = ativa();
        if (r == null) { System.out.println("Inicie uma ronda para vincular a ocorrencia."); return; }
        Ocorrencia o = new Ocorrencia();
        o.setIdRonda(r.getIdRonda());
        o.setDataHora(Agora.brasil());
        o.setTitulo(Entrada.obrigatorio(sc, "Titulo"));
        o.setDescricao(Entrada.obrigatorio(sc, "Descricao"));
        o.setTipoOcorrencia(Entrada.texto(sc, "Tipo"));
        o.setUrlFoto(Entrada.texto(sc, "URL da foto (opcional)"));
        ocorrencias.cadastrar(o);
        System.out.println("Ocorrencia registrada: " + o.getIdOcorrencia());
    }
    private void historicoRondas() {
        boolean achou = false;
        for (Ronda r : rondas.consultar()) if (minha(r)) { achou = true; System.out.println(r); }
        if (!achou) System.out.println("Nenhuma ronda.");
    }
    private void historicoOcorrencias() {
        boolean achou = false;
        for (Ocorrencia o : ocorrencias.consultar()) {
            if (o.getIdRonda() == null) continue;
            Ronda r = rondas.consultar(o.getIdRonda());
            if (minha(r)) { achou = true; System.out.println(o); }
        }
        if (!achou) System.out.println("Nenhuma ocorrncia vinculada as suas rondas.");
    }
}
