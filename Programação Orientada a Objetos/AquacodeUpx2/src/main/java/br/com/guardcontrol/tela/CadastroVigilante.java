package br.com.guardcontrol.tela;

import br.com.guardcontrol.modelo.Vigilante;
import br.com.guardcontrol.util.Entrada;
import java.util.Scanner;

public class CadastroVigilante {
    public Vigilante exibirCadastro(Scanner sc) {
        System.out.println("\n=== CADASTRO DE VIGILANTE ===");
        Vigilante v = new Vigilante();
        v.setMatricula(Entrada.obrigatorio(sc, "Matricula/RE"));
        v.setCpf(Entrada.somenteDigitos(sc, "CPF", 11));
        v.setNomeCompleto(Entrada.obrigatorio(sc, "Nome completo"));
        v.setTelefone(Entrada.obrigatorio(sc, "Telefone"));
        v.setEmail(Entrada.texto(sc, "Email (opcional)"));
        v.setPostoPrincipal(Entrada.obrigatorio(sc, "Posto principal"));
        return v;
    }

    public Vigilante editarCadastro(Scanner sc, Vigilante v) {
        System.out.println("\n=== ALTERAR VIGILANTE ===");
        v.setMatricula(Entrada.alterar(sc, "Matricula/RE", v.getMatricula(), true));
        v.setNomeCompleto(Entrada.alterar(sc, "Nome", v.getNomeCompleto(), true));
        v.setTelefone(Entrada.alterar(sc, "Telefone", v.getTelefone(), true));
        v.setEmail(Entrada.alterar(sc, "Email", v.getEmail(), false));
        v.setPostoPrincipal(Entrada.alterar(sc, "Posto principal", v.getPostoPrincipal(), true));
        return v;
    }
}
