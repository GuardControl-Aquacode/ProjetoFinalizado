package br.com.guardcontrol;
import br.com.guardcontrol.modelo.Vigilante;

import java.util.ArrayList;
import br.com.guardcontrol.tela.LoginEmpresa;
import br.com.guardcontrol.tela.CadastroVigilante;
import java.util.Scanner;

public class GuardControl {

    public static void main(String[] args) {

        LoginEmpresa tela = new LoginEmpresa();

        boolean loginAutorizado = false;

        while (!loginAutorizado) {

            loginAutorizado = tela.exibirLogin();

            if (!loginAutorizado) {
                System.out.println("Acesso negado, tente novamente!");
            }
        }

        System.out.println("Acesso autorizado!");

        Scanner tec = new Scanner(System.in);
        ArrayList<Vigilante> vigilantes = new ArrayList<>();

        String opcao = "";

        while (!opcao.equals("0")) {

            System.out.println("===== MENU DO GESTOR =====");
            System.out.println("1 - Cadastrar Vigilante");
            System.out.println("0 - Sair");

            System.out.print("Escolha uma opção: ");
            opcao = tec.nextLine();

            if (opcao.equals("1")) {

                CadastroVigilante cadastro = new CadastroVigilante();

                cadastro.exibirCadastro(tec, vigilantes);

            } else if (opcao.equals("0")) {

                System.out.println("Saindo do GuardControl...");

            } else {

                System.out.println("Opção inválida!");

            }
        }
    }
}
