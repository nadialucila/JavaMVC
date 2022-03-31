package modelos;

public class Usuarios {
	//Atributos
	private int id;
	private String nombre;
	private String apellido;
	private String usuario;
	private String pass;
	private String rol;
	
	//constructor	
	public Usuarios(int id, String nombre, String apellido, String username, String pass, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = username;
		this.pass = pass;
		this.rol = rol;
	}

	public Usuarios() {
		super();
	}
	
	//getter y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String username) {
		this.usuario = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
	
	
}
