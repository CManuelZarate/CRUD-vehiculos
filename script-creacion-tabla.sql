USE vehiculos;

CREATE TABLE Vehiculo(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(255),
    modelo VARCHAR(255),
    placa VARCHAR(255),
    precio DECIMAL(19,2)
);