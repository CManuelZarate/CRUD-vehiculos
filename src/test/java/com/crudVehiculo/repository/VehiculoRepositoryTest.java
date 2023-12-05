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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.crudVehiculo.model.Vehiculo;

@ExtendWith(MockitoExtension.class)
public class VehiculoRepositoryTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Mock
	private SimpleJdbcInsert simpleJdbcInsert;
	
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
	
	
	@Test
	void getVehiculoByIdTest() {
		//Expected
		Vehiculo vehiculo = new Vehiculo(1,"Honda","Accord","MMM-551",23500.50);
		when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class),
				anyString())).thenReturn(vehiculo);
		
		Vehiculo actualVehiculo = vehiculoRepository.getVehiculoById("1");
		
		assertNotNull(actualVehiculo);
		assertEquals(actualVehiculo.getMarca(), vehiculo.getMarca());
		assertEquals(actualVehiculo.getId(), vehiculo.getId());
		
	}
	
	
	@Test
	void deleteVehiculoTest() {

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
	
	    vehiculoRepository.updateVehiculo(vehiculo,idVehiculo);
	    
	 // Assert
	    ArgumentCaptor<Object> arg1 = ArgumentCaptor.forClass(Object.class);
	    ArgumentCaptor<Object> arg2 = ArgumentCaptor.forClass(Object.class);
	    ArgumentCaptor<Object> arg3 = ArgumentCaptor.forClass(Object.class);
	    ArgumentCaptor<Object> arg4 = ArgumentCaptor.forClass(Object.class);
	    ArgumentCaptor<Object> arg5 = ArgumentCaptor.forClass(Object.class);

	    verify(jdbcTemplate).update(eq("UPDATE Vehiculo SET marca=?,modelo=?,placa=?,precio=? WHERE id=?"),
	            arg1.capture(), arg2.capture(), arg3.capture(), arg4.capture(), arg5.capture());

	    assertEquals("Honda", arg1.getValue());
	    assertEquals("Accord", arg2.getValue());
	    assertEquals("MMM-551", arg3.getValue());
	    assertEquals(23500.50, arg4.getValue());
	    assertEquals(idVehiculo, arg5.getValue()); 
	}
	
	

	
	@Test
	void createVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo(2,"Honda","Accord","MMM-551",23500.50);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Mockear la consulta SQL
        when(jdbcTemplate.update(any(PreparedStatementCreator.class),
        		any(KeyHolder.class)))
        	.thenAnswer(invocation -> {//se llame al método update, se debe ejecutar la lógica proporcionada en el bloque
	            PreparedStatement ps = mock(PreparedStatement.class);
	         
	            // Configurar un objeto ResultSet mock para ser devuelto por la llamada a getGeneratedKeys()
	            ResultSet rs = mock(ResultSet.class);//mock para representar resultados de la consulta
	            //Esto simula la obtención de claves generadas automáticamente después de ejecutar una consulta SQL 
	            
	            return 1;//numero de filas afectadas valor simulado
        	});

        // Llamar al método de prueba
        Vehiculo result = vehiculoRepository.createVehiculo(vehiculo);
        assertEquals(result, vehiculo);
        // Verificar que el objeto retornado no sea nulo y que tenga el ID generado
        assertNotNull(vehiculo);
        assertNotNull(vehiculo.getId());
	}
	
	

}
