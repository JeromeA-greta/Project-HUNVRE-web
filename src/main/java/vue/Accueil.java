package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utilisateur;

import java.io.IOException;

import controleur.ControleurConnexion;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession h = request.getSession(false);
		Utilisateur joueur= (Utilisateur) h.getAttribute("joueur");
		
		//HTML de la vue Accueil
		String htmlHaut = "<!doctype html>"
				+ "<html>"
				+ "<head>"
				+ "<link rel='stylesheet' href='style.css'>"
				+ "<meta charset='UTF-8'>"
				+ "<title>Accueil</title>"
				+ "</head>"
				+ "<body>";
		String htmlBody = "<h2 id='titre' align='center'>Bonjour " + joueur.getPseudo() + "</h2>"
				+ "<div class='centrage centrage-xy'>"
				+ "<form action='ControleurAccueil' method='get'>"
				+ "<table>"
				+ "<tr>"
				+ "<td><input type='submit' name='direction' value='Nouvelle partie'></td>"
				+ "<td style='width: 15px;'></td>"
				+ "<td><input type='submit' name='direction' value='Profil'></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><input type='submit' name='direction' value='Tableau des scores'></td>"
				+ "<td style='width: 15px;'></td>"
				+ "<td><input type='submit' name='direction' value='Quitter'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</form>"
				+ "</div>";
		
		String htmlBas = "</body>"
				+ "</html>";
		
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
