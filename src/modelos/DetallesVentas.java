package modelos;

public class DetallesVentas {
	//detalle
	private int nro_venta;
	private String nombre_articulo;
	private double precio_articulo;
	private int cantidad_articulo;

	//constructor
	public DetallesVentas(int nro_venta, String nombre_articulo, double precio_articulo, int cantidad_articulo) {
		this.nro_venta = nro_venta;
		this.nombre_articulo = nombre_articulo;
		this.precio_articulo = precio_articulo;
		this.cantidad_articulo = cantidad_articulo;
	}

	public DetallesVentas() {
	}
		
	//getters y setters
	public int getNro_venta() {
		return nro_venta;
	}
	public void setNro_venta(int nro_venta) {
		this.nro_venta = nro_venta;
	}
	public String getNombre_articulo() {
		return nombre_articulo;
	}
	public void setNombre_articulo(String nombre_articulo) {
		this.nombre_articulo = nombre_articulo;
	}
	public double getPrecio_articulo() {
		return precio_articulo;
	}
	public void setPrecio_articulo(double precio_articulo) {
		this.precio_articulo = precio_articulo;
	}
	public int getCantidad_articulo() {
		return cantidad_articulo;
	}
	public void setCantidad_articulo(int cantidad_articulo) {
		this.cantidad_articulo = cantidad_articulo;
	}
		
}
