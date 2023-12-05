package com.crudVehiculo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crudVehiculo.dto.VehiculoDTO;
import com.crudVehiculo.model.Vehiculo;
import com.crudVehiculo.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {
	@Mock
	VehiculoRepository vehiculoRepository;
	
	@InjectMocks
	VehiculoServiceImpl vehiculoServiceImpl;
	
	@Test
	public void getVehiculosTest() {
		
		Vehiculo vehiculo = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);
		List<Vehiculo> listVehiculo = new ArrayList<>();
		listVehiculo.add(vehiculo);
		
		when(vehiculoRepository.getVehiculos()).thenReturn(listVehiculo);
		
		List<Vehiculo> actual = vehiculoServiceImpl.getVehiculos();
		assertEquals(actual, listVehiculo);
		assertEquals(actual.size(),listVehiculo.size());
		
	}
	
	@Test
	public void createVehiculoTest() {
		
		VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);
	
		
		Vehiculo expectedVehiculo = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);
		
		when(vehiculoRepository.createVehiculo(any(Vehiculo.class))).thenReturn(expectedVehiculo);
		
		Vehiculo actualVehiculo = vehiculoServiceImpl.createVehiculo(vehiculoDTO);
		assertEquals(expectedVehiculo,actualVehiculo);
		
	}
	
	
	@Test
	public void getVehiculoByIdTest() {
		String id = "1";
		Vehiculo expectedVehiculo = new Vehiculo(Integer.parseInt(id),"Honda","Accord","MMM-551",23500.50);
		
		when(vehiculoRepository.getVehiculoById(id)).thenReturn(expectedVehiculo);
		
		Vehiculo actualVehiculo = vehiculoServiceImpl.getVehiculoById(id);
		assertEquals(expectedVehiculo,actualVehiculo);
	}
	
	@Test
	public void updateVehiculoTest() {
        String idVehiculo = "1";
        VehiculoDTO vehiculoDto = new VehiculoDTO("Honda","Accord","MMM-551",23500.50);
        Vehiculo vehiculoMock = new Vehiculo(vehiculoDto);
        
        when(vehiculoRepository.updateVehiculo(any(Vehiculo.class), eq(idVehiculo))).thenReturn(vehiculoMock);

        Vehiculo actualVehiculo = vehiculoServiceImpl.updateVehiculo(vehiculoDto, idVehiculo);

        assertEquals(vehiculoMock, actualVehiculo);

        verify(vehiculoRepository, times(1)).updateVehiculo(any(Vehiculo.class), eq(idVehiculo));
	}
	
	
	@Test
	public void deleteVehiculoTest() {
		String idVehiculo = "1";
		vehiculoServiceImpl.deleteVehiculo(idVehiculo);
        verify(vehiculoRepository, times(1)).deleteVehiculo(eq(idVehiculo));
	}
	
	
}
