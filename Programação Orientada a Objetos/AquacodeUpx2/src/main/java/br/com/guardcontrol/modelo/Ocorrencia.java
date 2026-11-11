package br.com.guardcontrol.modelo;

import java.time.LocalDateTime;

public class Ocorrencia {
    private Integer idOcorrencia;
    private Integer idRonda;
    private String titulo;
    private LocalDateTime dataHora;
    private String descricao;
    private String tipoOcorrencia;
    private String urlFoto;

    public Integer getIdOcorrencia() { return idOcorrencia; }
    public void setIdOcorrencia(Integer valor) { idOcorrencia = valor; }
    public Integer getIdRonda() { return idRonda; }
    public void setIdRonda(Integer valor) { idRonda = valor; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String valor) { titulo = valor; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime valor) { dataHora = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String valor) { descricao = valor; }
    public String getTipoOcorrencia() { return tipoOcorrencia; }
    public void setTipoOcorrencia(String valor) { tipoOcorrencia = valor; }
    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String valor) { urlFoto = valor; }

    @Override
    public String toString() {
        return "Ocorrencia{id=" + idOcorrencia + ", ronda=" + idRonda + ", titulo=" + titulo +
                ", dataHora=" + dataHora + ", descricao=" + descricao +
                ", tipo=" + tipoOcorrencia + ", foto=" + urlFoto + "}";
    }
}
