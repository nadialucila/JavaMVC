<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Realizar nuevo movimiento</title>
</head>
<body>
	<h1>///// Nuevo Movimiento /////</h1>
	<br><br>
	Monto actual: <c:out value="${caja.monto}"/><br>
	<br><i><c:out value="${errorInput1}"/></i><br><br>
	<!-- Retirar dinero -->
	<form action="<c:url value="/CajaAhorro" />" method="post">
		<input type="hidden" name="accion" value="update">
		<input type="hidden" name="mov" value="retiro">
		<input type="hidden" name="id" value="<c:out value="${caja.id_usuario}"/>">
		Retirar dinero <br>
		<input type="text" name="monto" required>
		<input type="submit" value=" >> ">
		<c:if test="${retiro == true }"> <c:out value="Retiro realizado con éxito." /></c:if>
		<c:if test="${error == true }"> <c:out value="No dispone del monto ingresado a retirar." /></c:if>
	</form><br>
	
	<!-- Ingresar dinero -->
	<form action="<c:url value="/CajaAhorro" />" method="post">
		<input type="hidden" name="accion" value="update">
		<input type="hidden" name="mov" value="ingreso">
		<input type="hidden" name="id" value="<c:out value="${caja.id_usuario}"/>">
		Ingresar dinero <br>
		<input type="text" name="monto" required>
		<input type="submit" value=" >> ">
		<c:if test="${ingreso == true }"> <c:out value="Ingreso de dinero realizado con éxito." /></c:if>
	</form><br>
	<br>
	
	<!-- Transferir dinero -->
	<form action="<c:url value="/CajaAhorro" />" method="get">
		<input type="hidden" name="accion" value="buscar">
		Ingresar id del destinatario <br>
		<input type="text" name="destino" required><input type="submit" value="Buscar"><i><c:out value="${errorInput}"/></i><br><br>
		<c:out value="${errorDestino}"/>
		<c:out value="id de la caja: ${cajaDestino.id_caja}"/>  
		<c:out value="${u_destino.nombre}"/><br><br><br>
	</form>
	<form action="<c:url value="/CajaAhorro" />" method="post">
		<input type="hidden" name="accion" value="update">
		<input type="hidden" name="id" value="<c:out value="${caja.id_usuario}"/>">
		<input type="hidden" name="cajaDestino" value="<c:out value="${cajaDestino.id_usuario}"/>">
		<input type="hidden" name="mov" value="transferencia">
		Ingrese el monto<br>
		<input type="text" name="monto" required>
		<input type="submit" value=" >> ">
		<c:if test="${transferir == true }"> <c:out value="Transferencia de dinero realizada con éxito." /></c:if>
		<c:if test="${transferror == true }"> <c:out value="El monto a transferir es mayor al disponible en la cuenta." /></c:if>
	</form><br>
		
	<!-- Volver -->
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="show">
		<input type="submit" value=" < Volver ">
	</form>
</body>
</html>