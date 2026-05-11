package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class Scores
 */
@WebServlet("/Scores")
public class Scores extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String html;
	// Variables qui contiendront les noms et scores
	ArrayList<String> pseudos = new ArrayList<>();
	ArrayList<Integer> scores = new ArrayList<>();
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pseudos = (ArrayList<String>) request.getAttribute("pseudos");
		scores = (ArrayList<Integer>) request.getAttribute("scores");
		
		// Première partie du HTML
		html = "<!doctype html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset='UTF-8'>"
				+ "<link href='style.css' rel='stylesheet'>"
				+ "<title>Scores</title>"
				+ "</head>"
				+ "<body>"
				+ "<h2 align='center'>Tableau des scores</h2>"
				+ "<div class='centrage'>"
				+ "<table>";
				
		// Affichage de chaque pseudo et score associé grâce à une boucle
		for(int i = 0; i < 5; i++) {
			html += "<tr>"
					+ "<td>" + pseudos.get(i) + "</td>"
					+ "<td>" + scores.get(i) + "</td>"
					+ "</tr>";
		}
				
		// Fin du HTML
		html +=	"</table>"
				+ "</div>"
				+ "<div class='centrage'>"
				+ "<form action='ControleurScores' method='get'>"
				+ "<input type='submit' name='direction' value='Accueil'>"
				+ "</form>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
		
		
		response.getWriter().append(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
