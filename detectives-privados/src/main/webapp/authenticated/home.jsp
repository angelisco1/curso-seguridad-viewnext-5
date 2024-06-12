<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>

	<body>
		<h1>Bienvenido ${usuario.nombre} (${usuario.rol})</h1>

		<ul>
			<li>
				<a href="nuevo-informe.html">Crear informe</a>
			</li>
			<li>
				<a href="informes">Informes</a>
			</li>
			<li>
				<a href="logout">Logout</a>
			</li>
		</ul>

	</body>

	</html>