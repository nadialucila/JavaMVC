<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cargar articulos</title>
</head>
<body>
	<h1>Cargar un nuevo articulo</h1>
	<br><br>
	<form action="<c:url value="/Articulos"/>" method="post">
		<input type="hidden" name="accion" value = "insert">
		Nombre: <input type="text" name="nombre" required><br><br>
		Descripción: <input type="text" name="descrip" required><br><br>
		Precio: <input type="text" name="precio" required> <i><c:out value="${errorInput}"/></i> <br><br>
		Cantidad: <input type="text" name="stock" required> <i><c:out value="${errorInput}"/></i> <br><br>
		<input type="submit" value = "Agregar">	
	</form>
	<br><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" value="index">
		<input type="submit" value=" << Volver">
	</form>
</body>
</html>