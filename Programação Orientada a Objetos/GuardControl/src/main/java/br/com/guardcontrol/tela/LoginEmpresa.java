package br.com.guardcontrol.tela;

import java.util.Scanner;

public class LoginEmpresa {

    public boolean exibirLogin() {

        Scanner tec = new Scanner(System.in);

        String cnpj;
        String senha;

        System.out.println("\n ===== LOGIN DA EMPRESA ===== ");
        System.out.println("\nBem-vindo ao GuardControl!");

        System.out.println("Insira o CNPJ");
        cnpj = tec.nextLine();

        System.out.println("Insira a senha");
        senha = tec.nextLine();

        if (cnpj.isBlank() || senha.isBlank()) {

            System.out.println("ERRO: Preencha todos os campos!");
            return false;
        } else {

            if (cnpj.equals("12345678000190") && senha.equals("teste123")) {

                System.out.println("Login realizado com sucesso!");
                System.out.println("Bem-vindo à Central GuardControl!");
                return true;
            } else {

                System.out.println("CNPJ ou senha incorretos!");
                return false;
            }
        }
    }
}
