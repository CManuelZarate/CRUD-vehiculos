package com.crudVehiculo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.crudVehiculo.model.Vehiculo;

@ExtendWith(MockitoExtension.class)
public class VehiculoRepositoryTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private VehiculoRepository vehiculoRepository;
	
	@Test
	void getVehiculosTest() {
		Vehiculo vehiculo = new Vehiculo(1,"Honda","Accord","MMM-551",23500.50);
		List<Vehiculo> expectedVehiculos = new ArrayList<>();
		expectedVehiculos.add(vehiculo);
		
		when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
			.thenReturn(expectedVehiculos);
		
		List<Vehiculo> actualVehiculos = vehiculoRepository.getVehiculos();
		assertEquals(expectedVehiculos.size(), actualVehiculos.size());
		assertEquals(expectedVehiculos, actualVehiculos);
		
	}
	
	@Test
	void mapToSaleTest() throws SQLException  {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getInt("id")).thenReturn(1);
		when(rs.getString("marca")).thenReturn("Honda");
		when(rs.getString("modelo")).thenReturn("Accord");
		when(rs.getString("placa")).thenReturn("MMM-551");
		when(rs.getDouble("precio")).thenReturn(23500.50);
		
		Vehiculo actualVehiculo = vehiculoRepository.mapToVehiculo(rs);
		
		assertNotNull(actualVehiculo);
		assertEquals(1, actualVehiculo.getId());
		assertEquals(23500.50, actualVehiculo.getPrecio());
	}
	
	
	/*
	@Test
	void createVehiculoTest() {
        Vehiculo vehiculo = new Vehiculo(1,"Honda","Accord","MMM-551",23500.50);

        // Configurar el comportamiento simulado para SimpleJdbcInsert
        SimpleJdbcInsert simpleJdbcInsert = mock(SimpleJdbcInsert.class);
        when(simpleJdbcInsert.executeAndReturnKey(anyMap())).thenReturn(1);//SIMULAMOS CLAVE GENERADA

        // Configurar el comportamiento simulado para JdbcTemplate
        when(jdbcTemplate.getDataSource()).thenReturn(null);  // Puedes configurar un DataSource simulado según sea necesario

        // Inyectar el SimpleJdbcInsert simulado en tu clase de servicio
        vehiculoRepository.setSimpleJdbcInsert(simpleJdbcInsert);

        // Llamar al método en tu clase de servicio
        Vehiculo result = vehiculoRepository.createVehiculo(vehiculo);

        // Verificar el resultado
        assertEquals(1, result.getId()); // Ajusta según tu lógica de generación de clave primaria

        // Verificar que el SimpleJdbcInsert se llamó con los parámetros esperados
        verify(simpleJdbcInsert, times(1)).executeAndReturnKey(argThat(argument -> {
            // Verificar que el mapa de parámetros contiene las claves y valores esperados
            assertEquals(vehiculo.getMarca(), argument.get("marca"));
            assertEquals(vehiculo.getModelo(), argument.get("modelo"));
            assertEquals(vehiculo.getPlaca(), argument.get("placa"));
            assertEquals(vehiculo.getPrecio(), argument.get("precio"));
            return true;
        }));
	}*/
	
	
	@Test
	void deleteVehiculoTest() {
		/*
		String idVehiculo = "1";
        String SQL = "DELETE FROM Vehiculo WHERE id=?";
        
        // Configurar el comportamiento simulado para jdbcTemplate.update
        //when(jdbcTemplate.update(eq(SQL), any(Object[].class))).thenReturn(1);
        doReturn(1).when(jdbcTemplate).update(eq(SQL), any(Object[].class));
        // Llamar al método en tu clase de repositorio
        vehiculoRepository.deleteVehiculo(idVehiculo);

        // Verificar que jdbcTemplate.update fue llamado con los parámetros esperados
        verify(jdbcTemplate, times(1)).update(eq(SQL), any(Object[].class));
        */

		String idVehiculo = "1";
        String SQL = "DELETE FROM Vehiculo WHERE id=?";

        vehiculoRepository.deleteVehiculo(idVehiculo);

        // Verificar que jdbcTemplate.update fue llamado con los parámetros esperados
        verify(jdbcTemplate).update(eq(SQL), eq(idVehiculo));
		
	}
	
	@Test
    void updateVehiculoTest() {
        String idVehiculo = "1";
        Vehiculo vehiculo = new Vehiculo(1,"Honda","Accord","MMM-551",23500.50);

        String SQL = "UPDATE Vehiculo SET marca=?,modelo=?,placa=?,precio=? WHERE id=?";
        
        // Configurar el comportamiento simulado para jdbcTemplate.update
        when(jdbcTemplate.update(eq(SQL), any(Object[].class))).thenReturn(1);

        // Configurar el comportamiento simulado para getVehiculoById
        when(vehiculoRepository.getVehiculoById(idVehiculo)).thenReturn(vehiculo);

        // Llamar al método en tu clase de repositorio
        Vehiculo result = vehiculoRepository.updateVehiculo(vehiculo, idVehiculo);

        // Verificar el resultado
        assertEquals(vehiculo, result);

        // Verificar que jdbcTemplate.update fue llamado con los parámetros esperados
        verify(jdbcTemplate).update(eq(SQL), any(Object[].class));
    }
	
	
}
