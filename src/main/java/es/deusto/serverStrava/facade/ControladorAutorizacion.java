/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.serverStrava.facade;

import java.util.Optional; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.serverStrava.dto.CredencialesDTO;
import es.deusto.serverStrava.dto.UsuarioDTO;
import es.deusto.serverStrava.entidad.Usuario;
import es.deusto.serverStrava.servicio.ServicioAutorizacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Controlador de autorizacion", description = "Operaciones de login y logout")
public class ControladorAutorizacion {

    private ServicioAutorizacion servicioAutorizacion;
    
	public ControladorAutorizacion(ServicioAutorizacion servicioAutorizacion) {
		this.servicioAutorizacion = servicioAutorizacion;
	}
	
	//Registrar un nuevo usuario
	@Operation(summary = "Registrar nuevo usuario", description = "Permite a un usuario registrarse", responses = {
			@ApiResponse(responseCode = "201", description = "OK: Usuario registrado correctamente"),
			@ApiResponse(responseCode = "400", description = "Peticion No Valida: Email invalido") })
	@PostMapping("/registrar")
	public ResponseEntity<Void> registrar(
			@Parameter(description = "Credenciales del usuario", required = true) 
			@RequestBody UsuarioDTO usuarioDTO) {

		// Comprobar si el email ya está en uso
		if (servicioAutorizacion.obtenerUsuarioPorEmail(usuarioDTO.getEmail()) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		//Validate email using Facebook or Google API
		
		
		// Crear un nuevo usuario y guardarlo
		servicioAutorizacion.aniadirUsuario(usuarioADTO(usuarioDTO));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
    
    // Login endpoint
    @Operation(
        summary = "Iniciar sesión",
        description = "Permite al usuario iniciar sesión a través del email.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Inicio de sesión correcto."),
            @ApiResponse(responseCode = "401", description = "No Autorizado: Credenciales no válidas"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
    		@Parameter(name = "Credenciales", description = "Credenciales del usuario", required = true)    	
    		@RequestBody CredencialesDTO credentials) {    	
        Optional<String> token = servicioAutorizacion.login(credentials.getEmail(), credentials.getContrasenia());
        
    	if (token.isPresent()) {
    		return new ResponseEntity<>(token.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }

    // Logout endpoint
    @Operation(
        summary = "Cerrar sesión",
        description = "Permite al usuario cerrar sesión.",
        responses = {
            @ApiResponse(responseCode = "204", description = "OK: Cierre de sesión correcto"),
            @ApiResponse(responseCode = "401", description = "No Autorizado: Token invalido"),
        }
    )    
    @PostMapping("/logout")    
    public ResponseEntity<Void> logout(
    		@Parameter(name = "Token", description = "Token del usuario", required = true)
    		@RequestBody String token) {    	
        Optional<Boolean> result = servicioAutorizacion.logout(token);
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
    }
    
    public Usuario usuarioADTO(UsuarioDTO u) {
    	Usuario usuario = new Usuario();
    	usuario.setAlturaCm(u.getAlturaCm());
    	usuario.setEmail(u.getEmail());
    	usuario.setFechaNac(u.getFechaNac());
    	usuario.setFrecuenciaMax(u.getFrecuenciaMax());
    	usuario.setFrecuenciaReposo(u.getFrecuenciaReposo());
    	usuario.setNombre(u.getNombre());
    	usuario.setNombreUsuario(u.getNombreUsuario());
    	usuario.setPesoKg(u.getPesoKg());
    	return usuario;
    }
    
 
}