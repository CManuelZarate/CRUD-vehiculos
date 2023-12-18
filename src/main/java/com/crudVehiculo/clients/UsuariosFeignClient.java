package com.crudVehiculo.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.crudVehiculo.model.Usuario;


@FeignClient(name = "microservicio-usuarios")
public interface UsuariosFeignClient {
	
	@GetMapping("/v1/usuarios")
	public List<Usuario> getUsuarios();
	
	
	
}
