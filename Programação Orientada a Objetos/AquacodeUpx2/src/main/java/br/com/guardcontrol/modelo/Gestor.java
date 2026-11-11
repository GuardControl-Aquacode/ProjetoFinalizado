package br.com.guardcontrol.modelo;

public class Gestor {
    private Integer idGestor;
    private Integer idCliente;
    private String nome;
    private String telefone;
    private String email;

    public Gestor() {}

    public Integer getIdGestor() { return idGestor; }
    public void setIdGestor(Integer valor) { this.idGestor = valor; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer valor) { this.idCliente = valor; }

    public String getNome() { return nome; }
    public void setNome(String valor) { this.nome = valor; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String valor) { this.telefone = valor; }

    public String getEmail() { return email; }
    public void setEmail(String valor) { this.email = valor; }

    @Override
    public String toString() {
        return "Gestor{" +
                "idGestor=" + idGestor + ", " +
                "idCliente=" + idCliente + ", " +
                "nome=" + nome + ", " +
                "telefone=" + telefone + ", " +
                "email=" + email + "}";
    }
}
