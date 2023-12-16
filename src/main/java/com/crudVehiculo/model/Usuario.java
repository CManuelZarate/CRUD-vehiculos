package com.crudVehiculo.model;

import java.util.Date;

import lombok.Data;

@Data
public class Usuario{


	private Long id;
	private String nombres;
	private String apellidos;
	private String correo;
	private Date createAt;
	

	

}
