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

import dao.ArticulosDAO;
import dao.CajaAhorroDAO;
import dao.DetallesVentasDAO;
import dao.TransaccionesDAO;
import dao.VentasDAO;
import modelos.ArticuloCarrito;
import modelos.Articulos;
import modelos.CajaAhorro;
import modelos.Carrito;
import modelos.DetallesVentas;
import modelos.Transacciones;
import modelos.Usuarios;
import modelos.Ventas;
import utiles.Validadores;

/**
 * Servlet implementation class CarritoController
 */
@WebServlet("/Carrito")
public class CarritoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public CarritoController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = Optional.ofNullable(request.getParameter("accion")).orElse("");
		
		switch (accion) {
		case "":
				show(request, response);
			break;
		case "show":
			show(request, response);
			break;
		case "volver":
			volver(request, response);
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
		case "agregar":
			agregarArticulo(request, response);
			break;
		case "confirmar":
			confirmarCompra(request, response);
			break;
		case "delete":
			delete(id, request, response);
			break;
		default:
			break;
		}
	}

	protected void create(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			HttpSession session = request.getSession();
			Carrito carrito = new Carrito();
			session.setAttribute("carrito", carrito);
			
	}
	
	protected void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			Usuarios usu = (Usuarios) session.getAttribute("usuario");
			
			ArticulosDAO adao = new ArticulosDAO();
			try {
				List<Articulos> lista = adao.list();
				
				request.setAttribute("listado", lista);
				request.setAttribute("usuario", usu);
				request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	protected void agregarArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//si no existe un carrito crea uno, caso contrario continua con el de la sesión.
		HttpSession session = request.getSession();
		if (session.getAttribute("carrito") == null) {
			create(request, response);
		}
		Carrito carrito = (Carrito) session.getAttribute("carrito");
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		
		ArticulosDAO adao = new ArticulosDAO();
		
		int id_usuario = usuario.getId();
		String sCodigo = request.getParameter("id");
		String sCantidad = request.getParameter("cantidad");
		
		if(!Validadores.isInteger(sCantidad)) {
			request.setAttribute("errorInput", Validadores.ERROR);
			try {
				request.setAttribute("listado", adao.list());
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		int codigo = Integer.parseInt(sCodigo);
		int cantidad = Integer.parseInt(sCantidad);
		
		if(!Validadores.isPositive((double) cantidad)) {
			request.setAttribute("errorInput", Validadores.ERROR2);
			try {
				request.setAttribute("listado", adao.list());
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Articulos art1;
		
		try {
			
			//busco el articulo con el dao, luego creo uno de tipo articulo carrito y lo cargo con los datos del articulo original
			//exceptuando la cantidad que es la que solicita el usuario
			art1 = adao.getById(codigo);
			ArticuloCarrito art2 = new ArticuloCarrito();
			art2.setCodigo(codigo);
			art2.setDescripcion(art1.getDescripcion());
			art2.setNombre(art1.getNombre());
			art2.setPrecio(art1.getPrecio());
			art2.setStock(cantidad);
			
			//me aseguro que el stock pedido no sea un numero negativo, y que sea menor o igual al stock existente
			if (art1.getStock() >= art2.getStock() && art2.getStock() > 0) {
				
				if(!carrito.agregarCantidad(codigo, cantidad)) {
					carrito.agregarArticulo(art2);
				}
				carrito.setId_usuario(id_usuario);
				
				//le informo al usuario que el articulo se agregó
				request.setAttribute("exito", true);
				request.setAttribute("listado", adao.list());
				
			} else {
				//en caso de errores le informo que algo anduvo mal, reenvio la lista de art y el usuario de la sesion
				request.setAttribute("error", true);
				request.setAttribute("listado", adao.list());
				request.setAttribute("usuario", usuario);
			}
			request.getRequestDispatcher("vistas/articulos/Listado.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	protected void show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Carrito carr = (Carrito) session.getAttribute("carrito");
		if (carr==null || carr.getListaArticulos().size() <= 0) {
			//en caso de querer ver un carrito que no existe aun, o que se vacio, redirecciona al index y le informa al usuario.
			request.setAttribute("error", true);
			request.getRequestDispatcher("vistas/articulos/Menu.jsp").forward(request, response);
		} 
		else {
			
			Usuarios user = (Usuarios) session.getAttribute("usuario");
			List<ArticuloCarrito> art = carr.getListaArticulos();
			
			request.setAttribute("listado", art);
			request.setAttribute("usuario", user);
			request.setAttribute("total", carr.calcularTotal());
			
			request.getRequestDispatcher("vistas/usuarios/Carrito.jsp").forward(request, response);
	
		}
	}

	protected void confirmarCompra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuarios user = (Usuarios) session.getAttribute("usuario");
		
		VentasDAO ventasDao = new VentasDAO();
		DetallesVentasDAO dvdao = new DetallesVentasDAO();
		ArticulosDAO artDao = new ArticulosDAO();
		
		Carrito carr = (Carrito) session.getAttribute("carrito");
		
		String metodopago = request.getParameter("metodopago");
		
		
		if(!carr.getListaArticulos().isEmpty()) {
			try {
				
				if(metodopago.equals("efectivo")) {
					//inserta la venta en una tabla de pedidos y otra con detalles, luego actualiza el stock de los articulos
					//finalmente vacia el carrito para volver a ser utilizado en otro momento
					ventasDao.insertVenta(carr);
					artDao.updateStock(carr);
					
					//primero obtengo las ventas del usuario activo con su id, despues obtengo el numero de la ultima venta realizada
					//y utilizo el numero de esa venta para levantar de la bdd los detalles de la misma
					List<Ventas> ventas = ventasDao.getVentasByUserId(user.getId());
					int nro_venta = ventas.get(0).getNro_venta();
					double total_precio = ventas.get(0).getTotal_precio();
					List<DetallesVentas> dv = dvdao.getByNroVenta(nro_venta);
					
					request.setAttribute("total", total_precio);
					request.setAttribute("listado", dv);
					request.getRequestDispatcher("vistas/usuarios/CompraRealizada.jsp").forward(request, response);
					
					carr.vaciarCarrito();

				} else if (metodopago.equals("caja")) {
					
					CajaAhorroDAO cajaAhorroDao = new CajaAhorroDAO();
					CajaAhorro cajaAhorro = cajaAhorroDao.getByIdUser(user.getId());
					TransaccionesDAO transacDao = new TransaccionesDAO();
					Transacciones transac;
					
					String sTotal = request.getParameter("totalmonto");
					double total = Double.parseDouble(sTotal);
					
					if(total > cajaAhorro.getMonto()) {
						request.setAttribute("errorcaja", "No dispone de fondos para realizar la compra.");
						List<ArticuloCarrito> art = carr.getListaArticulos();
						request.setAttribute("listado", art);
						request.setAttribute("total", carr.calcularTotal());
						request.getRequestDispatcher("vistas/usuarios/Carrito.jsp").forward(request, response);
					} else if (total <= cajaAhorro.getMonto()) {
			
						cajaAhorro.setMonto((cajaAhorro.getMonto()-total));
						cajaAhorroDao.update(cajaAhorro);
						
						transac = new Transacciones();
						transac.setFecha(java.sql.Date.valueOf(LocalDate.now()));
						transac.setId_caja(cajaAhorro.getId_caja());
						transac.setMonto(total);
						transac.setMovimiento("Compra");
						transacDao.insert(transac);
						
						ventasDao.insertVenta(carr);
						artDao.updateStock(carr);
						
						List<Ventas> ventas = ventasDao.getVentasByUserId(user.getId());
						int nro_venta = ventas.get(0).getNro_venta();
						double total_precio = ventas.get(0).getTotal_precio();
						List<DetallesVentas> dv = dvdao.getByNroVenta(nro_venta);
						
						request.setAttribute("total", total_precio);
						request.setAttribute("listado", dv);
						
						request.getRequestDispatcher("vistas/usuarios/CompraRealizada.jsp").forward(request, response);
						
						carr.vaciarCarrito();
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	protected void delete(int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Carrito carr = (Carrito) session.getAttribute("carrito");
		Usuarios user = (Usuarios) session.getAttribute("usuarios");
		
		List<ArticuloCarrito> listado = carr.getListaArticulos();
		String sIndice = request.getParameter("indice");
		int indice = Integer.parseInt(sIndice);
		
		listado.remove(indice);
		carr.setListaArticulos(listado);
		
		request.setAttribute("listado", listado);
		request.setAttribute("usuario", user);
		request.setAttribute("total", carr.calcularTotal());
		request.getRequestDispatcher("vistas/usuarios/Carrito.jsp").forward(request, response);
	}

}
