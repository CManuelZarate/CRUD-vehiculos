package com.crudVehiculo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crudVehiculo.dto.VehiculoDTO;
import com.crudVehiculo.model.Usuario;
import com.crudVehiculo.model.Vehiculo;
import com.crudVehiculo.repository.IVehiculoRepository;

@Service
public class VehiculoServiceImpl implements IVehiculoService {
	
	@Autowired
    RestTemplate rt;
	
	@Autowired
	private IVehiculoRepository vehiculoRepository;

	@Override
	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.getVehiculos();
	}

	@Override
	public Vehiculo createVehiculo(VehiculoDTO vehiculoDto) {
		Vehiculo vehiculo = new Vehiculo(vehiculoDto);
		return vehiculoRepository.createVehiculo(vehiculo);
	}

	@Override
	public Vehiculo getVehiculoById(String idVehiculo) {
		return vehiculoRepository.getVehiculoById(idVehiculo);
	}

	@Override
	public Vehiculo updateVehiculo(VehiculoDTO vehiculoDto, String idVehiculo) {
		Vehiculo vehiculo = new Vehiculo(vehiculoDto);
		return vehiculoRepository.updateVehiculo(vehiculo, idVehiculo);
	}

	@Override
	public void deleteVehiculo(String idVehiculo) {
		vehiculoRepository.deleteVehiculo(idVehiculo);
	}

	@Override
	public List<Usuario> vehiculoxUsuario() {
		List <Usuario> us= rt.getForObject("http://microservicio-usuarios/v1/usuarios",List.class);
		System.out.println(us);
		return us;
	}

}
