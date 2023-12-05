package com.crudVehiculo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculoDTO {
	private String marca;
	private String modelo;
	private String placa;
	private Double precio;
}
