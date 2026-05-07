package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

// Servlet codée par Vitally - 7 mai 2026

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
	                + "<html lang='fr'><head><meta charset='utf-8'>" // En-tête HTML, encodage UTF-8 pour les accents
	                
	                // --- Titre ---
	                + "<title>HUNVRE Connexion</title></head>"
	                
	                + "<body>"
	                
		                // --- Description ---
		                + "<p>\"Un jeu inspiré de Balatro pour plonger\\ndans les profondeurs du rêve lucide.\"</p>"
		                
		                // --- Début formulaire méthode POST
		                + "<form action='ControleurConnexion' method='POST'>" // Le formulaire envoie les données à la servlet ControleurConnexion en POST
		                
		                // --- Champs de saisie avec labels ---		                
		                // --- Adresse mail
		                + "<label>Adresse email :</label><br>"
		                + "<input type='email' name='labelEmail' placeholder= 'exemple@mail.com' /><br>" // Champ adresse mail — name='labelEmail' est la clé qu'on récupérera avec getParameter("labelEmail")
		                
		                // --- mot de passe
		                + "<label>Mot de passe :</label><br>"
		                + "<input type='password' name='mdp' placeholder='Votre mot de passe' /><br>" // Champ mot de passe — name='mdp' est la clé qu'on récupérera avec getParameter("mdp")
		                
		             	// --- Boutons ---
		             	// --- Bouton Se connecter ---
		                + "<button type='submit' name='connexion' >Se connecter</button>" // Bouton qui déclenche 
		                // --- Bouton Créer un compte ---
		                + "<button type='submit' name='creation' >Créer un compte</button>" // Bouton qui déclenche 
		                
		                // --- Fin formulaire méthode POST
		                + "</form>"
	                
	                + "</body></html>");
		
	}
}
