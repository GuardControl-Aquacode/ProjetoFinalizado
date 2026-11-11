package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Vigilante;
import java.sql.*;
import java.util.*;

/** Mapeia Vigilante para a tabela seguranca do DER. */
public class VigilanteDao implements IVigilanteDao {
    private Vigilante mapear(ResultSet rs) throws SQLException {
        Vigilante v = new Vigilante();
        v.setCpf(rs.getString("cpf_seguranca"));
        v.setNomeCompleto(rs.getString("nome"));
        v.setTelefone(rs.getString("telefone"));
        v.setEmail(rs.getString("email"));
        v.setMatricula(rs.getString("codigo_identificacao"));
        v.setPostoPrincipal(rs.getString("posto_principal"));
        return v;
    }

    @Override
    public List<Vigilante> consultar() {
        List<Vigilante> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM seguranca");
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar vigilantes: " + e.getMessage(), e); }
    }

    @Override
    public Vigilante consultar(String cpf) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM seguranca WHERE cpf_seguranca = ?")) {
            p.setString(1, cpf);
            try (ResultSet rs = p.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar vigilante: " + e.getMessage(), e); }
    }

    /** Busca apenas para seleção didática; NÃO autentica o usuário. */
    public Vigilante consultarPorMatricula(String matricula) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM seguranca WHERE codigo_identificacao = ?")) {
            p.setString(1, matricula);
            try (ResultSet rs = p.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar matrícula: " + e.getMessage(), e); }
    }

    @Override
    public Vigilante cadastrar(Vigilante v) {
        String sql = "INSERT INTO seguranca (cpf_seguranca, nome, telefone, email, codigo_identificacao, posto_principal) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, v.getCpf());
            p.setString(2, v.getNomeCompleto());
            p.setString(3, v.getTelefone());
            p.setString(4, v.getEmail());
            p.setString(5, v.getMatricula());
            p.setString(6, v.getPostoPrincipal());
            p.executeUpdate();
            return v;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar vigilante: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Vigilante v, String cpf) {
        String sql = "UPDATE seguranca SET nome = ?, telefone = ?, email = ?, codigo_identificacao = ?, posto_principal = ? WHERE cpf_seguranca = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, v.getNomeCompleto());
            p.setString(2, v.getTelefone());
            p.setString(3, v.getEmail());
            p.setString(4, v.getMatricula());
            p.setString(5, v.getPostoPrincipal());
            p.setString(6, cpf);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar vigilante: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(String cpf) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM seguranca WHERE cpf_seguranca = ?")) {
            p.setString(1, cpf);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir vigilante (verifique rondas vinculadas): " + e.getMessage(), e); }
    }
}
