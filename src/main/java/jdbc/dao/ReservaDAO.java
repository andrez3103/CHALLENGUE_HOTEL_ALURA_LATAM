package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Reserva reserva) {
		try{
			final PreparedStatement statement = con.prepareStatement(
				"INSERT INTO reservas(fechaEntrada, fechaSalida, valor, formaPago) "
				+ "VALUES(?,?,?,?)", 
						Statement.RETURN_GENERATED_KEYS);

			try(statement){
				ejecutaRegistro(reserva, statement);
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}				
	}

	private void ejecutaRegistro(Reserva reserva, PreparedStatement statement)throws SQLException {
		statement.setDate(1, reserva.getFechaE());
		statement.setDate(2, reserva.getFechaS());
		statement.setString(3,reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
			while (resultSet.next()) {
				reserva.setId(resultSet.getInt(1));
				System.out.println(String.format(
						"Fue insertada la Reserva %s", reserva));
			}			
		}
	}
	
	public int modificar(Integer id, Date fechaE, Date fechaS, String Valor, String formaPago) {
		try{
			final PreparedStatement statement = con.prepareStatement(
				"UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?", 
						Statement.RETURN_GENERATED_KEYS);

			try(statement){
				statement.setDate(1,fechaE);
				statement.setDate(2, fechaS);;
				statement.setString(3,Valor);
				statement.setString(4, formaPago); 
				statement.setInt(5, id);
				
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
	
	public List<Reserva> listar() {
		List<Reserva> resultado = new ArrayList<>();
		
		try{
			final PreparedStatement statement = con.prepareStatement("SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas");
			
			try(statement){
				statement.execute();
				
				ResultSet resultSet = statement.getResultSet();
				
				
				while(resultSet.next()) {
					Reserva fila = new Reserva(resultSet.getInt("Id"),
							resultSet.getDate("fechaEntrada"),
							resultSet.getDate("fechaSalida"),
							resultSet.getString("valor"),
							resultSet.getString("formaPago"));
					
					resultado.add(fila);
				}			
				return resultado;				
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> listarPorId(Integer id) {
		List<Reserva> reserva = new ArrayList<>();
		
		try{
			var querySelect = "SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas"
					+ " WHERE id = ?";
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				ResultSet resultSet = statement.getResultSet();
				
				while(resultSet.next()) {
					Reserva fila = new Reserva(resultSet.getInt("id"),
							resultSet.getDate("fechaEntrada"),
							resultSet.getDate("fechaSalida"),
							resultSet.getString("valor"),
							resultSet.getString("formaPago"));
					
					reserva.add(fila);
				}			
				return reserva;				
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
