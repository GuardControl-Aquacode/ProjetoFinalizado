package br.com.guardcontrol;

import br.com.guardcontrol.modelo.Cliente;
import br.com.guardcontrol.modelo.Vigilante;
import br.com.guardcontrol.modelo.Ocorrencia;
import br.com.guardcontrol.modelo.Ronda;
import java.time.LocalDateTime;

/** Teste rápido das entidades sem precisar de banco. */
public class TestesBasicos {
    public static void main(String[] args) {
        Cliente empresa = new Cliente();
        empresa.setNome("Empresa Exemplo");
        if (!"Empresa Exemplo".equals(empresa.getNome())) throw new AssertionError("Cliente");
        Vigilante v = new Vigilante("RE123", "12345678901", "Vigilante Teste", "Bloco L");
        if (!"RE123".equals(v.getMatricula())) throw new AssertionError("Vigilante");
        Ronda r = new Ronda(); r.setCpfVigilante(v.getCpf()); r.setStatus("EM_ANDAMENTO");
        if (!v.getCpf().equals(r.getCpfVigilante())) throw new AssertionError("Ronda");
        Ocorrencia o = new Ocorrencia(); o.setDescricao("Exemplo"); o.setDataHora(LocalDateTime.now());
        if (o.getDataHora() == null) throw new AssertionError("Ocorrencia");
        System.out.println("TESTES BÁSICOS: OK");
    }
}
