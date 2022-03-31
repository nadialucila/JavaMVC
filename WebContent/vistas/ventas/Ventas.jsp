<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Historial de Ventas</title>
</head>
<body>
	<h1>///// Ventas realizadas /////</h1>
	<i>tip: hacer click en el id del usuario mostrará solo las ventas realizadas al mismo.</i>
	
	<c:if test="${vacio == true }">
		<c:out value="No hay ventas aún."/>
	</c:if>
	<c:if test="${vacio == false }">
		<table border="1">
			<thead>
				<tr>
					<th>Nro de Venta</th>
					<th>Usuario</th>
					<th>Total precio</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ventas}" var="venta">
				<tr>
					<td><c:out value="${venta.nro_venta}"/></td>
					<td><c:out value="${venta.id_usuario}"/></td>
					<td><c:out value="${venta.total_precio}"/></td>
					<td>
						<form action="<c:url value="/Ventas"/>" method="get">
							<input type="hidden" name="accion" value="detalles">
							<input type="hidden" name="venta" value="<c:out value="${venta.nro_venta}"/>">
							<input type="submit" value="Ver detalles >>">
						</form>
					</td>
					<td>
						<form action="<c:url value="/Ventas"/>" method="get">
							<input type="hidden" name="accion" value="ventas">
							<input type="hidden" name="id" value="<c:out value="${venta.id_usuario}"/>">
							<input type="submit" value="Ver ventas al usuario >>">
						</form>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
	<br><br>
	<form action="<c:url value="/Ventas"/>" method="get">
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" << Volver">
	</form>
</body>
</html>