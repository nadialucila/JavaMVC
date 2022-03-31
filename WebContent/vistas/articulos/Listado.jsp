<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado</title>
</head>
<body>

	<h1>Listado de articulos</h1>
	<br><br>
	<c:if test="${error == true }">
		<c:out value="Hubo un error con la cantidad elegida."/>
	</c:if>
	<c:if test="${exito == true }">
		<c:out value="El articulo seleccionado se agregó al carrito de compras." />
	</c:if>
	<table border="1">
		<thead>
			<tr>
				<th>Código</th>
				<th>Nombre</th>
				<th>Descripcion</th>
				<th>Precio</th>
				<th>Stock</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach items="${listado}" var="art">
			<tr>
				<td><c:out value="${art.codigo}"/></td>
				<td><c:out value="${art.nombre}"/></td>
				<td><c:out value="${art.descripcion}"/></td>
				<td><c:out value="${art.precio}"/></td>
				<td><c:if test="${art.stock == 0}"><c:out value="Fuera de stock"/></c:if><c:if test="${art.stock > 0}"><c:out value="${art.stock}"/></c:if></td>
					<c:set var = "admin" scope="session" value="admin"/>
					<c:if test="${usuario.rol == admin}">
				<td>	 
					<form action="Articulos" method="get">
						<input type="hidden" name="accion" value="edit">
						<input type="hidden" name="id" value="<c:out value="${art.codigo}"/>">
						<input type="submit" value="Editar">
					</form>

				</td>
				<td>
					<form action="Articulos" method="post">
						<input type="hidden" name="accion" value="delete">
						<input type="hidden" name="id" value="<c:out value="${art.codigo}"/>">
						<input type="submit" value="Eliminar">
					</form>
				</td>
					</c:if>
					<c:if test="${usuario.rol != admin}">
				<td>
					<form action="Carrito" method="post">
						<input type="hidden" name="accion" value="agregar">
						<input type="hidden" name="id" value="<c:out value="${art.codigo}"/>">
						<input type="text" placeholder="Indique cantidad" name="cantidad" required>
						<input type="submit" value="Agregar al carrito >>"><i><c:out value="${errorInput}"/></i>
					</form>
				</td>
					</c:if>
			</tr>
	</c:forEach>
		</tbody>
	</table>
	<br><br>
	<c:if test="${usuario.rol != admin}">
		<form action="<c:url value="/Carrito"/>" method="get">
			<input type="hidden" name="accion" value="show">
			<input type="submit" value=" Ver carrito >>">
		</form>
		<br><br>
	</c:if>
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" << Volver">
	</form>
	
</body>
</html>