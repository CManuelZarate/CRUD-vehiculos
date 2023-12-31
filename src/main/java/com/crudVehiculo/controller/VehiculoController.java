package com.crudVehiculo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudVehiculo.dto.VehiculoDTO;
import com.crudVehiculo.model.Usuario;
import com.crudVehiculo.model.Vehiculo;
import com.crudVehiculo.service.IVehiculoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/vehiculos")
@Slf4j
public class VehiculoController {
	@Autowired
	private IVehiculoService vehiculoService;
	
	@Value("${config.balanceador.test}")
	private String balanceadorTest;
	
	@GetMapping("")
	public ResponseEntity<List<Vehiculo>> getVehiculos(){
		try {
			List<Vehiculo> vehiculos = vehiculoService.getVehiculos();			
			return ResponseEntity.status(HttpStatus.OK).body(vehiculos);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<Map<String, Object>> balanceador(){
		try {
			List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
			Map<String,Object> response = new HashMap<String,Object>();
			response.put("balanceador", balanceadorTest);
			response.put("vehiculos", vehiculos);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("")
	@Transactional
	public ResponseEntity<Vehiculo> createVehiculo(@RequestBody VehiculoDTO vehiculoDto){
		try {
			Vehiculo vehiculo = vehiculoService.createVehiculo(vehiculoDto);
			log.info("Vehículo creado");
			return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{idVehiculo}")
	public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable String idVehiculo){
		try {
			Vehiculo vehiculo = vehiculoService.getVehiculoById(idVehiculo);
			return ResponseEntity.status(HttpStatus.OK).body(vehiculo);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{idVehiculo}")
	@Transactional
	public ResponseEntity<Vehiculo> updateVehiculo(@RequestBody VehiculoDTO userDto, @PathVariable String idVehiculo){
		try {
			Vehiculo vehiculo = vehiculoService.updateVehiculo(userDto, idVehiculo);
			return ResponseEntity.status(HttpStatus.OK).body(vehiculo);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/{idVehiculo}")
	@Transactional
	public ResponseEntity<?> deleteVehiculo(@PathVariable String idVehiculo){
		try {
			vehiculoService.deleteVehiculo(idVehiculo);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> usuariosVehiculo() {
		try {
			List<Usuario> usuarios = vehiculoService.vehiculoxUsuario();		
			return ResponseEntity.status(HttpStatus.OK).body(usuarios);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
    }
	
	@GetMapping("/usuarios/feign")
	public ResponseEntity<List<Usuario>> usuariosVehiculoFeign() {
		try {
			List<Usuario> usuarios = vehiculoService.vehiculoxUsuarioxFeign();		
			return ResponseEntity.status(HttpStatus.OK).body(usuarios);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
    }
}
