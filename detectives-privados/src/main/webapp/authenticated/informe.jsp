<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>

	<body>

		<h1>Informe ${informe.titulo}</h1>

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


					<div>
						<p><pre>${informe.descripcion}</pre></p>
						<hr>
						<p><pre>${informe.contenido}</pre></p>
					</div>



	</body>

	</html>