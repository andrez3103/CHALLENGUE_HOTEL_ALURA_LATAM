package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesController {
	
	private HuespedesDAO huespedesDao;
	
	public HuespedesController() {
		var factory = new ConnectionFactory();
		this.huespedesDao = new HuespedesDAO(factory.recuperarConexion());
	}
	
	public int modificar (Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idReserva) {
		return huespedesDao.modificar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
	}
	
	public int eliminar(Integer id) {
		return huespedesDao.eliminar(id);
	}

	public void guardar(Huespedes huesped){
		huespedesDao.guardar(huesped);
	}

	public List<Huespedes> listar(){
		return huespedesDao.listar();
	}

	public List<Huespedes> listarPorApellido(String apellido){
		return this.huespedesDao.listarPorApellido(apellido);
	}


}
