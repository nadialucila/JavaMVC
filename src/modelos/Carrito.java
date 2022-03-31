package modelos;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
	//atributos
	private int id_usuario;
	private List<ArticuloCarrito> listaArticulos = new ArrayList<ArticuloCarrito>();
	private int nro_venta = 0;
	
	//constructor
	public Carrito(int id_usuario, List<ArticuloCarrito> listaArticulos) {
		this.id_usuario = id_usuario;
		this.listaArticulos = listaArticulos;
	}
	
	public Carrito() {
		super();
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
	public List<ArticuloCarrito> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(List<ArticuloCarrito> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	
	
	//algunos métodos
	/**
	 * Metodo para saber si un articulo ya esta agregado en el carrito, en dicho caso, le agrega la cantidad.
	 * @param id de tipo entero
	 * */
	public boolean agregarCantidad(int id, int cantidad) {
		boolean ok = false;
		for (ArticuloCarrito arti : this.listaArticulos) {
			if(arti.getCodigo() == id) {
				int cantidadNueva = arti.getStock() + cantidad;
				arti.setStock(cantidadNueva);
				ok = true;
			} else {
				ok = false;
			}
		}
		return ok;
	}
	/**
	 * 
	 * Metodo para agregar un articulo al carrito
	 * @param articulo de tipo ArticuloCarrito
	 * */
	public void agregarArticulo(ArticuloCarrito articulo) {
		this.listaArticulos.add(articulo);
	}
	
	/**
	 * 
	 * Método para eliminar todos los articulos del carrito
	 * */
	public void vaciarCarrito() {
		this.listaArticulos.clear();
	}
	
	
	//podria fallar
	/**
	 * Calcula el total, en términos de precio, del carrito de compras. Sumando a una variable iniciada en 0 todos los precios multiplicados por la cantidad de cada articulo.
	 * */
	public double calcularTotal() {
		double totalCarrito = 0;
		for (ArticuloCarrito art : this.getListaArticulos()) {
			totalCarrito += art.getPrecio() * art.getStock();
		}
		return totalCarrito;	
	}	
	
}
