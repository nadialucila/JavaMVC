<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mi caja de ahorro</title>
</head>
<body>
	<h1>///// Caja de ahorro de <c:out value="${usuario.nombre}"/> /////</h1>
	<br><br>
	Monto: <c:out value="$ ${caja.monto}" /><br><br>
	Id de caja: <c:out value="${caja.id_caja}" /><br><br>
	
	<form action="<c:url value="/CajaAhorro" />" method="get">
		<input type="hidden" name="accion" value="movimientos">
		<input type="hidden" name="id" value="<c:out value="${caja.id_caja}"/>" >
		Ver todos los movimientos  <input type="submit" value=" >> ">
	</form><br><br>
	<form action="<c:url value="/CajaAhorro" />" method="get">
		<input type="hidden" name="accion" value="nuevomov">
		Realizar un movimiento  <input type="submit" value=" >> ">
	</form><br><br>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" << Volver ">
	</form>
</body>
</html>