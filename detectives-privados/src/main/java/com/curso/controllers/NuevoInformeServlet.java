package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.curso.models.Usuario;
import com.curso.utils.DatabaseUtil;

@WebServlet("/authenticated/nuevo-informe")
public class NuevoInformeServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String tokenCSRF = UUID.randomUUID().toString();
		HttpSession session = req.getSession(false);
		session.setAttribute("tokenCSRF", tokenCSRF);
		
//		res.sendRedirect("nuevo-informe.jsp");
		req.getRequestDispatcher("nuevo-informe.jsp").forward(req, res);;
	}
	
	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String titulo = req.getParameter("titulo");
		String descripcion = req.getParameter("descripcion");
		String contenido = req.getParameter("contenido");
		String temaColor = req.getParameter("temaColor");
		
		String tituloSaneado = ESAPI.encoder().encodeForHTML(titulo);
		String contenidoSaneado = ESAPI.encoder().encodeForHTML(contenido);
		String descripcionSaneada = ESAPI.encoder().encodeForHTML(descripcion);
		
		String tokenCSRFForm = req.getParameter("tokenCSRF");
		String sessionTokenCSRF = null;
//		
		Integer usuarioId = null;
		
		HttpSession session = req.getSession(false);
		if (session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if (usuario != null) {
				usuarioId = usuario.getId();
			}
			sessionTokenCSRF = (String) session.getAttribute("tokenCSRF");
		}
		
		
		if (sessionTokenCSRF == null || tokenCSRFForm == null || !tokenCSRFForm.equals(sessionTokenCSRF)) {
			System.out.println("ATAQUE CSRF");
			res.sendRedirect("../login.html");
			return;
		}
		
		Connection conn = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "INSERT INTO informes (titulo, descripcion, contenido, temaColor, usuarioId) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, titulo);
//			pst.setString(2, descripcion);
//			pst.setString(3, contenido);
			pst.setString(1, tituloSaneado);
			pst.setString(2, descripcionSaneada);
			pst.setString(3, contenidoSaneado);
			pst.setString(4, temaColor);
			pst.setInt(5, usuarioId);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(conn);
		}
		
		res.sendRedirect("informes");	
		
		
	}
	
	
}
