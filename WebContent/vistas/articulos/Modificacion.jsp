<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar articulo</title>
</head>
<body>
	<h1>Editar un articulo</h1>
	<br><br>
	<form action="<c:url value="/Articulos" />" method="post">
		<input type="hidden" name="accion" value = "update">
		Codigo: <input type="text" name="codigo" value="<c:out value="${articulo.codigo}" />" readonly><br><br>
		Nombre: <input type="text" name="nombre" value="<c:out value="${articulo.nombre}" />" required><br><br>
		Descripción: <input type="text" name="descripcion" value="<c:out value="${articulo.descripcion}" />" required><br><br>
		Precio: <input type="text" name="precio" value="<c:out value="${articulo.precio}" />" required><i><c:out value="${errorInput}"/></i><br><br>
		Cantidad: <input type="text" name="stock" value="<c:out value="${articulo.stock}" />" required><i><c:out value="${errorInput}"/></i><br><br>
		<input type="submit" value = "Modificar">	
	</form><br><br>
	<form action="<c:url value="/Articulos"/>" method="get">
		<input type="hidden" name="accion" value="show">
		<input type="submit" value=" << Volver">
	</form>
</body>
</html>