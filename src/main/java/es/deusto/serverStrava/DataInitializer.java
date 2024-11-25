package es.deusto.serverStrava;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.serverStrava.entidad.Deporte;
import es.deusto.serverStrava.entidad.TipoReto;
import es.deusto.serverStrava.entidad.Usuario;
import es.deusto.serverStrava.servicio.ServicioAutorizacion;
import es.deusto.serverStrava.servicio.ServicioStrava;

@Configuration
public class DataInitializer {
    
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	
	@Bean
	 CommandLineRunner initData(ServicioStrava servicioStrava, ServicioAutorizacion servicioAutorizacion) {
	 return args -> {
		 
		 Usuario u1 = new Usuario("Nicolás", "nicKo-626", "nico.p.cueva@opendeusto.es", 1078099200, 67.5f, 181, 180, 70, new ArrayList<>(), new ArrayList<>());
		 Usuario u2 = new Usuario("Iñigo", "GioFiat", "inigo.fiat@opendeusto.es", 2000000, 62, 171, 180, 70, new ArrayList<>(), new ArrayList<>());
		 
		
		 
		 servicioAutorizacion.aniadirUsuario(u1);
		 servicioAutorizacion.aniadirUsuario(u2);
		 
		 logger.info("Usuarios creados");
		 logger.info("Usuario 1: " + u1.getNombreUsuario()+u1.getEmail());
		 logger.info("\n"+ servicioAutorizacion.obtenerUsuarioPorEmail("2"));
		 
		 
		 servicioStrava.aniadirReto(u1, "Reto 1", 1000000, 2000000, 90,  TipoReto.Tiempo.toString(), Deporte.Running.toString());
		 servicioStrava.aniadirReto(u2, "Reto 2", 2000000, 4000000, 42.195f, TipoReto.Distancia.toString(), Deporte.Ciclismo.toString());
		 servicioStrava.aniadirReto(u1, "Reto 3", 3000000, 5000000, 120, TipoReto.Tiempo.toString(), Deporte.Running.toString());
		 
		 servicioStrava.aniadirSesion(u1, "Session 1", 40f, 1736928000, 1736980200, 90, Deporte.Running.toString());
		 servicioStrava.aniadirSesion(u1, "Sesion 2", 10f, 1737000000, 1737003600, 50, Deporte.Running.toString());
		 servicioStrava.aniadirSesion(u1, "Sesion 3", 25.5f, 1737010800, 1737018000, 120, Deporte.Ciclismo.toString());
		 servicioStrava.aniadirSesion(u1, "Sesion 4", 2.0f, 1737021600, 1737023400, 30, Deporte.Ciclismo.toString());
		 servicioStrava.aniadirSesion(u2, "Session 5", 50f, 1736928001, 1736980201, 70, Deporte.Running.toString());

	};

	}
}