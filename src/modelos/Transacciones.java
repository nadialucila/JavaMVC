package modelos;

import java.sql.Date;

public class Transacciones {
	//atributos
	private int id_transaccion;
	private int id_caja;
	private double monto;
	private String movimiento;
	private Date fecha;
	
	//Constructor
	public Transacciones(int id_transaccion, int id_caja, double monto, String movimiento, Date fecha) {
		this.id_caja = id_caja;
		this.monto = monto;
		this.movimiento = movimiento;
		this.fecha = fecha;
	}
	public Transacciones() {
	}
	
	//getters y setters
	public int getId_transaccion() {
		return id_transaccion;
	}
	public void setId_transaccion(int id_transaccion) {
		this.id_transaccion = id_transaccion;
	}
	public int getId_caja() {
		return id_caja;
	}
	public void setId_caja(int id_caja) {
		this.id_caja = id_caja;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date date) {
		this.fecha = date;
	}
	
	
}
