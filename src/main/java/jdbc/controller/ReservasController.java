package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;


public class ReservasController {
	
	private ReservaDAO reservaDao;
	
	public ReservasController() {
		var factory = new ConnectionFactory();
		this.reservaDao = new ReservaDAO(factory.recuperarConexion());

	}
	
	public int modificar (Integer id, Date fechaE, Date fechaS, String Valor, String formaPago) {
		return reservaDao.modificar(id, fechaE, fechaS, Valor, formaPago);
	}
	
	public int eliminar(Integer id) {
		return reservaDao.eliminar(id);
	}
	
	public List<Reserva> listar(){
		return reservaDao.listar();
		
	}
	
	public void guardar(Reserva reserva) {
	    reservaDao.guardar(reserva);
	}
	
	public List<Reserva> listaPorId(Integer id){
		return this.reservaDao.listarPorId(id);
	}

}
