package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetallesVentasDAO;
import dao.UsuariosDAO;
import dao.VentasDAO;
import modelos.DetallesVentas;
import modelos.Usuarios;
import modelos.Ventas;

/**
 * Servlet implementation class VentasController
 */
@WebServlet("/Ventas")
public class VentasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public VentasController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = Optional.ofNullable(request.getParameter("accion")).orElse("");
		String sId = Optional.ofNullable(request.getParameter("id")).orElse("-1");
		String sVenta = Optional.ofNullable(request.getParameter("venta")).orElse("-1");
		int venta = Integer.parseInt(sVenta);
		int id = Integer.parseInt(sId);
		

		switch (accion) {
		case "":
			if (id == -1) {
				index(request, response);
			} else {
				show(request, response);
			}
			break;
		case "show":
			show(request, response);
			break;
		case "volver":
			volver(request, response);
			break;
		case "detalles":
			detalles(venta, request, response);
			break;
		case "ventas":
			ventas(id, request, response);
		default:
			break;
		}
	}


	private void index(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			response.sendRedirect("vistas/articulos/Menu.jsp");
		
	}

	private void detalles(int venta, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		DetallesVentasDAO dvdao = new DetallesVentasDAO();
		
		try {
			List<DetallesVentas> detalles = dvdao.getByNroVenta(venta);
			request.setAttribute("detalles", detalles);
			request.setAttribute("numero", venta);
			request.getRequestDispatcher("vistas/ventas/Detalles.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void ventas(int id, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//acá se muestran todas las ventas hechas a un usuario por su id
		UsuariosDAO udao = new UsuariosDAO();
		VentasDAO vdao = new VentasDAO();
		try {
			Usuarios user = udao.getById(id);
			List<Ventas> ventasUser = vdao.getVentasByUserId(id);
			request.setAttribute("uventas", ventasUser);
			request.setAttribute("usuario", user);
			request.getRequestDispatcher("vistas/ventas/Usuario.jsp").forward(request, response);
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void show(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//acá es para mostrar todo el historial
		VentasDAO vdao = new VentasDAO();
		try {
			List<Ventas> ventas = vdao.list();
			
			if(ventas.size()>0) {
				request.setAttribute("ventas", ventas);
				request.setAttribute("vacio", false);
			} else {
				request.setAttribute("vacio", true);
			}
			request.getRequestDispatcher("vistas/ventas/Ventas.jsp").forward(request, response);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void volver(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		show(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
