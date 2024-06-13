package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.RandomAccessReferenceMap;

import com.curso.models.Informe;
import com.curso.models.Usuario;
import com.curso.utils.DatabaseUtil;

@WebServlet("/authenticated/informe")
public class InformeServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String indirectId = req.getParameter("id");
		Informe informe = null;
		
		HttpSession session = req.getSession(false);
		RandomAccessReferenceMap armap = null;
		Usuario usuario = null;
		if (session != null) {
			usuario = (Usuario) session.getAttribute("usuario");
			armap = (RandomAccessReferenceMap) session.getAttribute("armap");
		}
		
		String id = null;
		try {
			id = armap.getDirectReference(indirectId);
		} catch (AccessControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<Informe> informes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			
			String sql = "SELECT * FROM informes WHERE id=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, id);
//			pst.setString(1, indirectId);
			
			
			ResultSet rs = pst.executeQuery();
			
			
			if (rs.next()) {
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				Integer userId = rs.getInt("usuarioId");
				String temaColor = rs.getString("temaColor");			
				
				
				if (userId != usuario.getId()) {
					res.sendRedirect("forbidden.html");
					return;
				}
				
				informe = new Informe(indirectId, titulo, contenido, descripcion, temaColor, userId);
				System.out.println(informe);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(conn);
		}
		
		
		req.setAttribute("informe", informe);
		req.getRequestDispatcher("informe.jsp").forward(req, res);
		
		
	}

}
