package controladores;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class Controlador extends HttpServlet{
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


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
	

	protected abstract void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	protected abstract void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	protected abstract void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	protected abstract void show(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	protected abstract void edit(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	protected abstract void update(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	protected abstract void delete(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	
	

}
