<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu</title>
</head>
<body>
	<!-- Menu Admin -->
	<h1>/////  Día%  /////</h1>
	<br><br>
	<c:set var = "admin" scope="session" value="admin"/>
	<c:if test="${usuario.rol == admin}">
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="create"> 
		Cargar un nuevo articulo  <input type="submit" value=" >> ">
	</form><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="show">
		Listado de articulos  <input type="submit" value=" >> ">
	</form><br>
	<form action="<c:url value="/Ventas"/>" method="get">
		<input type="hidden" name="accion" value="show">
		Historial de ventas  <input type="submit" value=" >> ">
	</form><br>
	</c:if>
	
	<!-- Menu Clientes -->
	<c:if test="${usuario.rol != admin}">
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="show">
		Listado de articulos  <input type="submit" value=" >> ">
	</form><br>
	<form action="<c:url value="/Carrito"/>" method="get">
		<input type="hidden" name="accion" value="show">
		Ver carrito  <input type="submit" value=" >> ">
	</form><br>
	<c:if test="${error == true }">
	<c:out value="No hay un carrito de compras aún. Para crear uno, oprima ver listado y agregue un articulo."/>
	</c:if>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="">
		Banco  <input type="submit" value=" >> ">
	</form><br>
	</c:if>
	<form action="<c:url value="/Usuarios"/>" method="post">
		<input type="hidden" name="accion" value="salir">
		<input type="submit" value=" << Cerrar sesión ">
	</form>
</body>
</html>