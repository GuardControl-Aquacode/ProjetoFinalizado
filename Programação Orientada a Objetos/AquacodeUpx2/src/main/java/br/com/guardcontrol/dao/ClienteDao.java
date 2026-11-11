package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDao implements IClienteDao {
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNome(rs.getString("nome"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        return c;
    }

    @Override
    public List<Cliente> consultar() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM cliente");
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar empresas: " + e.getMessage(), e); }
    }

    @Override
    public Cliente consultar(Integer id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM cliente WHERE id_cliente = ?")) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao consultar empresa: " + e.getMessage(), e); }
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, cliente.getNome());
            p.setString(2, cliente.getTelefone());
            p.setString(3, cliente.getEmail());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) { if (rs.next()) cliente.setIdCliente(rs.getInt(1)); }
            return cliente;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar empresa: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Cliente cliente, Integer id) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, cliente.getNome());
            p.setString(2, cliente.getTelefone());
            p.setString(3, cliente.getEmail());
            p.setInt(4, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar empresa: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(Integer id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM cliente WHERE id_cliente = ?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir empresa (verifique registros vinculados): " + e.getMessage(), e); }
    }
}
