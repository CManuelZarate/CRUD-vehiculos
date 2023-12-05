package com.crudVehiculo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.crudVehiculo.model.Vehiculo;
import com.crudVehiculo.service.IVehiculoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/vehiculos")
@Slf4j
public class VehiculoController {
	@Autowired
	private IVehiculoService vehiculoService;
	
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
	public ResponseEntity<?> updateVehiculo(@RequestBody VehiculoDTO userDto, @PathVariable String idVehiculo){
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
}
