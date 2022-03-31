package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modelos.CajaAhorro;

public class CajaAhorroDAO extends DAO<CajaAhorro>{

	@Override
	public List<CajaAhorro> list() throws SQLException {
		return null;
	}

	public CajaAhorro getByIdUser(int id_usuario) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
								"SELECT * FROM caja_ahorro "
								+ "WHERE id_usuario=?");
		
		ps.setInt(1, id_usuario);
		
		ResultSet rs = ps.executeQuery();
		
		CajaAhorro caja = null;
		
		while(rs.next()) {
			caja = new CajaAhorro();
			caja.setId_caja(rs.getInt("id_caja"));
			caja.setId_usuario(id_usuario);
			caja.setMonto(rs.getDouble("monto"));
		}
		
		return caja;
	}

	@Override
	public void insert(CajaAhorro caja) throws SQLException {
		conexion.abrir();
		//inserta una nueva caja
		PreparedStatement ps = conexion
				.getPreparedStatementInsert("insert into caja_ahorro" + 
									" (id_usuario,monto)" + 	
									" values (?,?)");
		
		ps.setInt(1, caja.getId_usuario());
		ps.setDouble(2, caja.getMonto());

		ps.executeUpdate();
		
		//devuelve e inserta el id de la caja
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		caja.setId_usuario(id);
	
		conexion.cerrar();
	}

	@Override
	public void update(CajaAhorro caja) throws SQLException {
		//actualiza el monto de la caja
		conexion.abrir();
		
		PreparedStatement ps = conexion
				.getPreparedStatement("UPDATE caja_ahorro "
									+ "SET monto=? "
									+ "WHERE id_caja = ?");
		
		ps.setDouble(1, caja.getMonto());
		ps.setInt(2, caja.getId_caja());

		ps.executeUpdate();
	
		conexion.cerrar();
	}

	@Override
	public void delete(CajaAhorro caja) throws SQLException {
		//en caso de necesitas borrar una caja
		conexion.abrir();
		
		PreparedStatement ps = conexion
				.getPreparedStatement("DELETE from caja_ahorro "
									+ "WHERE id_caja = ?");
		
		ps.setInt(1, caja.getId_caja());

		ps.executeUpdate();
	
		conexion.cerrar();
	}

	@Override
	public CajaAhorro getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
