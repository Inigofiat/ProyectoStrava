package es.deusto.serverStrava.dto;

import java.util.Objects;

public class RetoDTO {
    private long idR;
    private String nombre;
    private long fechaIni;
    private long fechaFin;
    private float objetivo;
    private String deporte;
    private String tipoReto;

    public RetoDTO() {
    }

    public RetoDTO(long idR, String nombre, long fechaIni, long fechaFin, float objetivo, String deporte, String tipoReto) {
        this.idR = idR;
        this.nombre = nombre;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.objetivo = objetivo;
        this.deporte = deporte;
        this.tipoReto = tipoReto;
    }

    public long getIdR() {
        return idR;
    }

    public void setIdR(long idR) {
        this.idR = idR;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(long fechaIni) {
        this.fechaIni = fechaIni;
    }

    public long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(long fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(float objetivo) {
        this.objetivo = objetivo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getTipoReto() {
        return tipoReto;
    }

    public void setTipoReto(String tipoReto) {
        this.tipoReto = tipoReto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idR, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        RetoDTO other = (RetoDTO) obj;
        return idR == other.idR && Objects.equals(nombre, other.nombre);
    }


}
