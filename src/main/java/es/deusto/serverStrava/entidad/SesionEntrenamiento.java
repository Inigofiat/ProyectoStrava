package es.deusto.serverStrava.entidad;


import java.util.Objects;

public class SesionEntrenamiento {
	
	private long idSE;
	private String titulo;
	private float distancia;
	private long fechaIni;
	private long horaIni;
	private int duracion;
	private Deporte deporte;
	private Usuario usuario;

	// Constructor without parameters
	public SesionEntrenamiento() {
	}

	// Constructor with parameters
	
	public SesionEntrenamiento(long idSE,String titulo, float distancia, long fechaIni, long horaIni, int duracion,
			Deporte deporte, Usuario usuario) {
		super();
		this.idSE=idSE;
		this.titulo = titulo;
		this.distancia = distancia;
		this.fechaIni = fechaIni;
		this.horaIni = horaIni;
		this.duracion = duracion;
		this.deporte = deporte;
		this.usuario = usuario;
	}
	
	public SesionEntrenamiento(long idSE,String titulo, float distancia, long fechaIni, long horaIni, int duracion,
			String deporte, Usuario usuario) {
		super();
		this.idSE=idSE;
		this.titulo = titulo;
		this.distancia = distancia;
		this.fechaIni = fechaIni;
		this.horaIni = horaIni;
		this.duracion = duracion;
		this.deporte = Deporte.valueOf(deporte);
		this.usuario = usuario;
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

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	
	public String getDeporteToString() {
		return deporte.toString();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public long getIdSE() {
		return idSE;
	}

	public void setIdSE(long idSE) {
		this.idSE = idSE;
	}

	// hashCode and equals
	@Override
	public int hashCode() {
		return Objects.hash(titulo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SesionEntrenamiento other = (SesionEntrenamiento) obj;
		return Objects.equals(titulo, other.titulo);
	}
}