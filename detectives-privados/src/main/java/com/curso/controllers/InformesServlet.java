package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.reference.RandomAccessReferenceMap;

import com.curso.models.Informe;
import com.curso.utils.DatabaseUtil;

@WebServlet("/authenticated/informes")
public class InformesServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		List<Informe> informes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			
			String sql = "SELECT * FROM informes";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			RandomAccessReferenceMap armap = new RandomAccessReferenceMap();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				Integer userId = rs.getInt("usuarioId");
				String temaColor = rs.getString("temaColor");
				
				
//				String indirectId = id;
				String indirectId = armap.addDirectReference(id);
				
				
				Informe informe = new Informe(indirectId, titulo, contenido, descripcion, temaColor, userId);
				System.out.println(informe);
				
				
				informes.add(informe);
			}
			
			HttpSession session = req.getSession(true);
			session.setAttribute("armap", armap);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(conn);
		}
		
		
		req.setAttribute("informes", informes);
		req.getRequestDispatcher("informes.jsp").forward(req, res);
		
		
	}

}
