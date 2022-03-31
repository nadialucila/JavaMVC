package modelos;

public class CajaAhorro {
	//atributos
	private int id_usuario;
	private int id_caja;
	private double monto;

	//constructor
	public CajaAhorro() {
	}
	public CajaAhorro(int id_usuario, int id_caja, double monto) {
		this.id_usuario = id_usuario;
		this.id_caja = id_caja;
		this.monto = monto;
	}
	
	//getters y setters
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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
	
	
}
