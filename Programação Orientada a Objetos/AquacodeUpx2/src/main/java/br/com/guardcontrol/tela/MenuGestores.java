package br.com.guardcontrol.tela;

import br.com.guardcontrol.dao.*;
import br.com.guardcontrol.modelo.*;
import br.com.guardcontrol.util.*;
import java.util.Scanner;

/** Menu simples, com as 5 operações do CRUD escritas sem classes genéricas. */
public class MenuGestores {
    private final Scanner sc;
    private final Cliente empresa;
    private final GestorDao dao = new GestorDao();

    public MenuGestores(Scanner sc, Cliente empresa) {
        this.sc = sc;
        this.empresa = empresa;
    }

    private boolean pertence(Gestor v) {
        return v != null && empresa.getIdCliente().equals(v.getIdCliente());
    }

    

    public void exibir() {
        while (true) {
            System.out.println("\n==== GESTORES ====");
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
        for (Gestor v : dao.consultar()) {
            if (pertence(v)) {
                System.out.println(v);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum registro desta empresa.");
    }

    private Gestor localizar() {
        Integer id = Entrada.inteiro(sc, "ID do gestor");
        Gestor v = dao.consultar(id);
        if (!pertence(v)) {
            System.out.println("Registro não encontrado nesta empresa.");
            return null;
        }
        return v;
    }

    private void buscar() {
        Gestor v = localizar();
        if (v != null) System.out.println(v);
    }

    private void cadastrar() {
        Gestor v = novoRegistro();
        dao.cadastrar(v);
        System.out.println("Cadastro gravado no MySQL: " + v);
    }

    private void alterar() {
        Gestor v = localizar();
        if (v == null) return;
        editarRegistro(v);
        if (dao.alterar(v, v.getIdGestor())) {
            System.out.println("Registro alterado.");
        } else {
            System.out.println("Nenhuma alteracao realizada.");
        }
    }

    private void excluir() {
        Gestor v = localizar();
        if (v == null) return;
        System.out.println(v);
        if (Entrada.confirmar(sc, "Confirmar exclusao permanente?")) {
            if (dao.excluir(v.getIdGestor())) System.out.println("Registro excluido.");
            else System.out.println("Registro não excluido.");
        }
    }

    private Gestor novoRegistro() {
        Gestor v = new Gestor();
        v.setIdCliente(empresa.getIdCliente());
        v.setNome(Entrada.obrigatorio(sc, "Nome"));
        v.setTelefone(Entrada.obrigatorio(sc, "Telefone"));
        v.setEmail(Entrada.texto(sc, "E-mail (opcional)"));
        return v;
    }

    private void editarRegistro(Gestor v) {
        v.setNome(Entrada.alterar(sc, "Nome", v.getNome(), true));
        v.setTelefone(Entrada.alterar(sc, "Telefone", v.getTelefone(), true));
        v.setEmail(Entrada.alterar(sc, "E-mail", v.getEmail(), false));
    }
}
