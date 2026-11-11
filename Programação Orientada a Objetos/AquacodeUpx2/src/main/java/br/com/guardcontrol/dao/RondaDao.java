package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Ronda;
import java.sql.*;
import java.util.*;

/** DAO JDBC: consulta, cadastro, edição e exclusão. */
public class RondaDao implements IRondaDao {

    private Ronda mapear(ResultSet rs) throws SQLException {
        Ronda objeto = new Ronda();
        objeto.setIdRonda((Integer) rs.getObject("id_ronda", Integer.class));
        objeto.setCpfVigilante(rs.getString("cpf_seguranca"));
        objeto.setIdLocal((Integer) rs.getObject("id_local", Integer.class));
        objeto.setStatus(rs.getString("status"));
        objeto.setDataHoraInicio((rs.getTimestamp("dt_hora_inicio") == null ? null : rs.getTimestamp("dt_hora_inicio").toLocalDateTime()));
        objeto.setDataHoraFim((rs.getTimestamp("dt_hora_fim") == null ? null : rs.getTimestamp("dt_hora_fim").toLocalDateTime()));
        return objeto;
    }

    @Override
    public List<Ronda> consultar() {
        String sql = "SELECT * FROM ronda";
        List<Ronda> resultado = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar Ronda: " + e.getMessage(), e); }
    }

    @Override
    public Ronda consultar(Integer id) {
        String sql = "SELECT * FROM ronda WHERE id_ronda = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar Ronda: " + e.getMessage(), e); }
    }

    @Override
    public Ronda cadastrar(Ronda objeto) {
        String sql = "INSERT INTO ronda (cpf_seguranca, id_local, status, dt_hora_inicio, dt_hora_fim) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setObject(1, objeto.getCpfVigilante());
            p.setObject(2, objeto.getIdLocal());
            p.setObject(3, objeto.getStatus());
            p.setTimestamp(4, objeto.getDataHoraInicio() == null ? null : Timestamp.valueOf(objeto.getDataHoraInicio()));
            p.setTimestamp(5, objeto.getDataHoraFim() == null ? null : Timestamp.valueOf(objeto.getDataHoraFim()));
            p.executeUpdate();
            try (ResultSet keys = p.getGeneratedKeys()) { if (keys.next()) objeto.setIdRonda(keys.getInt(1)); }
            return objeto;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar Ronda: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Ronda objeto, Integer id) {
        String sql = "UPDATE ronda SET cpf_seguranca = ?, id_local = ?, status = ?, dt_hora_inicio = ?, dt_hora_fim = ? WHERE id_ronda = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, objeto.getCpfVigilante());
            p.setObject(2, objeto.getIdLocal());
            p.setObject(3, objeto.getStatus());
            p.setTimestamp(4, objeto.getDataHoraInicio() == null ? null : Timestamp.valueOf(objeto.getDataHoraInicio()));
            p.setTimestamp(5, objeto.getDataHoraFim() == null ? null : Timestamp.valueOf(objeto.getDataHoraFim()));
            p.setObject(6, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar Ronda: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(Integer id) {
        String sql = "DELETE FROM ronda WHERE id_ronda = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir Ronda (verifique vínculos): " + e.getMessage(), e); }
    }
}
