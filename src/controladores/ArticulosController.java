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
import javax.servlet.http.HttpSession;

import dao.ArticulosDAO;
import modelos.Articulos;
import modelos.Usuarios;
import utiles.Validadores;

/**
 * Servlet implementation class ArticulosController
 */
@WebServlet("/Articulos")
public class ArticulosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ArticulosController() {
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
				show(id, request, response);
			}
			break;
		case "show":
			show(id, request, response);
			break;
		case "edit":
			edit(id, request, response);
			break;
		case "create":
			create(request, response);
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = Optional.ofNullable(request.getParameter("accion")).orElse("insert");
		String sId = Optional.ofNullable(request.getParameter("id")).orElse("-1");
		int id = Integer.parseInt(sId);

		switch (accion) {
		case "insert":
			insert(request, response);
			break;
		case "update":
			update(id, request, response);
			break;
		case "delete":
			delete(id, request, response);
			break;
		default:
			break;
		}
	}

	protected void index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("vistas/articulos/Menu.jsp");
		
	}

	protected void create(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("vistas/articulos/Alta.jsp");
		
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArticulosDAO adao = new ArticulosDAO();
		Articulos art = new Articulos();
		
		String nombre = request.getParameter("nombre");
		String descrip = request.getParameter("descrip");
		String sPrecio = request.getParameter("precio");
		String sStock = request.getParameter("stock");
		double precio;
		int stock;
		
		if(Validadores.isDouble(sPrecio) && Validadores.isInteger(sStock)) {
			precio = Double.parseDouble(sPrecio);
			stock = Integer.parseInt(sStock);
			
			if(Validadores.isPositive(precio) && Validadores.isPositive((double) stock)) {
				art.setNombre(nombre);
				art.setDescripcion(descrip);
				art.setPrecio(precio);
				art.setStock(stock);
				
				try {
					adao.insert(art);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				request.setAttribute("errorInput", Validadores.ERROR2);
			}
		} else {
			request.setAttribute("errorInput", Validadores.ERROR);
		}
		
		request.getRequestDispatcher("vistas/articulos/Alta.jsp").forward(request, response);
	
	}

	protected void show(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuarios user = (Usuarios) session.getAttribute("usuario");
		
		ArticulosDAO adao = new ArticulosDAO();
		
		try {
			List<Articulos> articulos = adao.list();
			request.setAttribute("listado", articulos);
			request.setAttribute("usuario", user);
			
			request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	protected void edit(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArticulosDAO adao = new ArticulosDAO();
		try {
			Articulos art = adao.getById(id);
			request.setAttribute("articulo", art);
			request.getRequestDispatcher("vistas/articulos/Modificacion.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void update(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArticulosDAO adao = new ArticulosDAO();
		Articulos art = new Articulos();
		
		String sCodigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String descrip = request.getParameter("descripcion");
		String sPrecio = request.getParameter("precio");
		String sStock = request.getParameter("stock");
		int codigo = Integer.parseInt(sCodigo);
		
		
		if(!Validadores.isDouble(sPrecio) || !Validadores.isInteger(sStock)) {
			request.setAttribute("errorInput", Validadores.ERROR);
			request.getRequestDispatcher("vistas/articulos/Articulos?accion=edit&id=" + codigo).forward(request, response);
			return;
		} 
		
		double precio = Double.parseDouble(sPrecio);
		int stock = Integer.parseInt(sStock);
		if(!Validadores.isPositive(precio) || !Validadores.isPositive((double) stock)) {
			request.setAttribute("errorInput", Validadores.ERROR2);
			request.getRequestDispatcher("vistas/articulos/Articulos?accion=edit&id=" + codigo).forward(request, response);
			return;
		}
		
		art.setNombre(nombre);
		art.setDescripcion(descrip);
		art.setPrecio(precio);
		art.setStock(stock);
		art.setCodigo(codigo);
		
		try {
			adao.update(art);
			List<Articulos> articulos = adao.list();
			request.setAttribute("listado", articulos);
			request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void delete(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArticulosDAO adao = new ArticulosDAO();
		
		try {
			Articulos art = adao.getById(id);
			adao.delete(art);
			
			List<Articulos> articulos = adao.list();
			request.setAttribute("listado", articulos);
			request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
