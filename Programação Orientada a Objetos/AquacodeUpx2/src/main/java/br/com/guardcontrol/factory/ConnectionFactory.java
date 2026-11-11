package br.com.guardcontrol.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Configuração externa: nunca escrever a senha do banco no código-fonte. */
public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = config("AQUACODE_DB_URL", "jdbc:mysql://127.0.0.1:3306/AquacodeUpx2");
        String usuario = config("AQUACODE_DB_USER", "root");
        String senha = config("AQUACODE_DB_PASSWORD", "Anthony");
        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String config(String nome, String padrao) {
        String valor = System.getProperty(nome);
        if (valor == null) valor = System.getenv(nome);
        return valor == null ? padrao : valor;
    }
}
