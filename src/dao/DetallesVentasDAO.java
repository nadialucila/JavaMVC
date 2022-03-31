package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.DetallesVentas;

public class DetallesVentasDAO{
	
	protected ConexionMySQL conexion = new ConexionMySQL();

	public List<DetallesVentas> getByNroVenta(int nro_venta) throws SQLException {
		List<DetallesVentas> lista = new ArrayList<DetallesVentas>();
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement("SELECT * FROM detalle_venta "
															+ "WHERE nro_venta=? "
															+ "ORDER BY articulo DESC");
		
		ps.setInt(1, nro_venta);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			DetallesVentas dv = new DetallesVentas();
			dv.setNro_venta(rs.getInt("nro_venta"));
			dv.setNombre_articulo(rs.getString("articulo"));
			dv.setCantidad_articulo(rs.getInt("cantidad"));
			dv.setPrecio_articulo(rs.getDouble("precio"));
			lista.add(dv);
		}
		
		conexion.cerrar();		
		
		return lista;
	}

}
