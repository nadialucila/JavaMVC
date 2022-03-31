package modelos;

public class Ventas {
	//venta
	private int nro_venta;
	private int id_usuario;
	private double total_precio;
	
	//constructor
	public Ventas(int nro_venta, int id_usuario, double total_precio) {
		this.nro_venta = nro_venta;
		this.id_usuario = id_usuario;
		this.total_precio = total_precio;
	}

	public Ventas() {
	}

	//getters y setters
	public int getNro_venta() {
		return nro_venta;
	}
	public void setNro_venta(int nro_venta) {
		this.nro_venta = nro_venta;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public double getTotal_precio() {
		return total_precio;
	}
	public void setTotal_precio(double total_precio) {
		this.total_precio = total_precio;
	}
	
	
}
