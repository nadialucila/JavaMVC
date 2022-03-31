package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CajaAhorroDAO;
import dao.TransaccionesDAO;
import dao.UsuariosDAO;
import modelos.CajaAhorro;
import modelos.Transacciones;
import modelos.Usuarios;
import utiles.Validadores;

/**
 * Servlet implementation class CajaAhorroController
 */
@WebServlet("/CajaAhorro")
public class CajaAhorroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CajaAhorroController() {
        super();
    }
    //GET
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
		case "movimientos":
			verMovimientos(id, request, response);
			break;
		case "buscar":
			getCajaByIdUser( request, response);
			break;
		case "nuevomov":
			nuevoMov( request, response);
			break;
		case "create":
			create(request, response);
		default:
			break;
		}
	}
	
	private void getCajaByIdUser( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Usuarios u_activo = (Usuarios) session.getAttribute("usuario");
		UsuariosDAO udao = new UsuariosDAO();
		CajaAhorroDAO cadao = new CajaAhorroDAO();
		CajaAhorro cajaUsuario = null;
		try {
			cajaUsuario = cadao.getByIdUser(u_activo.getId());
		} catch (SQLException e1) {
			e1.printStackTrace();
		};
		
		String sDestino = request.getParameter("destino");
		
		if (!Validadores.isInteger(sDestino)) {
			request.setAttribute("errorInput", Validadores.ERROR);
		} else {
			int id_destino = Integer.parseInt(sDestino);
			try {
				CajaAhorro cajaAhorroDestino = cadao.getByIdUser(id_destino);
				if(cajaAhorroDestino == null) {
					request.setAttribute("errorDestino", "No se encontró el usuario. Inténtelo nuevamente.");
				} else {
					request.setAttribute("cajaDestino", cajaAhorroDestino);
					Usuarios u_destino = udao.getById(id_destino);
					request.setAttribute("u_destino", u_destino);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("caja", cajaUsuario);
		request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
			
	}
	//POST
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
		default:
			break;
		}
	}
	
	private void nuevoMov(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Usuarios user = (Usuarios) session.getAttribute("usuario");
		CajaAhorroDAO cadao = new CajaAhorroDAO();  
		try {
			CajaAhorro ca = cadao.getByIdUser(user.getId());
			request.setAttribute("caja", ca);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("usuario", user);
		request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
		
	}
	
	private void verMovimientos(int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//ver los movimientos de la caja
		TransaccionesDAO trdao = new TransaccionesDAO();
		try {
			List<Transacciones> lista = trdao.getMovById(id);
			
			request.setAttribute("mov", lista);
			
			request.getRequestDispatcher("vistas/banco/Movimientos.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("vistas/banco/Menu.jsp");
		
	}

	protected void create(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("vistas/banco/Alta.jsp");
	}
	
	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuarios user = (Usuarios) session.getAttribute("usuario");
		Transacciones tr = new Transacciones();
		CajaAhorro ca = new CajaAhorro();
		TransaccionesDAO tdao = new TransaccionesDAO();
		CajaAhorroDAO cadao = new CajaAhorroDAO();
		
		try {
			if (cadao.getByIdUser(user.getId()) != null) {
				request.setAttribute("error", true);
				request.setAttribute("exito", false);
			} else {
				String sMonto = request.getParameter("monto");
				if(!Validadores.isDouble(sMonto)) {
					request.setAttribute("errorInput", Validadores.ERROR);
					request.getRequestDispatcher("vistas/banco/Alta.jsp").forward(request, response);
					return;
				}
				double monto = Double.parseDouble(sMonto);
				if(!Validadores.isPositive(monto)) {
					request.setAttribute("errorInput", Validadores.ERROR2);
					request.getRequestDispatcher("vistas/banco/Alta.jsp").forward(request, response);
					return;
				}
				
				ca.setId_usuario(user.getId());
				ca.setMonto(monto);
				cadao.insert(ca);
				
				ca = cadao.getByIdUser(user.getId());
				
				tr.setFecha(java.sql.Date.valueOf(LocalDate.now()));
				tr.setId_caja(ca.getId_caja());
				tr.setMonto(monto);
				tr.setMovimiento("Creacion");
				tdao.insert(tr);
				
				request.setAttribute("error", false);
				request.setAttribute("exito", true);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("vistas/banco/Alta.jsp").forward(request, response);

	}
	
	protected void show(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuarios user = (Usuarios) session.getAttribute("usuario");
		CajaAhorroDAO cadao = new CajaAhorroDAO();  
		try {
			CajaAhorro ca = cadao.getByIdUser(user.getId());
			request.setAttribute("caja", ca);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("usuario", user);
		request.getRequestDispatcher("vistas/banco/MiCaja.jsp").forward(request, response);
	}

	protected void update(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String opcion = request.getParameter("mov");
		
		CajaAhorroDAO cadao = new CajaAhorroDAO();
		TransaccionesDAO trdao = new TransaccionesDAO();
		
		Transacciones tr = new Transacciones();
		CajaAhorro cajaDestino, cajaUsuario;
		
		String sMonto = request.getParameter("monto");
		
		if (!Validadores.isDouble(sMonto)) {
			try {
				cajaUsuario = cadao.getByIdUser(id);
				request.setAttribute("errorInput1", "Por favor solo ingrese valores numericos.");
				request.setAttribute("caja", cajaUsuario);
				request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		double monto = Double.parseDouble(sMonto);
		
		if(!Validadores.isPositive(monto)) {
			try {
				cajaUsuario = cadao.getByIdUser(id);
				request.setAttribute("errorInput1", Validadores.ERROR2);
				request.setAttribute("caja", cajaUsuario);
				request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			cajaUsuario = cadao.getByIdUser(id);
			cajaDestino = null;
			
			switch(opcion) {
			case "retiro":
				{
					if(cajaUsuario.getMonto() < monto) {
						request.setAttribute("error", true);
					} else {
						//hago el update de la caja
						double nuevoMonto = cajaUsuario.getMonto() - monto;
						cajaUsuario.setMonto(nuevoMonto);
						cadao.update(cajaUsuario);
						
						//agrego un movimiento a las transacciones de la caja
						tr.setFecha(java.sql.Date.valueOf(LocalDate.now()));
						tr.setId_caja(cajaUsuario.getId_caja());
						tr.setMonto(monto);
						tr.setMovimiento("Retiro");
						trdao.insert(tr);
						
						request.setAttribute("retiro", true);
						request.setAttribute("ingreso", false);
					}
				}
				
				break;
			case "ingreso":
				{
					//hago el update de la caja
					double nuevoMonto = cajaUsuario.getMonto() + monto;
					cajaUsuario.setMonto(nuevoMonto);
					cadao.update(cajaUsuario);
					
					//agrego un movimiento a las transacciones de la caja
					tr.setFecha(java.sql.Date.valueOf(LocalDate.now()));
					tr.setId_caja(cajaUsuario.getId_caja());
					tr.setMonto(monto);
					tr.setMovimiento("Ingreso");
					trdao.insert(tr);
					
					request.setAttribute("ingreso", true);
					request.setAttribute("retiro", false);
				}
			break;
			case "transferencia":
				{
					String sIdDestino = request.getParameter("cajaDestino");
					int idDestino = Integer.parseInt(sIdDestino);
					cajaDestino = cadao.getByIdUser(idDestino);
					
					String sMontoTransferencia = request.getParameter("monto");
					
					if(!Validadores.isDouble(sMontoTransferencia)) {
						request.setAttribute("errorInput", Validadores.ERROR);
						request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
					} else {
						
						double montoTransferencia = Double.parseDouble(sMontoTransferencia);
						if(!Validadores.isPositive(montoTransferencia)) {
							request.setAttribute("errorInput", Validadores.ERROR2);
							request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
						}
						
						if (montoTransferencia > cajaUsuario.getMonto()) {
							request.setAttribute("transferror", true);
							request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
						} else {
							
							double nuevoCajaUsuario = cajaUsuario.getMonto() - montoTransferencia;
							cajaUsuario.setMonto(nuevoCajaUsuario);
							double nuevoCajaDestino = cajaDestino.getMonto() + montoTransferencia;
							cajaDestino.setMonto(nuevoCajaDestino);
							cadao.update(cajaUsuario);
							cadao.update(cajaDestino);
							
							tr = new Transacciones();
							tr.setFecha(java.sql.Date.valueOf(LocalDate.now()));
							tr.setId_caja(cajaUsuario.getId_caja());
							tr.setMonto(monto);
							tr.setMovimiento("Transferencia realizada");
							trdao.insert(tr);
							
							tr.setFecha(java.sql.Date.valueOf(LocalDate.now()));
							tr.setId_caja(cajaDestino.getId_caja());
							tr.setMonto(monto);
							tr.setMovimiento("Transferencia recibida");
							trdao.insert(tr);
							
							request.setAttribute("transferir", true);
						}
					}
				}
			}
			
			request.setAttribute("caja", cajaUsuario);
			request.setAttribute("cajaDestino", cajaDestino);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("vistas/banco/RealizarMovimiento.jsp").forward(request, response);
	}
}
