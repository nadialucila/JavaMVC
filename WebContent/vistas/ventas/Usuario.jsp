<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ventas por usuario</title>
</head>
<body>
	<h1> Ventas realizadas al usuario <c:out value="${usuario.nombre}"/></h1>
	<br><br>
	<table border="1">
		<thead>
			<tr>
				<th>Nro de Venta</th>
				<th>Total precio</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${uventas}" var="venta">
			<tr>
				<td><c:out value="${venta.nro_venta}"/></td>
				<td><c:out value="${venta.total_precio}"/></td>
				<td>
					<form action="<c:url value="/Ventas"/>" method="get">
						<input type="hidden" name="accion" value="detalles">
						<input type="hidden" name="venta" value="<c:out value="${venta.nro_venta}"/>">
						<input type="submit" value="Ver Detalles >>">
					</form>
				</td>
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