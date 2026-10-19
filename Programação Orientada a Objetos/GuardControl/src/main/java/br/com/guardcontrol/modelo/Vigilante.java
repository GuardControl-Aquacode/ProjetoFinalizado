package br.com.guardcontrol.modelo;

public class Vigilante {

    private String matricula;
    private String cpf;
    private String nomeCompleto;
    private String turno;
    private String estado;
    private String postoPrincipal;
    
    public Vigilante(String matricula, String cpf,
                 String nomeCompleto, String turno,
                 String estado, String postoPrincipal) {
        
        this.matricula = matricula;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.turno = turno;
        this.estado = estado;
        this.postoPrincipal = postoPrincipal;
    }

}
