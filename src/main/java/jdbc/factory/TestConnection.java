package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperarConexion();
		
		System.out.println("Cerrando conexion");
		
		con.close();
		
	}
	

}
