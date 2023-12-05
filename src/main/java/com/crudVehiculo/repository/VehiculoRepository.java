package com.crudVehiculo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.crudVehiculo.model.Vehiculo;

@Repository
public class VehiculoRepository implements IVehiculoRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Vehiculo> getVehiculos() {
		String SQL = "SELECT * FROM Vehiculo";
		List<Vehiculo> vehiculos = jdbcTemplate.query(SQL, 
				(rs, rowNum) -> mapToVehiculo(rs));
		return vehiculos;
	}
	
	public Vehiculo mapToVehiculo(ResultSet rs) throws SQLException {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(rs.getInt("id"));
		vehiculo.setMarca(rs.getString("marca"));
		vehiculo.setModelo(rs.getString("modelo"));
		vehiculo.setPlaca(rs.getString("placa"));
		vehiculo.setPrecio(rs.getDouble("precio"));

		return vehiculo;
	}

	@Override
	public Vehiculo createVehiculo(Vehiculo vehiculo) {
		SimpleJdbcInsert simpleJdbcInsert =
	            new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("Vehiculo")
                .usingGeneratedKeyColumns("id");
		
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("marca", vehiculo.getMarca());
	    parameters.put("modelo", vehiculo.getModelo());
	    parameters.put("placa", vehiculo.getPlaca());
	    parameters.put("precio", vehiculo.getPrecio());

	    int id = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
	    vehiculo.setId(id);
	    return vehiculo;
		
	}

	@Override
	public Vehiculo getVehiculoById(String idVehiculo) {
		String sql = "SELECT * FROM Vehiculo WHERE id = ?";
		Vehiculo vehiculo = jdbcTemplate.queryForObject(sql, 
				(rs, rowNum) -> mapToVehiculo(rs),idVehiculo);
		return vehiculo;
	}

	@Override
	public Vehiculo updateVehiculo(Vehiculo vehiculo, String idVehiculo) {
		String SQL = "UPDATE Vehiculo SET marca=?,modelo=?,placa=?,precio=? WHERE id=?";
		jdbcTemplate.update(SQL, 
			 new Object[] { vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getPlaca(),vehiculo.getPrecio(), idVehiculo });
		return getVehiculoById(idVehiculo);
	}

	@Override
	public void deleteVehiculo(String idVehiculo) {
		String SQL = "DELETE FROM Vehiculo WHERE id=?";
		jdbcTemplate.update(SQL, new Object[] { idVehiculo });
	}

}
