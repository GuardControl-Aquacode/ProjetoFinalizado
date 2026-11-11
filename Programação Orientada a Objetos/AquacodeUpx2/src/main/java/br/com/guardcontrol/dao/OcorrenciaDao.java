package br.com.guardcontrol.dao;

import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Ocorrencia;
import java.sql.*;
import java.util.*;

public class OcorrenciaDao implements IOcorrenciaDao {
    private Ocorrencia mapear(ResultSet rs) throws SQLException {
        Ocorrencia o = new Ocorrencia();
        o.setIdOcorrencia(rs.getInt("id_ocorrencia"));
        o.setIdRonda((Integer)rs.getObject("id_ronda", Integer.class));
        o.setTitulo(rs.getString("titulo"));
        Timestamp t = rs.getTimestamp("data_hora");
        o.setDataHora(t == null ? null : t.toLocalDateTime());
        o.setDescricao(rs.getString("descricao"));
        o.setTipoOcorrencia(rs.getString("tipo_ocorrencia"));
        o.setUrlFoto(rs.getString("URL_foto"));
        return o;
    }

    @Override
    public List<Ocorrencia> consultar() {
        List<Ocorrencia> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM ocorrencia");
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar ocorrencias: " + e.getMessage(), e); }
    }

    @Override
    public Ocorrencia consultar(Integer id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM ocorrencia WHERE id_ocorrencia = ?")) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar ocorrencia: " + e.getMessage(), e); }
    }

    @Override
    public Ocorrencia cadastrar(Ocorrencia o) {
        String sql = "INSERT INTO ocorrencia (id_ronda, titulo, data_hora, descricao, tipo_ocorrencia, URL_foto) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setObject(1, o.getIdRonda());
            p.setString(2, o.getTitulo());
            p.setTimestamp(3, Timestamp.valueOf(o.getDataHora()));
            p.setString(4, o.getDescricao());
            p.setString(5, o.getTipoOcorrencia());
            p.setString(6, o.getUrlFoto());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) { if (rs.next()) o.setIdOcorrencia(rs.getInt(1)); }
            return o;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar ocorrencia: " + e.getMessage(), e); }
    }

    @Override
    public boolean alterar(Ocorrencia o, Integer id) {
        String sql = "UPDATE ocorrencia SET id_ronda = ?, titulo = ?, data_hora = ?, descricao = ?, tipo_ocorrencia = ?, URL_foto = ? WHERE id_ocorrencia = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setObject(1, o.getIdRonda());
            p.setString(2, o.getTitulo());
            p.setTimestamp(3, Timestamp.valueOf(o.getDataHora()));
            p.setString(4, o.getDescricao());
            p.setString(5, o.getTipoOcorrencia());
            p.setString(6, o.getUrlFoto());
            p.setInt(7, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao alterar ocorrencia: " + e.getMessage(), e); }
    }

    @Override
    public boolean excluir(Integer id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM ocorrencia WHERE id_ocorrencia = ?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir ocorrencia: " + e.getMessage(), e); }
    }
}
