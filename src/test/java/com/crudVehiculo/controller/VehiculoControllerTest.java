package com.crudVehiculo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.crudVehiculo.dto.VehiculoDTO;
import com.crudVehiculo.model.Vehiculo;
import com.crudVehiculo.service.IVehiculoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IVehiculoService vehiculoService;
	
	@InjectMocks
    private VehiculoController vehiculoController;
	
	@Test
	public void getVehiculosOkTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		Vehiculo v1 = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);
		
		List<Vehiculo> expectedListVehiculos = new ArrayList<>();
		expectedListVehiculos.add(v1);
		
		int expectedStatus = 200;
		String uri = "/v1/vehiculos";
		
		when(vehiculoService.getVehiculos()).thenReturn(expectedListVehiculos);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		
		int actualStatus = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		List<Vehiculo> actualVehiculos = mapper.readValue(content,
					new TypeReference<List<Vehiculo>>() {});

		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedListVehiculos, actualVehiculos);
		assertEquals(expectedListVehiculos.size(), actualVehiculos.size());
	}
	
	@Test
	public void getVehiculosExceptionTest() throws Exception {
		int expectedStatus = 500;
		String uri = "/v1/vehiculos";
        when(vehiculoService.getVehiculos()).thenThrow(new RuntimeException("Internal Server Error"));
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
	}
	
	@Test
	public void createVehiculoTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Vehiculo expectedVehiculo = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);
		
		int expectedStatus = 201;
		String uri = "/v1/vehiculos";
		
		VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);

	    
		String bodyString = mapper.writeValueAsString(vehiculoDTO);
			
		
		when(vehiculoService.createVehiculo(any(VehiculoDTO.class))).thenReturn(expectedVehiculo);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyString)).andReturn();
		
		int actualStatus = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		Vehiculo actualVehiculo = mapper.readValue(content, Vehiculo.class);


		assertEquals(expectedStatus, actualStatus);
		assertEquals(actualVehiculo.getId(), expectedVehiculo.getId());
		assertEquals(actualVehiculo.getMarca(), expectedVehiculo.getMarca());
		assertEquals(actualVehiculo.getModelo(), expectedVehiculo.getModelo());
		assertEquals(actualVehiculo.getPlaca(), expectedVehiculo.getPlaca());
		assertEquals(actualVehiculo.getPrecio(), expectedVehiculo.getPrecio());
	}
	
	@Test
	public void createSaleExceptionTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		int expectedStatus = 500;
		String uri = "/v1/vehiculos";
		
		VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);

	    String bodyString = mapper.writeValueAsString(vehiculoDTO);
	    
        when(vehiculoService.createVehiculo(any(VehiculoDTO.class))).thenThrow(new RuntimeException("Internal Server Error"));
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(bodyString))
        		.andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
	}
	
	@Test
	public void getVehiculoByIdTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Vehiculo expectedVehiculo = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);
		
		int expectedStatus = 200;
		String uri = "/v1/vehiculos/1";

		
		when(vehiculoService.getVehiculoById(anyString())).thenReturn(expectedVehiculo);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		int actualStatus = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		Vehiculo actualVehiculo = mapper.readValue(content, Vehiculo.class);

		assertEquals(expectedStatus, actualStatus);
		assertEquals(actualVehiculo.getId(), expectedVehiculo.getId());
		assertEquals(actualVehiculo.getMarca(), expectedVehiculo.getMarca());
		assertEquals(actualVehiculo.getModelo(), expectedVehiculo.getModelo());
		assertEquals(actualVehiculo.getPlaca(), expectedVehiculo.getPlaca());
		assertEquals(actualVehiculo.getPrecio(), expectedVehiculo.getPrecio());
	}
	
	@Test
    void getVehiculoByIdExceptionTest() throws Exception {
        
		ObjectMapper mapper = new ObjectMapper();
		 String idVehiculo = "1";
		
		int expectedStatus = 500;
		String uri = "/v1/vehiculos/1";
		
		VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);

	    String bodyString = mapper.writeValueAsString(vehiculoDTO);
        when(vehiculoService.getVehiculoById(anyString())).thenThrow(new RuntimeException("Internal Server Error"));
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri,idVehiculo)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(bodyString))
        		.andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }
	
	
	@Test
	public void updateVehiculoTest()  throws Exception{
		
        String idVehiculo = "1";
        VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);
        Vehiculo vehiculoMock = new Vehiculo(1,"Honda","Accord","AZY-551",23500.50);

        when(vehiculoService.updateVehiculo(any(VehiculoDTO.class), eq(idVehiculo))).thenReturn(vehiculoMock);

        
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/vehiculos/{idVehiculo}", idVehiculo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vehiculoDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vehiculoMock.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca").value(vehiculoMock.getMarca()));
        
	}
	
	private String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
	
	
	
	
	
	@Test
    void updateVehiculoExceptionTest() throws Exception {
        
		ObjectMapper mapper = new ObjectMapper();
		 String idVehiculo = "1";
		
		int expectedStatus = 500;
		String uri = "/v1/vehiculos/1";
		
		VehiculoDTO vehiculoDTO = new VehiculoDTO("Honda","Accord","AZY-551",23500.50);

	    String bodyString = mapper.writeValueAsString(vehiculoDTO);
        when(vehiculoService.updateVehiculo(any(VehiculoDTO.class),eq(idVehiculo))).thenThrow(new RuntimeException("Internal Server Error"));
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri,idVehiculo)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(bodyString))
        		.andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }
	
	
	
	@Test
    void deleteVehiculoTest() throws Exception {
		
        String idVehiculo = "1";

        
        mockMvc.perform(delete("/v1/vehiculos/{idVehiculo}", idVehiculo))
                .andExpect(status().isNoContent());

        verify(vehiculoService).deleteVehiculo(idVehiculo);
    }
	
	@Test
    void deleteVehiculoErrorTest() throws Exception {
        
        String idVehiculo = "1";

        doThrow(new RuntimeException("Simulación de error en el servicio"))
        .when(vehiculoService).deleteVehiculo(anyString());

        mockMvc.perform(delete("/v1/vehiculos/{idVehiculo}", idVehiculo))
                .andExpect(status().isInternalServerError());
    }
}
