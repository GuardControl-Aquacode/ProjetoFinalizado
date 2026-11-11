package br.com.guardcontrol.modelo;

public class Cliente {
    private Integer idCliente;
    private String nome;
    private String telefone;
    private String email;

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Cliente{id=" + idCliente + ", nome=" + nome + ", telefone=" + telefone +
                ", email=" + email + "}";
    }
}
