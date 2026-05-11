package vue;

import java.io.IOException;
import java.io.PrintWriter;

import controleur.ControleurPartie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utilisateur;

@WebServlet("/Partie")
public class Partie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		
		HttpSession h = request.getSession(false);
		Utilisateur joueur = (Utilisateur) h.getAttribute("joueur");
		
		if (h == null || h.getAttribute("joueur") == null) { //Si la session n'existe pas, renvoie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
		
	//	ControleurPartie controleurpartie = new ControleurPartie();
		
		out.print("<!Doctype html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"utf-8\"/> \r\n"
				+ "<link rel='stylesheet' href='style.css'>"
				+ "</head>"
				+ "<body> <div id='partie'>"
				+ "<table>"
				+ "<tr><td id='case00'>test</td><td id='case01'></td><td id='case02'></td></tr>"
				+ "<tr><td id='case11' colspan='3'>test</td></tr>"
				+ "<tr><td id='case20'>test</td><td id='case21'></td><td id='case22'></td></tr>"
				+ "</table>"				
				+ "</body>"
				+ "</html>");

	}
}