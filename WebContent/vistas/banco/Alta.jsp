<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Nueva Caja de Ahorro</title>
</head>
<body>
	<h1>Nueva caja de ahorro</h1>
	<br><br>
	<c:if test="${error == true }" >
		<c:out value="Error al intentar crear una caja de ahorro. Ya existe una con este usuario." />
	</c:if>
	<c:if test="${exito == true }" >
		<c:out value="Se creó la caja de ahorro con éxito" />
	</c:if>
	<form action="<c:url value="/CajaAhorro"/>" method="post">
		<input type="hidden" name="accion" value = "insert">
		Monto:  <input type="text" name="monto" required>  <input type="submit" value = "Agregar">  <i><c:out value="${errorInput}"/></i>
	</form><br><br>
	<form action="<c:url value="/CajaAhorro"/>" method="get">
		<input type="hidden" name="accion" value="">
		<input type="submit" value=" << Volver ">
	</form>
</body>
</html>