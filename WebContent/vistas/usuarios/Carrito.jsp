<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Carrito de compras</title>
</head>
<body>
		<h1>///// Carrito de compras de <c:out value="${usuario.nombre}" /> /////</h1><br><br>
		<table border="1">
		<thead>
			<tr>
				<th>Orden</th>
				<th>Código de Art.</th>
				<th>Nombre</th>
				<th>Descripcion</th>
				<th>Precio</th>
				<th>Cantidad</th>
			</tr>
		</thead>
		<tbody>
	<c:set var="contador" scope="session" value="0"/>
	<c:forEach items="${listado}" var="art">
			<tr>
				<td><c:out value="${contador + 1}"/></td>
				<td><c:out value="${art.codigo}"/></td>
				<td><c:out value="${art.nombre}"/></td>
				<td><c:out value="${art.descripcion}"/></td>
				<td><c:out value="${art.precio}"/></td>
				<td><c:out value="${art.stock}"/></td>
				<td>
					<form action="<c:url value="/Carrito"/>" method="post">
						<input type="hidden" name="accion" value="delete">
						<input type="hidden" name="indice" value="<c:out value="${contador}"/>">
						<input type="submit" value="Eliminar">
					</form>
				</td>
			</tr>
			<c:set var="contador" scope="session" value="${contador + 1 }" />
	</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td>Total: <c:out value="${total}"/></td>
		</tr>
		</tbody>
	</table><br>
	<form action="<c:url value="/Carrito"/>" method="post">
		<br>
		<input type="hidden" name="accion" value="confirmar">
		<input type="hidden" name="metodopago" value="efectivo">
		<input type="submit" value=" Pagar con efectivo >">
	</form><br>
	<form action="<c:url value="/Carrito"/>" method="post">
		<br>
		<input type="hidden" name="accion" value="confirmar">
		<input type="hidden" name="metodopago" value="caja">
		<input type="hidden" name="totalmonto" value="<c:out value="${total}"/>" >
		<input type="submit" value=" Pagar con caja de ahorro >"> <c:out value="${errorcaja}"/>
	</form><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<br>
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" < Inicio">
	</form>
		
</body>
</html>