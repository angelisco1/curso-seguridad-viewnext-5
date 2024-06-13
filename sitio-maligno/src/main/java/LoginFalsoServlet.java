import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login-falso")
public class LoginFalsoServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("------> Se han logueado con username: " + username + " - password: " + password);
	}
}
