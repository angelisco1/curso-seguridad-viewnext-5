package com.curso.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import com.curso.models.Usuario;
import com.curso.utils.DatabaseUtil;
import com.curso.utils.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println("LLEGA LA PETICION");
		
		String usernameSaneado = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), username);
		String passwordSaneada = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), password);
		
		Connection conn = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			
//			String sql = "SELECT * FROM usuarios WHERE username='" + username + "' and password='" + password + "'";
//			String sql = "SELECT * FROM usuarios WHERE username='" + usernameSaneado + "' and password='" + passwordSaneada + "'";
//			' or '1'='1 -> \' or \'1\'\=\'1
			
			
//			System.out.println("- CONSULTA SQL: " + sql);
//			Statement st = conn.createStatement();
//			ResultSet rs = st.executeQuery(sql);
			
			
//			String sql = "SELECT * FROM usuarios WHERE username=? and password=?";
			String sql = "SELECT * FROM usuarios WHERE username=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
//			pst.setString(2, password);
			
			System.out.println("- CONSULTA SQL: " + pst);
			
			ResultSet rs = pst.executeQuery();
			
			
			if (rs.next()) {
				
				String hashedPassword = rs.getString("password");
				System.out.println("-HASHED PW- " + hashedPassword);
				if (!PasswordUtil.checkPasswords(password, hashedPassword)) {
					res.sendRedirect("login.html");
					return;
				}
				
				
				Integer id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String web = rs.getString("web");
				String rol = rs.getString("rol");
				
				Usuario usuario = new Usuario(id, nombre, username, null, web, rol);
				
				Cookie galletita = new Cookie("galletita", "con_pepitas_de_chocolate");
				galletita.setHttpOnly(true);
				galletita.setMaxAge(50);
				res.addCookie(galletita);
				
				HttpSession session = req.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				
				session = req.getSession(true);				
				
				session.setAttribute("usuario", usuario);
				
				
				
//				req.setAttribute("usuario", usuario);
//				req.getRequestDispatcher("authenticated/home.jsp").forward(req, res);
				
				res.sendRedirect("authenticated/home.jsp");
				
			} else {
				res.sendRedirect("login.html");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			res.sendRedirect("login-intrusos.html");
		} finally {
			DatabaseUtil.closeConnection(conn);
		}
		
	}
	
}
