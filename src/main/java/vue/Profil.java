package vue;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
		Utilisateur activeUser = (Utilisateur) h.getAttribute("activeUser");
		
		if (h == null || h.getAttribute("activeUser") == null) { //Si la session n'existe pas, renvoie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
		
		out.print("<!Doctype html><html><head><meta charset=\"utf-8\"/> \r\n"
				+ "<link href=\"licence.css\" rel=\"stylesheet\">"
				+ "<link href=\"range-slider-fiche-sportive.css\" rel=\"stylesheet\">"
				+ "</head>");
		out.print("<body> <div id='fondprofil'>"
				+ "<h1 align=center>Informations adhérent : </h1></br>"
				+ "<div align='right'> <form action='ControleurDeconnexion' name='boutondeconnexion' method='get'> <input type ='submit' name='deconnexion' value='Se déconnecter'> </form></div><br>"
				+ "<div align=center>"
				+ "<table border>"
			//	+ "<form action='ControleurModifInfosProfil'>"
				+ "<tr><td>Nom : </td><td><input type='text' name='nom' placeholder='"+activeUser.getPseudo()+"'></br></td></tr>"
				+ "<tr><td>Prénom : </td><td><input type='text' name='prenom' placeholder='"+activeUser.getMail()+"'></br></td></tr>"							
				+ "<tr><td>Numéro de licence : </td><td><input type='text' name='numLic' value='" +activeUser.getScore()+ "' readonly></br></td></tr>"
				+ "</table>"
				+ "</div>"
				+ "</body>"
				+ "</html>"
				);
		
		
	}
}
