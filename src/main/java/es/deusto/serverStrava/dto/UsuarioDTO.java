package es.deusto.serverStrava.dto;

import java.util.Objects;

import es.deusto.serverStrava.entidad.TipoRegistro;

public class UsuarioDTO {
    
    private String nombre;
    private String nombreUsuario;
    private String email;
    private long fechaNac;
    private float pesoKg;
    private int alturaCm;
    private float frecuenciaMax;
    private float frecuenciaReposo;
    private long idUsuario;
    public TipoRegistro tipoRegistro;

    // Constructor vacío
    public UsuarioDTO() {
    }

    // Constructor con parámetros
    public UsuarioDTO(String nombre, String nombreUsuario, String email, long fechaNac,
                      float pesoKg, int alturaCm, float frecuenciaMax, float frecuenciaReposo, TipoRegistro tipoRegistro) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.fechaNac = fechaNac;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
        this.frecuenciaMax = frecuenciaMax;
        this.frecuenciaReposo = frecuenciaReposo;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(long fechaNac) {
        this.fechaNac = fechaNac;
    }

    public float getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }

    public int getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(int alturaCm) {
        this.alturaCm = alturaCm;
    }

    public float getFrecuenciaMax() {
        return frecuenciaMax;
    }

    public void setFrecuenciaMax(float frecuenciaMax) {
        this.frecuenciaMax = frecuenciaMax;
    }

    public float getFrecuenciaReposo() {
        return frecuenciaReposo;
    }

    public void setFrecuenciaReposo(float frecuenciaReposo) {
        this.frecuenciaReposo = frecuenciaReposo;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public TipoRegistro getTipoRegistro() {
    	return tipoRegistro;
    }
    
    public void setTipoRegistro(TipoRegistro tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UsuarioDTO other = (UsuarioDTO) obj;
        return Objects.equals(email, other.email) &&
               Objects.equals(nombreUsuario, other.nombreUsuario) &&
               Float.compare(other.pesoKg, pesoKg) == 0 &&
               alturaCm == other.alturaCm &&
               Float.compare(other.frecuenciaMax, frecuenciaMax) == 0 &&
               Float.compare(other.frecuenciaReposo, frecuenciaReposo) == 0 &&
               fechaNac == other.fechaNac &&
               Objects.equals(nombre, other.nombre) &&
               Objects.equals(tipoRegistro, other.tipoRegistro);
    }
}
