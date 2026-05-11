package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

// Servlet Connexion codée par Vitally - 7 mai 2026 - matin
// Mise à jour table HTML - 10 mai 2026

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    out.println("<!DOCTYPE html>"
	            + "<html lang='fr'>"
	            + "<head>"
	            +     "<meta charset='utf-8'>"
	            +     "<link rel='stylesheet' href='style.css'>"
	            +     "<title>HUNVRE Connexion</title>"
	            + "</head>"
	            + "<body>"

	                // --- Description ---
	                + "<p>\"Un jeu inspiré de Balatro pour plonger dans les profondeurs du rêve lucide.\"</p>"

	                // --- Formulaire connexion en POST ---
	                + "<form action='ControleurConnexion' method='POST'>"
	                + "<table>"

	                    // --- Ligne email ---
	                    + "<tr>"
	                    +     "<td><label>Adresse email :</label></td>"
	                    +     "<td><input type='email' name='labelEmail' placeholder='exemple@mail.com' /></td>"
	                    + "</tr>"

	                    // --- Ligne mot de passe ---
	                    + "<tr>"
	                    +     "<td><label>Mot de passe :</label></td>"
	                    +     "<td><input type='password' name='mdp' placeholder='Votre mot de passe' /></td>"
	                    + "</tr>"

	                    // --- Ligne bouton connexion ---
	                    + "<tr>"
	                    +     "<td></td>"
	                    +     "<td><input type='submit' name='connexion' value='Se connecter'></td>"
	                    + "</tr>"

	                + "</table>"
	                + "</form>"

	                // --- Formulaire création de compte en GET - modif pauline 7/05/26 14:38 ---
	                + "<form action='CreationCompte' method='GET'>"
	                + "<table>"
	                +	"<tr>"
	                +	  "<td></td>"
	                +     "<td><input type='submit' name='creation' value='Créer un compte'></td>"
	                +	"</tr>"
	                + "</table>" 
	                + "</form>"

	            + "</body></html>");
	}
}