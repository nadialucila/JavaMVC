<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Movimientos de la caja</title>
</head>
<body>
	<h1>///// Movimientos /////</h1>
	<br><br>
	<table border="1">
		<thead>
			<tr>
				<th>Nro Movimiento</th>
				<th>Descripcion</th>
				<th>Monto</th>
				<th>Fecha</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mov}" var="mov">
				<tr>
					<td><c:out value="${mov.id_transaccion}"/></td>
					<td><c:out value="${mov.movimiento}"/></td>
					<td><c:out value="${mov.monto}"/></td>
					<td><c:out value="${mov.fecha}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br><br>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<br>
		<input type="hidden" name="accion" value="show">
		<input type="submit" value=" << Volver">
	</form>
</body>
</html>