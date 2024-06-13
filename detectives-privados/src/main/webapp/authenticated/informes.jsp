<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>

	<body>

		<h1>Últimos informes</h1>

		<ul>
			<li>
				<a href="nuevo-informe">Crear informe</a>
			</li>
			<li>
				<a href="informes">Informes</a>
			</li>
			<li>
				<a href="logout">Logout</a>
			</li>
		</ul>

		<form action="buscar-informes" method="get">
			<div>
				<label for="busqueda">Buscar informe por:</label>
				<input type="text" id="busqueda" name="busqueda">
			</div>
			<button type="submit">Buscar</button>
		</form>


		<c:choose>
			<c:when test="${informes.size() > 0}">
				<c:forEach items="${informes}" var="informe">
					<div>
						<h2>${informe.titulo}</h2>
						<p><pre>${informe.descripcion}</pre></p>
						<a href="informe?id=${informe.id}">Ver informe</a>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>No hay informes...</p>
			</c:otherwise>
		</c:choose>


	</body>

	</html>