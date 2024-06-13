package com.curso.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curso.models.RecaptchaResponse;
import com.curso.utils.DatabaseUtil;
import com.curso.utils.PasswordUtil;
import com.google.gson.Gson;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String nombre = req.getParameter("nombre");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String web = req.getParameter("web");
		String rol = req.getParameter("rol");
		
//		TODO: Controlar que llegan todos los datos rellenos
		

		String recaptchaSecret = "6LeiWfcpAAAAAKOXrAiniA0aOq2uEHoE6dFjW3JP";
		String recaptchaResponse = req.getParameter("g-recaptcha-response");
		
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String params = "secret=" + recaptchaSecret + "&response=" + recaptchaResponse;
		
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.getOutputStream().write(params.getBytes());
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		
		while((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		
		String contentJSON = content.toString();
		System.out.println(contentJSON);
		
		Gson gson = new Gson();
		RecaptchaResponse recaptchaResp = gson.fromJson(contentJSON, RecaptchaResponse.class);
		if (!recaptchaResp.isSuccess()) {
			res.sendRedirect("signup.html");
			return;
		}
		
		
		String hashedPassword = PasswordUtil.hashPassword(password);
//		$2a$10$OZzx4q3I.dMcWeh2NLV3kOQ5DiBBr8XLjvRNBxP97BD7ehmWUNLZ.
	
		Connection conn = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "INSERT INTO usuarios (nombre, username, password, web, rol) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, nombre);
			pst.setString(2, username);
			pst.setString(3, hashedPassword);
			pst.setString(4, web);
			pst.setString(5, rol);
			
			
			pst.executeUpdate();
			
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			DatabaseUtil.closeConnection(conn);
		}
		
		
		res.sendRedirect("login.html");
		
	}
	
}
