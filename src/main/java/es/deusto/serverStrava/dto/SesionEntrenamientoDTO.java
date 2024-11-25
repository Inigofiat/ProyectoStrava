package es.deusto.serverStrava.dto;

import java.util.Objects; 

public class SesionEntrenamientoDTO {
    
    private long idSE;
    private String titulo;
    private float distancia;
    private long fechaIni;
    private long horaIni;
    private int duracion;
    private String deporte;
    private String emailUsuario;

    // Constructor vacío
    public SesionEntrenamientoDTO() {
    }

    // Constructor con parámetros
    public SesionEntrenamientoDTO(long idSE, String titulo, float distancia, long fechaIni, long horaIni, int duracion,
                                  String deporte, String emailUsuario) {
        this.idSE = idSE;
        this.titulo = titulo;
        this.distancia = distancia;
        this.fechaIni = fechaIni;
        this.horaIni = horaIni;
        this.duracion = duracion;
        this.deporte = deporte;
        this.emailUsuario = emailUsuario;
    }

    // Getters y Setters
    public long getIdSE() {
        return idSE;
    }

    public void setIdSE(long idSE) {
        this.idSE = idSE;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public long getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(long fechaIni) {
        this.fechaIni = fechaIni;
    }

    public long getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(long horaIni) {
        this.horaIni = horaIni;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setUsuarioId(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SesionEntrenamientoDTO other = (SesionEntrenamientoDTO) obj;
        return idSE == other.idSE && Objects.equals(titulo, other.titulo);
    }
}

