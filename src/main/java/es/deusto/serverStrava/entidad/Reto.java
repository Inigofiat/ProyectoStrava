/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.serverStrava.entidad;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Reto {
	
	private long idR;
	private String nombre;
	private long fechaIni;
	private long fechaFin;
	private float objetivo;
	private Deporte deporte;
	private TipoReto tipoReto;
	private Usuario creador;
	private List<Usuario> usuarios = new ArrayList<>();

	public Reto() {
	}

	public Reto(long idR,String nombre, long fechaIni, long fechaFin, float objetivo, TipoReto tipoReto,
			Deporte deporte, Usuario creador) {
		super();
		this.idR=idR;
		this.nombre = nombre;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.objetivo=objetivo;
		this.deporte = deporte;
		this.tipoReto = tipoReto;
		this.creador = creador;
	}
	
	public Reto(long idR,String nombre, long fechaIni, long fechaFin, float objetivo, String tipoReto,
			String deporte, Usuario creador) {
		super();
		this.idR=idR;
		this.nombre = nombre;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.objetivo=objetivo;
		this.deporte = Deporte.valueOf(deporte);
		this.tipoReto=TipoReto.valueOf(tipoReto);
		this.creador = creador;
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

	public Deporte getDeporte() {
		return deporte;
	}
	
	public String getDeporteString() {
		return deporte.toString();
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	
	public long getIdR() {
		return idR;
	}

	public void setIdR(long idR) {
		this.idR = idR;
	}

	public float getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(float objetivo) {
		this.objetivo = objetivo;
	}

	public TipoReto getTipoReto() {
		return tipoReto;
	}

	public void setTipoReto(TipoReto tipoReto) {
		this.tipoReto = tipoReto;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsers(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public Usuario getCreador() {
		return creador;
	}
	
	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reto other = (Reto) obj;
		return nombre == other.nombre;
	}
	
	public boolean estaActivo() {
		if (fechaIni < fechaFin ) {
			return true;
		} else {
			return false;
		}
	}
}