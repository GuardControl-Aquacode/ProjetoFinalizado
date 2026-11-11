package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Local;
import java.sql.*;
import java.util.*;

/** DAO JDBC: consulta, cadastro, edição e exclusão. */
public class LocalDao implements ILocalDao {

    private Local mapear(ResultSet rs) throws SQLException {
        Local objeto = new Local();
        objeto.setIdLocal((Integer) rs.getObject("id_local", Integer.class));
        objeto.setIdCliente((Integer) rs.getObject("id_cliente", Integer.class));
        objeto.setNomeLocal(rs.getString("nome_local"));
        objeto.setDescricao(rs.getString("descricao"));
        objeto.setCidade(rs.getString("cidade"));
        return objeto;
    }

    @Override
    public List<Local> consultar() {
        String sql = "SELECT * FROM local";
        List<Local> resultado = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar Local: " + e.getMessage(), e); }
    }

    @Override
    public Local consultar(Integer id) {
        String sql = "SELECT * FROM local WHERE id_local = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar Local: " + e.getMessage(), e); }
    }

    @Override
    public Local cadastrar(Local objeto) {
        String sql = "INSERT INTO local (id_cliente, nome_local, descricao, cidade) VALUES (?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setObject(1, objeto.getIdCliente());
            p.setObject(2, objeto.getNomeLocal());
            p.setObject(3, objeto.getDescricao());
            p.setObject(4, objeto.getCidade());
            p.executeUpdate();
            try (ResultSet keys = p.getGeneratedKeys()) { if (keys.next()) objeto.setIdLocal(keys.getInt(1)); }
            return objeto;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar Local: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Local objeto, Integer id) {
        String sql = "UPDATE local SET id_cliente = ?, nome_local = ?, descricao = ?, cidade = ? WHERE id_local = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, objeto.getIdCliente());
            p.setObject(2, objeto.getNomeLocal());
            p.setObject(3, objeto.getDescricao());
            p.setObject(4, objeto.getCidade());
            p.setObject(5, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar Local: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(Integer id) {
        String sql = "DELETE FROM local WHERE id_local = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir Local (verifique vínculos): " + e.getMessage(), e); }
    }
}
