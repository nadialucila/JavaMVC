package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.ArticuloCarrito;
import modelos.Articulos;
import modelos.Carrito;

public class ArticulosDAO extends DAO<Articulos>{
	@Override
	public List<Articulos> list() throws SQLException {
		
		List<Articulos> lista = new ArrayList<Articulos>();
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement("SELECT * FROM articulos");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Articulos art = new Articulos();
			art.setCodigo(rs.getInt("codigo"));
			art.setNombre(rs.getString("nombre"));
			art.setDescripcion(rs.getString("descrip"));
			art.setPrecio(rs.getDouble("precio"));
			art.setStock(rs.getInt("stock"));
			
			lista.add(art);
		}
		
		conexion.cerrar();		
		
		return lista;
	}

	@Override
	public Articulos getById(int id) throws SQLException {
		Articulos art = null;
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
									"SELECT * FROM articulos"
									+ " WHERE codigo = ?");
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			art = new Articulos();
			art.setCodigo(rs.getInt("codigo"));
			art.setNombre(rs.getString("nombre"));
			art.setDescripcion(rs.getString("descrip"));
			art.setPrecio(rs.getDouble("precio"));
			art.setStock(rs.getInt("stock"));
			
		}
		
		conexion.cerrar();
		return art;
	}

	@Override
	public void insert(Articulos articulo) throws SQLException {
		conexion.abrir();
		//inserta el articulo
		PreparedStatement ps = conexion
				.getPreparedStatementInsert("insert into articulos" + 
									" (nombre,descrip,precio,stock)" + 	
									" values (?,?,?,?)");
		
		ps.setString(1, articulo.getNombre());
		ps.setString(2, articulo.getDescripcion());
		ps.setDouble(3, articulo.getPrecio());
		ps.setInt(4, articulo.getStock());
		
		ps.executeUpdate();
		
		//devuelve e inserta el codigo del articulo
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		articulo.setCodigo(id);
		
		
		conexion.cerrar();
		
	}

	@Override
	public void update(Articulos articulo) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
				"UPDATE articulos"
				+ " SET nombre = ?,"
				+ " descrip = ?,"
				+ " precio = ?,"
				+ " stock = ?"
				+ " WHERE codigo = ?");
		
		ps.setString(1, articulo.getNombre());
		ps.setString(2, articulo.getDescripcion());
		ps.setDouble(3, articulo.getPrecio());
		ps.setInt(4, articulo.getStock());
		ps.setInt(5, articulo.getCodigo());
		
		
		ps.executeUpdate();
		
		conexion.cerrar();
		
	}

	@Override
	public void delete(Articulos articulo) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
				"delete from articulos"
				+ " WHERE codigo = ?");
		
		ps.setInt(1, articulo.getCodigo());
		
		ps.executeUpdate();
		
		conexion.cerrar();
	}
	
	/**
	 * Pasandole el carrito de compras, una vez que la misma es confirmada, actualiza el stock de los articulos que se compraron.
	 * @param carrito de tipo Carrito
	 * 
	 * */
	public void updateStock(Carrito carrito) throws SQLException {
		
		List<ArticuloCarrito> lista = carrito.getListaArticulos();
		
		for (ArticuloCarrito art : lista) {
			try {
				
				Articulos articulo = this.getById(art.getCodigo());
				int nuevoStock = articulo.getStock() - art.getStock();
				articulo.setStock(nuevoStock);
				this.update(articulo);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}

}
