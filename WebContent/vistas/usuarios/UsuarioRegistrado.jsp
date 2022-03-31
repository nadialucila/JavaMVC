<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Usuario Registrado</title>
</head>
<body>
	<h1>¡Se ha registrado con éxito!</h1><br><br>
	<h3>Puede volver al inicio e iniciar sesión con sus datos.</h3><br><br><br>
	<form action="<c:url value="/Usuarios"/>" method="get">
		<input type="hidden" value="">
		<input type="submit" value=" < Inicio">
	</form>
</body>
</html>