<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Detalles de venta</title>
</head>
<body>
	<h1>///// Detalles de la venta N° <c:out value="${numero}"/> /////</h1>
	<br><br>
	<table border="1">
		<thead>
			<tr>
				<th>Articulo</th>
				<th>Cantidad</th>
				<th>Precio</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${detalles}" var="detalles">
			<tr>
				<td><c:out value="${detalles.nombre_articulo}"/></td>
				<td><c:out value="${detalles.cantidad_articulo}"/></td>
				<td><c:out value="${detalles.precio_articulo}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br><br>
	<form action="<c:url value="/Ventas"/>" method="get">
		<input type="hidden" name="accion" value="volver">
		<input type="submit" value=" << Volver">
	</form>
</body>
</html>