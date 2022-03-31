package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.ArticuloCarrito;
import modelos.Carrito;
import modelos.Ventas;

public class VentasDAO {
	
	protected ConexionMySQL conexion = new ConexionMySQL();
	
	/**
	 * Devuelve una lista con todas las ventas realizadas
	 * @return lista de ventas
	 * */
	public List<Ventas> list() throws SQLException {
		List<Ventas> lista = new ArrayList<Ventas>();
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement("SELECT * FROM ventas "
															+ "ORDER BY nro_venta DESC");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Ventas venta = new Ventas();
			venta.setNro_venta(rs.getInt("nro_venta"));
			venta.setId_usuario(rs.getInt("id_usuario"));
			venta.setTotal_precio(rs.getDouble("total_precio"));
			
			lista.add(venta);
		}
		
		conexion.cerrar();		
		
		return lista;
	}
	
	/**
	 * Permite ver las ventas hechas a un solo usuario específico, partiendo por la más reciente.
	 * @param id_usuario el id del usuario
	 * @return una lista de las ventas de tipo List
	 * */
	public List<Ventas> getVentasByUserId(int id_usuario) throws SQLException {
		List<Ventas> ventas = new ArrayList<Ventas>();
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement("SELECT * FROM ventas "
															+ "WHERE id_usuario=? "
															+ "ORDER BY nro_venta DESC");
		ps.setInt(1, id_usuario);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Ventas venta = new Ventas();
			venta.setId_usuario(id_usuario);
			venta.setNro_venta(rs.getInt("nro_venta"));
			venta.setTotal_precio(rs.getDouble("total_precio"));
			
			ventas.add(venta);
		}
		
		conexion.cerrar();
		return ventas;
	}
	
	public void insertVenta(Carrito carrito) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatementInsert(
				"insert into ventas" + 
				" (id_usuario, total_precio)" + 	
				" values (?,?)");
		
		ps.setInt(1, carrito.getId_usuario());
		ps.setDouble(2, carrito.calcularTotal());
		ps.executeUpdate();
		
		//devuelve e inserta el nro de la venta
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		carrito.setNro_venta(id);
		
		//inserto los detalles de la venta en otra tabla
		for (ArticuloCarrito art : carrito.getListaArticulos()) {
			PreparedStatement ps2 = conexion.getPreparedStatementInsert(
					"insert into detalle_venta " +
					"(nro_venta, articulo, cantidad, precio)" + 
					" values (?,?,?,?)");
			
			ps2.setInt(1, carrito.getNro_venta());
			ps2.setString(2, art.getNombre());
			ps2.setInt(3, art.getStock());
			ps2.setDouble(4, art.getPrecio());
			
			ps2.executeUpdate();
		}
		
		conexion.cerrar();
	}


}
