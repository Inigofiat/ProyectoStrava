package es.deusto.serverStrava.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.serverStrava.dto.RetoDTO;
import es.deusto.serverStrava.dto.SesionEntrenamientoDTO;
import es.deusto.serverStrava.entidad.Deporte;
import es.deusto.serverStrava.entidad.Reto;
import es.deusto.serverStrava.entidad.SesionEntrenamiento;
import es.deusto.serverStrava.entidad.Usuario;
import es.deusto.serverStrava.servicio.ServicioAutorizacion;
import es.deusto.serverStrava.servicio.ServicioStrava;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/strava")
@Tag(name = "Controlador Strava", description = "Operaciones relacionadas con la creacion y visualizacion de sesiones de entrenamiento y retos")
public class ControladorStrava {

	private final ServicioStrava servicioStrava;
	private final ServicioAutorizacion servicioAutorizacion;

	public ControladorStrava(ServicioStrava servicioStrava, ServicioAutorizacion servicioAutorizacion) {
		this.servicioStrava = servicioStrava;
		this.servicioAutorizacion = servicioAutorizacion;
	}

	//Obtener todas las sesiones de entrenamiento
	@Operation(
		summary = "Obtener todas las sesiones de entrenamiento",
		description = "Devuelve una lista de todas las sesiones de entrenamiento",
		responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de sesiones"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontaron sesiones de entrenamiento"),
			@ApiResponse(responseCode = "500", description = "Error del servidor")
		}
	)
	
	
	//Obtener sesiones de entrenamiento
	@GetMapping("/sesiones")
	public ResponseEntity<List<SesionEntrenamientoDTO>> obtenerSesionesEntrenamiento( 
		@Parameter (name = "Token", description = "Token del usuario", required = true)    	
		@RequestParam ("Token") String token) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<SesionEntrenamiento> sesionesEntrenamiento = servicioStrava.obtenerUltimas5Sesiones(usuario.getNombreUsuario());
		
		if (sesionesEntrenamiento.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<SesionEntrenamientoDTO> sesionesDTOs = sesionesEntrenamiento.stream().map(this::sesionesADTO).collect(Collectors.toList());
		return new ResponseEntity<>(sesionesDTOs, HttpStatus.OK);
		
	}
	
	//Crear sesion
	@Operation(summary = "Crear una sesion de entrenamiento", description = "Crea una sesion de entrenamiento", responses = {
			@ApiResponse(responseCode = "201", description = "OK: Sesion de entrenamiento creada correctamente"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token Invalido"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@PostMapping("/sesiones")
	public ResponseEntity<Void> crearSesionEntrenamiento(
			@Parameter(name = "Token", description = "Token del usuario", required = true) 
			@RequestParam("Token") String token,
			@Parameter(name = "SesionEntrenamientoDTO", description = "Informacion de la sesion de entrenamiento", required = true) 
			@RequestBody SesionEntrenamientoDTO sesionEntrenamientoDTO ){
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		servicioStrava.aniadirSesion(usuario, sesionEntrenamientoDTO.getTitulo(), sesionEntrenamientoDTO.getDistancia(), sesionEntrenamientoDTO.getFechaIni(), 
									sesionEntrenamientoDTO.getHoraIni(), sesionEntrenamientoDTO.getDuracion(), sesionEntrenamientoDTO.getDeporte());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Obtener sesiones por token usuario
	@Operation(summary = "Obtener sesiones de entrenamiento por token de usuario", description = "Devuelve una lista de sesiones de entrenamiento por usuario", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de sesiones de entrenamiento recibidas con exito"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontraron sesiones de entrenamiento"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido") })
	@GetMapping("/sesiones/usuario")
	public ResponseEntity<List<SesionEntrenamientoDTO>> obtenerSesionesPorTokenUsuario(
			@Parameter(name = "Token", description = "Token del usuario", required = true)
			@RequestParam("Token") String token) {
			
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<SesionEntrenamiento> sesiones = servicioStrava.obtenerSesionesUsuario(usuario);
		if (sesiones.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<SesionEntrenamientoDTO> sesionDTO = sesiones.stream().map(this::sesionesADTO).collect(Collectors.toList());
		return new ResponseEntity<>(sesionDTO, HttpStatus.OK);
	}
	
	
	//Obtener sesiones por fecha
	@Operation(summary = "Obtener sesiones de entrenamiento por fecha", description = "Devuelve una lista de sesioens de entrenamiento en un rango de fechas", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de sesiones de entrenamiento recividas con exito"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontraron sesiones de entrenamiento"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido") })
	@GetMapping("/sesiones/fecha")
	public ResponseEntity<List<SesionEntrenamientoDTO>> obtenerSesionesPorFecha(
			@Parameter(name = "Token", description = "Token del usuario", required = true)
			@RequestParam("Token") String token,
			@Parameter(name= "FechaInicio", description = "Fecha de inicio", required = true)
			@RequestParam("FechaInicio") long fechaIni,
			@Parameter(name= "FechaFinal", description = "Fecha de final", required = true)
			@RequestParam("FechaFinal") long fechaFin) {
			
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<SesionEntrenamiento> sessions = servicioStrava.obtenerSesionesPorFecha(usuario,fechaIni, fechaFin);
		if (sessions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<SesionEntrenamientoDTO> sessionDTOs = sessions.stream().map(this::sesionesADTO).collect(Collectors.toList());
		return new ResponseEntity<>(sessionDTOs, HttpStatus.OK);
	}

	//Obtener sesiones por token usuario
	@Operation(summary = "Obtener retos por token de usuario", description = "Devuelve una lista de retos por usuario", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de sesiones de entrenamiento recibidas con exito"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontraron sesiones de entrenamiento"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido") })
	@GetMapping("/retos/usuario")
	public ResponseEntity<List<RetoDTO>> obtenerRetosPorTokenUsuario(
			@Parameter(name = "Token", description = "Token del usuario", required = true)
			@RequestParam("Token") String token) {
					
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<Reto> retos = servicioStrava.obtenerRetosUsuario(usuario);
		if (retos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<RetoDTO> retoDTO = retos.stream().map(this::retosADTO).collect(Collectors.toList());
		return new ResponseEntity<>(retoDTO, HttpStatus.OK);
	}
	
	//Crear reto
	@Operation(summary = "Crear un reto", description = "Crea un nuevo reto", responses = {
			@ApiResponse(responseCode = "201", description = "Creado: Reto creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@PostMapping("/retos")
	public ResponseEntity<Void> crearReto(
				
			@Parameter(name = "Token", description = "Token de usuario", required = true) 
			@RequestParam("Token") String token,
			@Parameter(name = "RetoDTO", description = "Informacion de reto", required = true) 
			@RequestBody RetoDTO retoDTO) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		servicioStrava.aniadirReto(usuario, retoDTO.getNombre(), retoDTO.getFechaIni(), retoDTO.getFechaFin(), retoDTO.getObjetivo(),retoDTO.getDeporte(), retoDTO.getTipoReto());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	//Obtener retos activos
	@Operation(summary = "Obtener retos activos", description = "Devuelve una lista de retos activos", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de retos devuelta correctamente"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se han encontrado retos"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@GetMapping("/retos")
	public ResponseEntity<List<RetoDTO>> obtenerRetosActivos(
			@Parameter(name = "Token", description = "Token del usuario", required = true) 
			@RequestParam("Token") String token) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<Reto> retos = servicioStrava.obtener5RetosActivos();
		if (retos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<RetoDTO> retoDTO = retos.stream().map(this::retosADTO).collect(Collectors.toList());
		return new ResponseEntity<>(retoDTO, HttpStatus.OK);
	}
	
	//Obtener retos por deporte
	@Operation(summary = "Obtener retos por deporte", description = "Ddevuelve una lista de retos por deporte", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de retos devuelta correctamente"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontraron retos"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@GetMapping("/retos/deporte")
	public ResponseEntity<List<RetoDTO>> obtenerRetosPorDepote(
			
			@Parameter(name = "Token", description = "Token del usuario", required = true)
			@RequestParam("Token") String token,
			@Parameter(name = "DeporteStr", description = "Deporte", required = true)
			@RequestParam("DeporteStr") String deporteStr) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Deporte deporte = Deporte.valueOf(deporteStr);		
		List<Reto> retos = servicioStrava.obtenerRetosPorDeporte(deporte);
		if (retos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<RetoDTO> challengeDTOs = retos.stream().map(this::retosADTO).collect(Collectors.toList());
		return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);
	}
	
	//Obtener retos por fecha
	@Operation(summary = "Obtener retos por fecha", description = "Devuelve una lista de retos entre un rango de fechas", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de retos devuelta correctamente"),
			@ApiResponse(responseCode = "204", description = "No Conteido: No se encontraron retos"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@GetMapping("/retos/fecha")
	public ResponseEntity<List<RetoDTO>> obtenerRetosPorFecha(
			@Parameter(name = "Token", description = "Token del usuario", required = true) 
			@RequestParam("Token") String token,
			@Parameter(name= "FechaIni", description = "Fecha de inicio", required = true)
			@RequestParam("FechaIni") long fechaIni,
			@Parameter(name= "FechaFin", description = "Fecha de final", required = true)
			@RequestParam("FechaFin") long fechaFin) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<Reto> retos = servicioStrava.obtenerRetosPorFecha(fechaIni, fechaFin);
		if (retos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<RetoDTO> challengeDTOs = retos.stream().map(this::retosADTO).collect(Collectors.toList());
		return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);
	}
	
	//Aceptar reto
	@Operation(summary = "Aceptar reto", description = "Acepta un reto", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Reto aceptado correctamente"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido"),
			@ApiResponse(responseCode = "409", description = "Conflicto: Reto ya aceptado"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontro reto"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	
	@PutMapping("/retos/{retoIdR}")
	public ResponseEntity<Void> aceptarReto(
	        @PathVariable("idReto") long idR,
	        @RequestParam("Token") String token) {

	    Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
	    if (usuario == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }

	    Reto reto = servicioStrava.obtenerReto(idR);
	    if (reto == null) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    boolean aceptado = servicioStrava.aceptarReto(usuario, reto);
	    if (aceptado) {
	        return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }
	}
	
	//Obtener retos aceptados
	@Operation(summary = "Obtener retos aceptados", description = "Devulve una lista de retos acpetados", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Lista de retos devuelta correctamente"),
			@ApiResponse(responseCode = "204", description = "No Contenido: No se encontraron retos"),
			@ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido"),
			@ApiResponse(responseCode = "500", description = "Error del servidor") })
	@GetMapping("/retos/aceptados")
	public ResponseEntity<List<RetoDTO>> obtenerRetosAceptados(
			@Parameter(name = "Token", description = "Token del usuario", required = true) 
			@RequestParam("Token") String token) {
		Usuario usuario = servicioAutorizacion.obtenerUsuarioConToken(token);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<Reto> retos = usuario.getlRetos();
		if (retos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<RetoDTO> challengeDTOs = retos.stream().map(this::retosADTO).collect(Collectors.toList());
		return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);
	}
	

	private RetoDTO retosADTO(Reto reto) {
		return new RetoDTO(reto.getIdR(),reto.getNombre(), reto.getFechaIni(),
				 reto.getFechaFin(), reto.getObjetivo(), reto.getTipoReto().toString(),
				reto.getDeporte().toString());
	}	
	
	private SesionEntrenamientoDTO sesionesADTO(SesionEntrenamiento sesionEntrenamiento) {
		return new SesionEntrenamientoDTO(sesionEntrenamiento.getIdSE(),sesionEntrenamiento.getTitulo(),
				sesionEntrenamiento.getDistancia() ,sesionEntrenamiento.getFechaIni(),sesionEntrenamiento.getHoraIni(), 
				sesionEntrenamiento.getDuracion(), sesionEntrenamiento.getDeporte().toString(), sesionEntrenamiento.getUsuario().getEmail());
	}
}