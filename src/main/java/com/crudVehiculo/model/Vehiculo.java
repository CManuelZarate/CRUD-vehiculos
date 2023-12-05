package com.crudVehiculo.model;

import com.crudVehiculo.dto.VehiculoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {
	private int id;
	private String marca;
	private String modelo;
	private String placa;
	private Double precio;

	public Vehiculo(VehiculoDTO vehiculoDTO) {
		this.marca = vehiculoDTO.getMarca();
		this.modelo =vehiculoDTO.getModelo();
		this.placa =vehiculoDTO.getPlaca();
		this.precio =vehiculoDTO.getPrecio();
	}
}
