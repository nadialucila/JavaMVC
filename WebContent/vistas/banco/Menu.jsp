<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu Banco</title>
</head>
<body>
	<h1>///// Menú Banco /////</h1>
	<br><br>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="create">
		Crear una nueva caja de ahorro <input type="submit" value=" >> ">
	</form><br><br>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="show">
		Ver mi caja de ahorro <input type="submit" value=" >> ">
	</form>
	<br><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" << Volver ">
	</form>
</body>
</html>