package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.*;
import java.util.Scanner;

/** Menu simples, com as 5 operações do CRUD escritas sem classes genéricas. */
public class MenuLocais {
    private final Scanner sc;
    private final Cliente empresa;
    private final LocalDao dao = new LocalDao();

    public MenuLocais(Scanner sc, Cliente empresa) {
        this.sc = sc;
        this.empresa = empresa;
    }

    private boolean pertence(Local v) {
        return v != null && empresa.getIdCliente().equals(v.getIdCliente());
    }

    

    public void exibir() {
        while (true) {
            System.out.println("\n==== LOCAIS ====");
            System.out.println("1 - Listar");
            System.out.println("2 - Buscar por ID");
            System.out.println("3 - Cadastrar");
            System.out.println("4 - Alterar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Voltar");
            String opcao = Entrada.texto(sc, "Escolha");
            try {
                switch (opcao) {
                    case "1": listar(); break;
                    case "2": buscar(); break;
                    case "3": cadastrar(); break;
                    case "4": alterar(); break;
                    case "5": excluir(); break;
                    case "0": return;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (IllegalStateException | IllegalArgumentException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        }
    }

    private void listar() {
        boolean encontrou = false;
        for (Local v : dao.consultar()) {
            if (pertence(v)) {
                System.out.println(v);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum registro desta empresa.");
    }

    private Local localizar() {
        Integer id = Entrada.inteiro(sc, "ID do local");
        Local v = dao.consultar(id);
        if (!pertence(v)) {
            System.out.println("Registro não encontrado nesta empresa.");
            return null;
        }
        return v;
    }

    private void buscar() {
        Local v = localizar();
        if (v != null) System.out.println(v);
    }

    private void cadastrar() {
        Local v = novoRegistro();
        dao.cadastrar(v);
        System.out.println("Cadastro gravado no MySQL: " + v);
    }

    private void alterar() {
        Local v = localizar();
        if (v == null) return;
        editarRegistro(v);
        if (dao.alterar(v, v.getIdLocal())) {
            System.out.println("Registro alterado.");
        } else {
            System.out.println("Nenhuma alteracao realizada.");
        }
    }

    private void excluir() {
        Local v = localizar();
        if (v == null) return;
        System.out.println(v);
        if (Entrada.confirmar(sc, "Confirmar exclusao permanente?")) {
            if (dao.excluir(v.getIdLocal())) System.out.println("Registro excluido.");
            else System.out.println("Registro não excluido.");
        }
    }

    private Local novoRegistro() {
        Local v = new Local();
        v.setIdCliente(empresa.getIdCliente());
        v.setNomeLocal(Entrada.obrigatorio(sc, "Nome do local"));
        v.setDescricao(Entrada.obrigatorio(sc, "Descrição"));
        v.setCidade(Entrada.obrigatorio(sc, "Cidade"));
        return v;
    }

    private void editarRegistro(Local v) {
        v.setNomeLocal(Entrada.alterar(sc, "Nome", v.getNomeLocal(), true));
        v.setDescricao(Entrada.alterar(sc, "Descrição", v.getDescricao(), true));
        v.setCidade(Entrada.alterar(sc, "Cidade", v.getCidade(), true));
    }
}
