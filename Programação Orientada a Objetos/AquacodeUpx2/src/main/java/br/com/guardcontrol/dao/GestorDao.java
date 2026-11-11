package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Gestor;
import java.sql.*;
import java.util.*;

/** DAO JDBC: consulta, cadastro, edição e exclusão. */
public class GestorDao implements IGestorDao {

    private Gestor mapear(ResultSet rs) throws SQLException {
        Gestor objeto = new Gestor();
        objeto.setIdGestor((Integer) rs.getObject("id_gestor", Integer.class));
        objeto.setIdCliente((Integer) rs.getObject("id_cliente", Integer.class));
        objeto.setNome(rs.getString("nome"));
        objeto.setTelefone(rs.getString("telefone"));
        objeto.setEmail(rs.getString("email"));
        return objeto;
    }

    @Override
    public List<Gestor> consultar() {
        String sql = "SELECT * FROM gestor";
        List<Gestor> resultado = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar Gestor: " + e.getMessage(), e); }
    }

    @Override
    public Gestor consultar(Integer id) {
        String sql = "SELECT * FROM gestor WHERE id_gestor = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar Gestor: " + e.getMessage(), e); }
    }

    @Override
    public Gestor cadastrar(Gestor objeto) {
        String sql = "INSERT INTO gestor (id_cliente, nome, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setObject(1, objeto.getIdCliente());
            p.setObject(2, objeto.getNome());
            p.setObject(3, objeto.getTelefone());
            p.setObject(4, objeto.getEmail());
            p.executeUpdate();
            try (ResultSet keys = p.getGeneratedKeys()) { if (keys.next()) objeto.setIdGestor(keys.getInt(1)); }
            return objeto;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar Gestor: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Gestor objeto, Integer id) {
        String sql = "UPDATE gestor SET id_cliente = ?, nome = ?, telefone = ?, email = ? WHERE id_gestor = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, objeto.getIdCliente());
            p.setObject(2, objeto.getNome());
            p.setObject(3, objeto.getTelefone());
            p.setObject(4, objeto.getEmail());
            p.setObject(5, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar Gestor: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(Integer id) {
        String sql = "DELETE FROM gestor WHERE id_gestor = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir Gestor (verifique vínculos): " + e.getMessage(), e); }
    }
}
