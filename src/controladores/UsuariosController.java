package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuariosDAO;
import modelos.Usuarios;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/Usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public UsuariosController() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = Optional.ofNullable(request.getParameter("accion")).orElse("");
		String sId = Optional.ofNullable(request.getParameter("id")).orElse("-1");
		int id = Integer.parseInt(sId);
		

		switch (accion) {
		case "":
			if (id == -1) {
				index(request, response);
			} else {
			}
			break;
		case "create":
			create(request, response);
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = Optional.ofNullable(request.getParameter("accion")).orElse("insert");

		switch (accion) {
		case "insert":
			insert(request, response);
			break;
		case "ingresar":
			ingresar(request, response);
			break;
		case "salir":
			salir(request, response);
			break;
		default:
			break;
		}
	}
	
	private void salir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("index.jsp");
		
	}

	private void ingresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		
		UsuariosDAO udao = new UsuariosDAO();
		Usuarios user = null;
		
		String uname = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		try {
				user = udao.getByUsername(uname);
				if (user == null) {
					request.setAttribute("error", true);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} 
				else {
					
					if(pass.equals(user.getPass())) {
						session.setAttribute("usuario", user);
						request.setAttribute("usuario", user);
						request.getRequestDispatcher("vistas/articulos/Menu.jsp").forward(request, response);
					} else {
						request.setAttribute("error", true);
						request.getRequestDispatcher("index.jsp").forward(request, response);
					}
				}
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}

	protected void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("vistas/usuarios/Registrarse.jsp");
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosDAO udao = new UsuariosDAO();
		Usuarios user = new Usuarios();
		
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String usuario = request.getParameter("usuario");
		String pass = request.getParameter("pass");
		
		user.setNombre(nombre);
		user.setApellido(apellido);
		user.setUsuario(usuario);
		user.setPass(pass);
		
		try {
			if (udao.getByUsername(usuario) != null) {
				request.setAttribute("error", true);
				request.getRequestDispatcher("vistas/usuarios/Registrarse.jsp").forward(request, response);
			} else {
				udao.insert(user);
				request.getRequestDispatcher("vistas/usuarios/UsuarioRegistrado.jsp").forward(request, response);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
