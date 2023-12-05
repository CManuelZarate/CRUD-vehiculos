package com.crudVehiculo.service;

import java.util.List;

import com.crudVehiculo.dto.VehiculoDTO;
import com.crudVehiculo.model.Vehiculo;

public interface IVehiculoService {
	public List<Vehiculo> getVehiculos();
	public Vehiculo createVehiculo(VehiculoDTO vehiculoDto);
	public Vehiculo getVehiculoById(String idVehiculo);
	public Vehiculo updateVehiculo(VehiculoDTO vehiculoDto, String idVehiculo);
	public void deleteVehiculo(String idVehiculo);
}
