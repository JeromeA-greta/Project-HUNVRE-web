package vue;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DeckJoueur;
import model.Utilisateur;

@WebServlet("/Profil")
public class Profil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Profil() {
        super();
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		
		HttpSession h = request.getSession(false);
		Utilisateur joueur = (Utilisateur) h.getAttribute("joueur");
		
		if (h == null || h.getAttribute("joueur") == null) { //Si la session n'existe pas, renvoie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
		
		out.print("<!Doctype html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"utf-8\"/> \r\n"
				+ "<link href=\"style.css\" rel=\"stylesheet\">"
				+ "</head>"
				+ "<body> <div id='fondprofil'>"
				+ "<h1 align=center>Profil utilisateur : </h1></br>"
				+ "<div align='center'> "
				
				// création bouton accueil aligné sur déconnexion 
				+ "<div class='boutons'>"

				+ "<form action='Accueil' method='get'>"
				+ "<input type='submit' value='Accueil'>"
				+ "</form>"

				+ "<form action='ControleurDeconnexion' method='get'>"
				+ "<input type='submit' value='Se déconnecter'>"
				+ "</form>"

				+ "</div>"
			
				//	+ "<form action='ControleurModifInfosProfil'>"
				+ "<tr><td>Pseudo : </td><td>"+joueur.getPseudo()+"</br></td></tr>"
				+ "<tr><td>Adresse Mail : </td><td>"+joueur.getMail()+"</br></td></tr>"							
				+ "<tr><td>Score max : </td><td>" +joueur.getScore()+ "</br></td></tr>"
				+ "</table>"

				+ "</div>"
				+ "</body>"
				+ "</html>"
				);
		
		
	}
}
