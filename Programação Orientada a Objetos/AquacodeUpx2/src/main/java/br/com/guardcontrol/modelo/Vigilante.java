package br.com.guardcontrol.modelo;

/** Corresponde à tabela seguranca do DER. */
public class Vigilante {
    private String cpf;
    private String nomeCompleto;
    private String telefone;
    private String email;
    private String matricula;
    private String postoPrincipal;

    public Vigilante() {}

    public Vigilante(String matricula, String cpf, String nomeCompleto, String postoPrincipal) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.postoPrincipal = postoPrincipal;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getPostoPrincipal() { return postoPrincipal; }
    public void setPostoPrincipal(String postoPrincipal) { this.postoPrincipal = postoPrincipal; }

    @Override
    public String toString() {
        return "Vigilante{cpf=" + cpf + ", nome=" + nomeCompleto + ", telefone=" + telefone +
                ", email=" + email + ", matricula=" + matricula +
                ", postoPrincipal=" + postoPrincipal + "}";
    }
}
