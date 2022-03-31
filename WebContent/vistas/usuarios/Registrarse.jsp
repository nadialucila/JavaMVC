<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrarse</title>
</head>
<body>
	<h1>Nuevo usuario</h1><br><br>
	<form action="<c:url value="/Usuarios"/>" method="post">
		<input type="hidden" name="accion" value="insert">
		Nombre: <input type="text" name="nombre" required><br><br>
		Apellido: <input type="text" name="apellido" required><br><br>
		Usuario: <input type="text" name="usuario" required><br><br>
		Contraseña: <input type="password" name="pass" required><br><br>
		<input type="submit" value="Registrarse"><br><br>
	</form>
	<c:if test = "${error == true}">
		<c:out value="El usuario ingresado ya existe, elija otro." />
    </c:if>
	<form action="<c:url value="/Usuarios"/>" method="get">
		<br>
		<input type="hidden" value="">
		<input type="submit" value=" < Volver">
	</form>
	
</body>
</html>