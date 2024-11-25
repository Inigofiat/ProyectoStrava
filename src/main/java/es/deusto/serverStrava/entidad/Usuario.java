package es.deusto.serverStrava.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	
	private String nombre;
	private String nombreUsuario;
	private String email;
	private long fechaNac;
	private float pesoKg;
	private int alturaCm;
	private float frecuenciaMax;
	private float frecuenciaReposo;
	private List<SesionEntrenamiento> lSesionesEntrenamiento = new ArrayList<>();
	private List<Reto> lRetos = new ArrayList<>();
	private TipoRegistro tipoRegistro;
	
	// Constructor without parameters
	public Usuario() { }
	
	// Constructor with parameters
	
	public Usuario(String nombre, String nombreUsuario, String email, long fechaNac,
			float pesoKg, int alturaCm, float frecuenciaCardiacaMax, float frecuenciaCardiacaReposo,
			List<SesionEntrenamiento> lSesionesEntrenamiento, List<Reto> lRetos) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.fechaNac = fechaNac;
		this.pesoKg = pesoKg;
		this.alturaCm = alturaCm;
		this.frecuenciaMax = frecuenciaCardiacaMax;
		this.frecuenciaReposo = frecuenciaCardiacaReposo;
		this.lSesionesEntrenamiento = lSesionesEntrenamiento;
		this.lRetos = lRetos;
	}	


	// hashCode and equals
	@Override
	public int hashCode() {
		return Objects.hash(email, nombreUsuario);
	}

	//  Getters and setters

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

	public List<SesionEntrenamiento> getlSesionesEntrenamiento() {
		return lSesionesEntrenamiento;
	}

	public void setlSesionesEntrenamiento(List<SesionEntrenamiento> lSesionesEntrenamiento) {
		this.lSesionesEntrenamiento = lSesionesEntrenamiento;
	}

	public List<Reto> getlRetos() {
		return lRetos;
	}

	public void setlRetos(List<Reto> lRetos) {
		this.lRetos = lRetos;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && 
			   Objects.equals(nombreUsuario, other.nombreUsuario);
	}
}