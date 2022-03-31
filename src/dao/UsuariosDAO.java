package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Usuarios;

public class UsuariosDAO extends DAO<Usuarios>{

	/**
	 * Devuelve una lista con todos los usuarios
	 * @return lista de usuarios
	 * */
	@Override
	public List<Usuarios> list() throws SQLException {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement("SELECT * FROM usuarios");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Usuarios user = new Usuarios();
			user.setNombre(rs.getString("nombre"));
			user.setApellido(rs.getString("apellido"));
			user.setUsuario(rs.getString("usuario"));
			user.setPass(rs.getString("pass"));
			user.setId(rs.getInt("id_usuario"));
			user.setRol(rs.getString("rol"));
			
			lista.add(user);
		}
		
		conexion.cerrar();		
		
		return lista;
	}
	
	/**
	 * Devuelve un usuario buscandolo por su id
	 * @param id del usuario
	 * */
	@Override
	public Usuarios getById(int id) throws SQLException {
		Usuarios user = null;
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
									"SELECT * FROM usuarios"
									+ " WHERE id_usuario = ?");
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			user = new Usuarios();
			user.setId(rs.getInt("id_usuario"));
			user.setNombre(rs.getString("nombre"));
			user.setApellido(rs.getString("apellido"));
			user.setUsuario(rs.getString("usuario"));
			user.setPass(rs.getString("pass"));
			user.setRol(rs.getString("rol"));
			
		}
		
		conexion.cerrar();
		return user;
	}
	
	/**
	 * Devuelve un usuario buscandolo por su nombre de usuario
	 * @param user nombre del usuario
	 * */
	public Usuarios getByUsername(String user) throws SQLException {
		
		Usuarios user2 = null;
		
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
									"SELECT * FROM usuarios"
									+ " WHERE usuario = ?");
		
		ps.setString(1, user);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			user2 = new Usuarios();
			user2.setId(rs.getInt("id_usuario"));
			user2.setNombre(rs.getString("nombre"));
			user2.setApellido(rs.getString("apellido"));
			user2.setUsuario(rs.getString("usuario"));
			user2.setPass(rs.getString("pass"));
			user2.setRol(rs.getString("rol"));
		}
		
		conexion.cerrar();
		return user2;
	}
	
	@Override
	public void insert(Usuarios usuario) throws SQLException {
		conexion.abrir();
		//inserta el articulo
		PreparedStatement ps = conexion.getPreparedStatementInsert("INSERT INTO usuarios" + 
																" (nombre,apellido,usuario,pass)" + 	
																" values (?,?,?,?)");
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getApellido());
		ps.setString(3, usuario.getUsuario());
		ps.setString(4, usuario.getPass());
		
		ps.executeUpdate();
		
		//devuelve e inserta el codigo del articulo
		ResultSet keys = ps.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		usuario.setId(id);
		
		conexion.cerrar();
	}

	@Override
	public void update(Usuarios usuario) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
				"UPDATE usuarios"
				+ " SET nombre = ?,"
				+ " apellido = ?,"
				+ " usuario = ?,"
				+ " pass = ?"
				+ " WHERE id = ?");
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getApellido());
		ps.setString(3, usuario.getUsuario());
		ps.setString(4, usuario.getPass());
		ps.setInt(5, usuario.getId());
		
		ps.executeUpdate();
		
		conexion.cerrar();
	}

	@Override
	public void delete(Usuarios usuario) throws SQLException {
		conexion.abrir();
		
		PreparedStatement ps = conexion.getPreparedStatement(
				"delete from usuarios"
				+ " WHERE id = ?");
		
		ps.setInt(1, usuario.getId());
		
		ps.executeUpdate();
		
		conexion.cerrar();
	}

}
