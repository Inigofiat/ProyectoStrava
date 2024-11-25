package es.deusto.serverStrava.servicio;

import org.springframework.stereotype.Service;

import es.deusto.serverStrava.entidad.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioAutorizacion {

    private static Map<String, Usuario> reposUsu = new HashMap<>();    
    private static Map<String, Usuario> almacenToken = new HashMap<>(); 

    public Optional<String> login(String email, String password) {
        Usuario usuario = reposUsu.get(email);
        
        if (usuario != null) {
            String token = generarToken(); 
            almacenToken.put(token, usuario);    

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }
    
    public Optional<Boolean> logout(String token) {
        if (almacenToken.containsKey(token)) {
        	almacenToken.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    public void aniadirUsuario(Usuario usuario) {
    	if (usuario != null) {
    		reposUsu.putIfAbsent(usuario.getEmail(), usuario);
    	}
    }
    
    public Usuario obtenerUsuarioConToken(String token) {
        return almacenToken.get(token); 
    }
    
    public Usuario obtenerUsuarioPorEmail(String email) {
		return reposUsu.get(email);
	}

    private static synchronized String generarToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}