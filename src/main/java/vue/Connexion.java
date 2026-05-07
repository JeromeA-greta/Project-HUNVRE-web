package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

// Servlet Connexion codée par Vitally - 7 mai 2026 - matin

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
		
			response.setContentType("text/html"); // Dit au navigateur : ce que je t'envoie est du HTML
			PrintWriter out = response.getWriter(); // Ouvre le flux d'écriture vers le navigateur
			
	        out.println("<!DOCTYPE html>"
	                + "<html lang='fr'><head><meta charset='utf-8'><link rel=\"stylesheet\" href=\"style.css\">" // En-tête HTML, encodage UTF-8 pour les accents, CSS
	                
	                // --- Titre ---
	                + "<title>HUNVRE Connexion</title></head>"
	                
	                + "<body>"
	                
		                // --- Description ---
		                + "<p>\"Un jeu inspiré de Balatro pour plonger dans les profondeurs du rêve lucide.\"</p>"
		                
		                // --- Début formulaire méthode POST pour la connexion
		                + "<form action=\"ControleurConnexion\" method=\"POST\">" // Le formulaire envoie les données à la servlet ControleurConnexion en POST
		                
		                // --- Champs de saisie avec labels ---		                
		                // --- Adresse mail
		                + "<label>Adresse email :</label><br>"
		                + "<input type='email' name='labelEmail' placeholder='exemple@mail.com' ><br>" // Champ adresse mail — name='labelEmail' est la clé qu'on récupérera avec getParameter("labelEmail")
		                
		                // --- mot de passe
		                + "<label>Mot de passe :</label><br>"
		                + "<input type='password' name='mdp' placeholder='Votre mot de passe' ><br>" // Champ mot de passe — name='mdp' est la clé qu'on récupérera avec getParameter("mdp")
		                
		             	// --- Boutons ---
		             	// --- Bouton Se connecter ---
		                + "<input type='submit' value='Se connecter'>" // Bouton qui déclenche 
		                // --- Fin formulaire méthode POST pour la connexion
		                + "</form><br>" 
		               
		                //Formulaire méthode GET pour la création de compte - modif pauline 7/05/26 14:38
		                + "<form action='CreationCompte' method='GET'>"
		                       
		                // --- Bouton Créer un compte ---
		                + "<input type='submit' value='Créer un compte'>" // Bouton qui déclenche 
		                
		                // --- Fin formulaire méthode GET
		                + "</form>"
	                
	                + "</body></html>");
		
	}
}
