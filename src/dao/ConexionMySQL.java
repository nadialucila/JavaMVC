package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMySQL {
	private final String url = "jdbc:mysql://localhost/tpjava";
	private final String user = "root";
	private final String password = "";

	private Connection conn;
	
	public void abrir() throws SQLException {	
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url,user,password);		
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	
	public PreparedStatement getPreparedStatementInsert(String sql) throws SQLException {
		return conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	}
	
	public void cerrar() throws SQLException {
		conn.close();
	}
	
	
	

}
