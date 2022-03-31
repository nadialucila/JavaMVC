package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
public abstract class DAO<T> {

	protected ConexionMySQL conexion = new ConexionMySQL();
	
	//Va a retornar una lista de todos los elementos
	public abstract List<T> list() throws SQLException;
	
	
	public abstract T getById(int id) throws SQLException;

	public abstract void insert(T t) throws SQLException;	

	public abstract void update(T t) throws SQLException;	

	public abstract void delete(T t) throws SQLException;
	
}
