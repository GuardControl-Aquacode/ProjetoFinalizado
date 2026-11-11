package br.com.guardcontrol.modelo;

public class Local {
    private Integer idLocal;
    private Integer idCliente;
    private String nomeLocal;
    private String descricao;
    private String cidade;

    public Local() {}

    public Integer getIdLocal() { return idLocal; }
    public void setIdLocal(Integer valor) { this.idLocal = valor; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer valor) { this.idCliente = valor; }

    public String getNomeLocal() { return nomeLocal; }
    public void setNomeLocal(String valor) { this.nomeLocal = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String valor) { this.descricao = valor; }

    public String getCidade() { return cidade; }
    public void setCidade(String valor) { this.cidade = valor; }

    @Override
    public String toString() {
        return "Local{" +
                "idLocal=" + idLocal + ", " +
                "idCliente=" + idCliente + ", " +
                "nomeLocal=" + nomeLocal + ", " +
                "descricao=" + descricao + ", " +
                "cidade=" + cidade + "}";
    }
}
