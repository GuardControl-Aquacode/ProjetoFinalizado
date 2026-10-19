package br.com.guardcontrol.tela;

import java.util.Scanner;
import java.util.ArrayList;
import br.com.guardcontrol.modelo.Vigilante;

public class CadastroVigilante {

    public void exibirCadastro(Scanner tec, ArrayList<Vigilante> vigilantes) {

        String matricula;
        String cpf;
        String nomeCompleto;
        String turno = "";
        String estado = "";
        String postoPrincipal;

        System.out.println("==== CADASTRO DE VIGILANTE ====");
        System.out.println("Bem-vindo ao cadastro do GuardControl!");

        matricula = "";
        while (matricula.isBlank()) {
            System.out.println("Digite sua matrícula/RE:");
            matricula = tec.nextLine();

            if (matricula.isBlank()) {
                System.out.println("O campo de matrícula/RE não pode ficar vazio, preencha novamente!");
            }
        }

        cpf = "";
        while (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            System.out.println("Digite seu CPF:");
            cpf = tec.nextLine();

            if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
                System.out.println("O campo de CPF deve conter exatamente 11 caracteres, preencha novamente!");
            }
        }

        nomeCompleto = "";
        while (nomeCompleto.isBlank()) {
            System.out.println("Digite seu nome completo:");
            nomeCompleto = tec.nextLine();

            if (nomeCompleto.isBlank()) {
                System.out.println("O campo de nome completo não pode ficar vazio, preencha novamente!");
            }
        }

        boolean turnoValido = false;

        while (!turnoValido) {
            System.out.println("Digite o turno em que trabalha:");
            turno = tec.nextLine();

            if (turno.equalsIgnoreCase("Noturno") || (turno.equalsIgnoreCase("Diurno"))) {
                System.out.println("Turno válido!");

                turnoValido = true;

            } else {
                System.out.println("Turno inválido, tente novamente!");
            }
        }

        boolean estadoValido = false;

        while (!estadoValido) {
            System.out.println("Digite o Estado atual do Vigilante:");
            estado = tec.nextLine();

            if (estado.equalsIgnoreCase("Ativo") || (estado.equalsIgnoreCase("Inativo"))) {
                System.out.println("Estado válido!");

                estadoValido = true;
            } else {
                System.out.println("Estado inválido, digite novamente!");
            }
        }

        postoPrincipal = "";

        while (postoPrincipal.isBlank()) {
            System.out.println("Digite o posto principal:");
            postoPrincipal = tec.nextLine();

            if (postoPrincipal.isBlank()) {
                System.out.println("O posto principal não pode ficar vazio, preencha novamente!");

            }
        }

        System.out.println("Matrícula recebida: " + matricula);
        System.out.println("CPF recebido: " + cpf);
        System.out.println("Nome completo recebido: " + nomeCompleto);
        System.out.println("Posto principal recebido: " + postoPrincipal);

        Vigilante novoVigilante = new Vigilante(
                matricula,
                cpf,
                nomeCompleto,
                turno,
                estado,
                postoPrincipal
        );

        vigilantes.add(novoVigilante);

        System.out.println("Vigilante adicionado à lista com sucesso!");

        System.out.println("Total de vigilantes: " + vigilantes.size());

    }
}
