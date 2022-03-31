<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inicio</title>
</head>
<body>
	<h1>¡Bienvenid@ a Día%!</h1>
	<br><br>
	<form action="<c:url value="/Usuarios"/>" method="post">
		<input type="hidden" name="accion" value="ingresar">
		Usuario: <input type="text" name="user" required><br><br>
		Contraseña: <input type="password" name="pass" required><br><br>
		<input type="submit" value="Ingresar">
	</form><br>
	<c:if test = "${error == true}">
		<c:out value="Hubo un error con los datos ingresados, inténtelo nuevamente." />
    </c:if>
    
	<form action="<c:url value="/Usuarios"/>" method="get">
		<input type="hidden" name="accion" value="create">
		<input type="submit" value="Registrarse">
	</form>
</body>
</html>