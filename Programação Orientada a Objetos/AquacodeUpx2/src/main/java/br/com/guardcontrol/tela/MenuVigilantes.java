package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.VigilanteDao;
import br.com.guardcontrol.modelo.Vigilante;
import br.com.guardcontrol.util.Entrada;
import java.util.Scanner;

/** A tabela seguranca NÃO possui id_cliente: cadastro geral, não restrito por empresa. */
public class MenuVigilantes {
    private final Scanner sc;
    private final VigilanteDao dao = new VigilanteDao();

    public MenuVigilantes(Scanner sc) { this.sc = sc; }

    public void exibir() {
        System.out.println("Vigilantes são registros gerais:");
        while (true) {
            System.out.println("\n=== VIGILANTES ===");
            System.out.println("1 - Listar | 2 - Buscar por CPF | 3 - Cadastrar | 4 - Alterar | 5 - Excluir | 0 - Voltar");
            String escolha = Entrada.texto(sc, "Escolha");
            try {
                switch (escolha) {
                    case "1": for (Vigilante v : dao.consultar()) System.out.println(v); break;
                    case "2": buscar(); break;
                    case "3": dao.cadastrar(new CadastroVigilante().exibirCadastro(sc));
                              System.out.println("Vigilante cadastrado."); break;
                    case "4": alterar(); break;
                    case "5": excluir(); break;
                    case "0": return;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    private Vigilante localizar() {
        Vigilante v = dao.consultar(Entrada.somenteDigitos(sc, "CPF", 11));
        if (v == null) System.out.println("Vigilante nao encontrado.");
        return v;
    }
    private void buscar() { Vigilante v = localizar(); if (v != null) System.out.println(v); }
    private void alterar() {
        Vigilante v = localizar();
        if (v == null) return;
        new CadastroVigilante().editarCadastro(sc, v);
        System.out.println(dao.alterar(v, v.getCpf()) ? "Vigilante atualizado." : "Nenhuma alteracao.");
    }
    private void excluir() {
        Vigilante v = localizar();
        if (v != null && Entrada.confirmar(sc, "Confirmar exclusao?"))
            System.out.println(dao.excluir(v.getCpf()) ? "Vigilante excluido." : "Nenhuma exclusao.");
    }
}
