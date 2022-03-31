<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Compra Realizada</title>
</head>
<body>
	<h1>¡Su compra fue realizada con éxito!</h1><br><br>
	<i>Detalles de su compra:</i><br><br>
	
	<table border="1">
		<thead>
			<tr>
				<th>Numero de venta</th>
				<th>Articulo</th>
				<th>Precio</th>
				<th>Cantidad</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach items="${listado}" var="venta">
			<tr>
				<td><c:out value="${venta.nro_venta}"/></td>
				<td><c:out value="${venta.nombre_articulo}"/></td>
				<td><c:out value="${venta.precio_articulo}"/></td>
				<td><c:out value="${venta.cantidad_articulo}"/></td>	
			</tr>
	</c:forEach>
			<tr>
				<td>Total: <c:out value="${total}"/></td>
			</tr>
		</tbody>
	</table><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<br>
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" < Inicio">
	</form>
	
</body>
</html>