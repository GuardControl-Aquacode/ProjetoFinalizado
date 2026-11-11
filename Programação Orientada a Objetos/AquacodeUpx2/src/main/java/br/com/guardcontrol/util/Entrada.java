package br.com.guardcontrol.util;

import java.util.Scanner;

/** Centraliza a leitura do console para não criar Scanners concorrentes. */
public final class Entrada {
    private Entrada() { }

    public static String texto(Scanner sc, String rotulo) {
        System.out.print(rotulo + ": ");
        return sc.nextLine().trim();
    }
    public static String obrigatorio(Scanner sc, String rotulo) {
        while (true) {
            String valor = texto(sc, rotulo);
            if (!valor.isBlank()) return valor;
            System.out.println("Campo obrigatório.");
        }
    }
    public static String alterar(Scanner sc, String rotulo, String atual, boolean obrigatorio) {
        System.out.print(rotulo + " [" + (atual == null ? "" : atual) + "] (Enter mantém): ");
        String valor = sc.nextLine().trim();
        if (valor.isEmpty()) {
            if (obrigatorio && (atual == null || atual.isBlank())) return obrigatorio(sc, rotulo);
            return atual;
        }
        return valor;
    }
    public static Integer inteiro(Scanner sc, String rotulo) {
        while (true) {
            try { return Integer.parseInt(obrigatorio(sc, rotulo)); }
            catch (NumberFormatException e) { System.out.println("Informe um número inteiro."); }
        }
    }
    public static Integer inteiroOpcional(Scanner sc, String rotulo) {
        while (true) {
            String v = texto(sc, rotulo + " (Enter para nenhum)");
            if (v.isBlank()) return null;
            try { return Integer.valueOf(v); }
            catch (NumberFormatException e) { System.out.println("Número inválido."); }
        }
    }
    public static String somenteDigitos(Scanner sc, String rotulo, int tamanho) {
        while (true) {
            String v = texto(sc, rotulo + " (" + tamanho + " dígitos)");
            if (v.matches("\\d{" + tamanho + "}")) return v;
            System.out.println("Digite exatamente " + tamanho + " números, sem pontuação.");
        }
    }
    public static boolean confirmar(Scanner sc, String texto) {
        return texto(sc, texto + " [S/N]").equalsIgnoreCase("S");
    }
    // Escolha entre dois textos permitidos (exemplo: ATIVO ou INATIVO).
    public static String enumValor(Scanner sc, String rotulo, String a, String b) {
        while (true) {
            String opcao = texto(sc, rotulo + " (" + a + "/" + b + ")");
            if (opcao.equalsIgnoreCase(a)) return a;
            if (opcao.equalsIgnoreCase(b)) return b;
            System.out.println("Opção inválida.");
        }
    }

    // Editar valor: aceitar Enter para manter o dado atual.
    public static String enumAlterar(Scanner sc, String rotulo, String atual, String a, String b) {
        while (true) {
            String opcao = alterar(sc, rotulo + " (" + a + "/" + b + ")", atual, true);
            if (opcao.equalsIgnoreCase(a)) return a;
            if (opcao.equalsIgnoreCase(b)) return b;
            System.out.println("Opção inválida.");
        }
    }

    // Versão com três alternativas (exemplo: status da ronda).
    public static String enumAlterar(Scanner sc, String rotulo, String atual,
                                    String a, String b, String c) {
        while (true) {
            String opcao = alterar(sc, rotulo + " (" + a + "/" + b + "/" + c + ")", atual, true);
            if (opcao.equalsIgnoreCase(a)) return a;
            if (opcao.equalsIgnoreCase(b)) return b;
            if (opcao.equalsIgnoreCase(c)) return c;
            System.out.println("Opção inválida.");
        }
    }
}
