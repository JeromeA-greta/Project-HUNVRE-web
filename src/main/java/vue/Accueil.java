package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//HTML de la vue Accueil
	String htmlHaut = "<!doctype html>"
					+ "<html>"
						+ "<head>"
							+ "<link rel='stylesheet' href='style.css'>"
							+ "<meta charset='UTF-8'>"
							+ "<title>Accueil</title>"
						+ "</head>"
						+ "<body>";
	String htmlBody = "<h2 id='titre' align='center'>Bonjour Michel</h2>"
					+ "<div class='centrage centrage-xy'>"
						+ "<form action='ControleurAccueil' method='get'>"
				//Jérome : je n'ai pas mis les boutons Profil et Tableau des scores pour le moment
							+ "<table><tr>"
							+ "<td><input type='submit' name='direction' value='Nouvelle partie'></td>"
							+ "<td style='width: 15px;'></td>"
							+ "<td><input type='submit' name='direction' value='Quitter'></td>"
							+ "</tr></table>"
						+ "</form>"
					+ "</div>";
	
	String htmlBas = "</body>"
					+ "</html>";
	
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Affichage de la page HTML Accueil complète
		response.getWriter().append(htmlHaut).append(htmlBody).append(htmlBas);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
