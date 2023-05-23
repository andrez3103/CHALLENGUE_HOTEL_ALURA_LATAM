package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huespedes;

public class HuespedesDAO {
	
	private Connection con;

	public HuespedesDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huespedes huesped) {
		try{
			final PreparedStatement statement = con.prepareStatement(
				"INSERT INTO huespedes(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) "
				+ "VALUES(?,?,?,?,?,?)", 
						Statement.RETURN_GENERATED_KEYS);

			try(statement){
				ejecutaRegistro(huesped, statement);
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}				
	}

	private void ejecutaRegistro(Huespedes huesped, PreparedStatement statement) throws SQLException {
		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setDate(3,huesped.getFechaNacimiento());
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setInt(6, huesped.getIdReserva());

		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
			while (resultSet.next()) {
				huesped.setId(resultSet.getInt(1));
				System.out.println(String.format(
						"Fue insertada la Reserva %s", huesped));
			}			
		}
	}
	
	public int modificar(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idReserva) {
		try{
			final PreparedStatement statement = con.prepareStatement(
				"UPDATE huespedes SET nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono= ? WHERE id = ?", 
						Statement.RETURN_GENERATED_KEYS);

			try(statement){
				statement.setString(1,nombre);
				statement.setString(2, apellido);;
				statement.setDate(3,fechaNacimiento);
				statement.setString(4, nacionalidad); 
				statement.setString(5, telefono);
				statement.setInt(6, id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}	
	}
	
	public int eliminar(Integer id) {
		try{
			final PreparedStatement statement = con.prepareStatement(
				"DELETE FROM huespedes WHERE idReserva = ?", 
						Statement.RETURN_GENERATED_KEYS);

			try(statement){
				statement.setInt(1,id);
				
				statement.execute();
				
				try{
					final PreparedStatement statement2 = con.prepareStatement(
						"DELETE FROM reservas WHERE id = ?", 
								Statement.RETURN_GENERATED_KEYS);

					try(statement2){
						statement2.setInt(1,id);
						
						statement2.execute();
						
						int updateCount = statement.getUpdateCount();
						
						return updateCount;
					}
				} catch(SQLException e){
					throw new RuntimeException(e);
				}
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Huespedes> listar() {
		List<Huespedes> resultado = new ArrayList<>();
		
		try{
			final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes");
			
			try(statement){
				statement.execute();
				
				ResultSet resultSet = statement.getResultSet();
				
				
				while(resultSet.next()) {
					Huespedes fila = new Huespedes(resultSet.getInt("Id"),
							resultSet.getString("nombre"),
							resultSet.getString("apellido"),
							resultSet.getDate("fechaNacimiento"),
							resultSet.getString("nacionalidad"),
							resultSet.getString("telefono"),
							resultSet.getInt("idReserva"));
					
					resultado.add(fila);
				}			
				return resultado;				
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> listarPorApellido(String apellido) {
		List<Huespedes> huesped = new ArrayList<>();
		
		try{
			var querySelect = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes"
					+ " WHERE apellido = ?";
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try(statement){
				statement.setString(1, apellido);
				statement.execute();
				
				ResultSet resultSet = statement.getResultSet();
				
				while(resultSet.next()) {
					Huespedes fila = new Huespedes(resultSet.getInt("Id"),
							resultSet.getString("nombre"),
							resultSet.getString("apellido"),
							resultSet.getDate("fechaNacimiento"),
							resultSet.getString("nacionalidad"),
							resultSet.getString("telefono"),
							resultSet.getInt("idReserva"));
					
					huesped.add(fila);
				}			
				return huesped;				
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
