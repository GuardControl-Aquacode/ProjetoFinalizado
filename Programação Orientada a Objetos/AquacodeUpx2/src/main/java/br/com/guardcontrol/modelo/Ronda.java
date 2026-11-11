package br.com.guardcontrol.modelo;

import java.time.LocalDateTime;

public class Ronda {
    private Integer idRonda;
    private String cpfVigilante;
    private Integer idLocal;
    private String status;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public Ronda() {}

    public Integer getIdRonda() { return idRonda; }
    public void setIdRonda(Integer valor) { this.idRonda = valor; }

    public String getCpfVigilante() { return cpfVigilante; }
    public void setCpfVigilante(String valor) { this.cpfVigilante = valor; }

    public Integer getIdLocal() { return idLocal; }
    public void setIdLocal(Integer valor) { this.idLocal = valor; }

    public String getStatus() { return status; }
    public void setStatus(String valor) { this.status = valor; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime valor) { this.dataHoraInicio = valor; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime valor) { this.dataHoraFim = valor; }

    @Override
    public String toString() {
        return "Ronda{" +
                "idRonda=" + idRonda + ", " +
                "cpfVigilante=" + cpfVigilante + ", " +
                "idLocal=" + idLocal + ", " +
                "status=" + status + ", " +
                "dataHoraInicio=" + dataHoraInicio + ", " +
                "dataHoraFim=" + dataHoraFim + "}";
    }
}
