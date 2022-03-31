package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.Transacciones;

public class TransaccionesDAO {
	
	protected ConexionMySQL conexion = new ConexionMySQL();

	public List<Transacciones> getMovById(int id_caja) throws SQLException {
		//devuelve los movimientos de la caja especificada
		conexion.abrir();
		
		List<Transacciones> lista = new ArrayList<Transacciones>();
		Transacciones tr;
		
		PreparedStatement ps = conexion.getPreparedStatement(
												"SELECT * FROM transacciones "
												+ "WHERE id_caja=? ORDER BY fecha DESC");
		
		ps.setInt(1, id_caja);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			tr = new Transacciones();
			tr.setId_caja(id_caja);
			tr.setFecha(rs.getDate("fecha"));
			tr.setId_transaccion(rs.getInt("id_transaccion"));
			tr.setMonto(rs.getDouble("monto"));
			tr.setMovimiento(rs.getString("movimiento"));
			
			lista.add(tr);
			
		}
		
		conexion.cerrar();
		return lista;
	}

	
	public void insert(Transacciones tr) throws SQLException {
		//inserta un nuevo movimiento en la caja
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatementInsert(
										"INSERT INTO transacciones "
										+ "(id_caja, movimiento, monto, fecha)"
										+ " values (?,?,?,?)");
		ps.setInt(1, tr.getId_caja());
		ps.setString(2, tr.getMovimiento());
		ps.setDouble(3, tr.getMonto());
		ps.setDate(4, tr.getFecha());
		
		ps.executeUpdate();
		
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		tr.setId_transaccion(id);
		
		conexion.cerrar();
		
	}


}
