/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.serverStrava.servicio;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.serverStrava.entidad.Deporte;
import es.deusto.serverStrava.entidad.Reto;
import es.deusto.serverStrava.entidad.SesionEntrenamiento;
import es.deusto.serverStrava.entidad.Usuario;

@Service
public class ServicioStrava {

	private static Map<Long, SesionEntrenamiento> mapaSesiones = new HashMap<>();
	private static Map<Long, Reto> mapaRetos = new HashMap<>();

	public List<SesionEntrenamiento> obtenerTodasSesiones() {
	    List<SesionEntrenamiento> sesionesOrdenadas = new ArrayList<>(mapaSesiones.values());
	    sesionesOrdenadas.sort((s1, s2) -> Long.compare(s1.getFechaIni(), s2.getFechaIni()));
	    int rango;
	    if (sesionesOrdenadas.size() < 5) {
	        rango = sesionesOrdenadas.size();
	    } else {
	        rango = 5;
	    }
	    List<SesionEntrenamiento> sesionesLimitadas  = new ArrayList<>();
	    for (int i = 0; i < rango; i++) {
	    	sesionesLimitadas .add(sesionesOrdenadas.get(i));
	    }
	    return sesionesLimitadas ;
	}


	public List<SesionEntrenamiento> obtenerSesionesUsuario(Usuario u) {
		List<SesionEntrenamiento> sesiones = new ArrayList<>();
		for (SesionEntrenamiento sesionEntrenamiento : mapaSesiones.values()) {
			if(sesionEntrenamiento.getUsuario().equals(u)) {
				sesiones.add(sesionEntrenamiento);
			}
		}
		return sesiones;
	}
	
	public List<SesionEntrenamiento> obtenerSesionesPorFecha(Usuario u, long fechaIni, long fechaFin){
		List<SesionEntrenamiento> sesionesFiltradas = new ArrayList<>();
		List<SesionEntrenamiento> sesiones = new ArrayList<>(this.obtenerSesionesUsuario(u));
		for (SesionEntrenamiento sesionEntrenamiento : sesiones) {
	        if (sesionEntrenamiento.getFechaIni() > fechaIni && sesionEntrenamiento.getFechaIni() < fechaFin) {
				sesionesFiltradas.add(sesionEntrenamiento);
			}
		}
		return sesionesFiltradas;
	}

	public SesionEntrenamiento obtenerSesion(long id) {
		return mapaSesiones.get(id);
	}
	
	public void aniadirSesion(Usuario u, String titulo, float distancia, long fechaIni, long horaIni, int duracion, String deporte) {
		SecureRandom aleatorio = new SecureRandom();
		SesionEntrenamiento sesion = new SesionEntrenamiento(aleatorio.nextLong(), titulo, distancia, fechaIni, horaIni, duracion, deporte, u);
		u.getlSesionesEntrenamiento().add(sesion);
		mapaSesiones.put(sesion.getIdSE(), sesion);
	}
	
	public List<SesionEntrenamiento> obtenerUltimas5Sesiones(String nombreUsu){
		List<SesionEntrenamiento> sesiones = new ArrayList<>();
		for (Long l : mapaSesiones.keySet()) {
			if(mapaSesiones.get(l).getUsuario().getNombreUsuario()==(nombreUsu)) {
				sesiones.add(mapaSesiones.get(l));
			}
		}
		List<SesionEntrenamiento> sesionesFiltradas = new ArrayList<>();
		sesiones.sort((s1, s2) ->Long.compare(s1.getFechaIni(), s2.getFechaIni()));
		int rango;
		if (sesiones.size() < 5) {
			rango = sesiones.size();
		}else {
			rango = 5;
		}
		for (int i = 0; i < rango; i++) {
			sesionesFiltradas.add(sesiones.get(i));
		}
		return sesionesFiltradas;
	}
	
	public void aniadirReto(Usuario creador, String nombre, long fechaIni, long fechaFin, float objetivo, String deporte, String tipoReto) {
		SecureRandom random = new SecureRandom();
		Reto reto = new Reto(random.nextLong(),nombre, fechaIni, fechaFin, objetivo, deporte, tipoReto, creador);
		creador.getlRetos().add(reto);
		mapaRetos.put(reto.getIdR(), reto);
	}
	
	public boolean aceptarReto(Usuario usuario, Reto reto) {
	    if (reto.getUsuarios().contains(usuario)) {
	        return false;
	    }

	    // Añadir el usuario al desafío y viceversa
	    reto.getUsuarios().add(usuario);
	    usuario.getlRetos().add(reto);

	    
	    return true; // Desafío aceptado con éxito
	}
	
	public List<Reto> obtenerTodosLosRetos() {
		return new ArrayList<>(mapaRetos.values());
	}
	
	public List<Reto> obtenerRetosUsuario(Usuario u) {
		List<Reto> retos = new ArrayList<>();
		for (Reto reto : mapaRetos.values()) {
			if(reto.getCreador().equals(u)) {
				retos.add(reto);
			}
		}
		return retos;
	}
	
	public List<Reto> obtenerRetosPorDeporte(Deporte deporte) {
		List<Reto> retos = new ArrayList<>();
		for (Reto reto : mapaRetos.values()) {
			if (reto.getDeporte().equals(deporte)) {
				retos.add(reto);
			}
		}
		return retos;
	}
	
	
	public List<Reto> obtenerRetosPorFecha(long fechaIni, long fechaFin) {
		List<Reto> retos = new ArrayList<>();
		for (Reto reto : mapaRetos.values()) {
			if (reto.getFechaIni() < reto.getFechaFin()) {
				retos.add(reto);
			}
		}
		return retos;
	}
	
	
	public List<Reto> obtener5RetosActivos() {
		List<Reto> ordenado = new ArrayList<>(mapaRetos.values());
		ordenado.sort((r1, r2) ->Long.compare(r1.getFechaIni(), r2.getFechaIni()));
		List<Reto> ordenadoActivo = new ArrayList<>();
		for (Reto reto : ordenado) {
            if (reto.estaActivo()) {
                ordenadoActivo.add(reto);
            }
        }
		List<Reto> retos = new ArrayList<>();
		int rango;
		if (ordenadoActivo.size() < 5) {
			rango = ordenadoActivo.size();
		}else {
			rango = 5;
		}
		for (int i = 0; i < rango; i++) {
		      retos.add(ordenadoActivo.get(i));  
		}  
        return retos;
	}
        

	public List<Reto> obtenerRetosAceptados(Usuario usuario) {
		List<Reto> retos = new ArrayList<>();
		for (Reto reto : usuario.getlRetos()) {
			if (reto.getUsuarios().contains(usuario)) {
				retos.add(reto);
			}
		}
		return retos;
	}
	
	public Reto obtenerReto(Long id) {
		return mapaRetos.get(id);
	}
	
}