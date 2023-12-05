package com.crudVehiculo.repository;

import java.util.List;

import com.crudVehiculo.model.Vehiculo;

public interface IVehiculoRepository {
	public List<Vehiculo> getVehiculos();
	public Vehiculo createVehiculo(Vehiculo vehiculo);
	public Vehiculo getVehiculoById(String idVehiculo);
	public Vehiculo updateVehiculo(Vehiculo vehiculo, String idVehiculo);
	public void deleteVehiculo(String idVehiculo);
}
