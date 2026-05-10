package vue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class CreationCompte
 */
@WebServlet("/CreationCompte")
public class CreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html"); // Dit au navigateur : ce que je t'envoie est du HTML
		PrintWriter out = response.getWriter(); // Ouvre le flux d'écriture vers le navigateur
		
        out.println("<!DOCTYPE html>"
                + "<html lang='fr'><head><meta charset='utf-8'><link rel=\"stylesheet\" href=\"style.css\">" // En-tête HTML, encodage UTF-8 pour les accents, CSS
                
                // --- Titre ---
                + "<title>HUNVRE Création d'un compte</title></head>"
                
                + "<body>"
               
	                // --- Début formulaire méthode POST pour la création d'un compte
	                + "<form action=\"ControleurCreationCompte\" method=\"POST\">" // Le formulaire envoie les données à la servlet ControleurCreationCompte en POST
	                
	                // --- Champs de saisie avec labels ---
	                // --- Pseudo ---
	                + "<label>Pseudo :</label><br>"
	                + "<input type='test' name='pseudo' placeholder='pseudo' ><br>" // Champ pseudo — name='pseudo' est la clé qu'on récupérera avec getParameter("pseudo")
	                
	                // --- Adresse mail
	                + "<label>Adresse email :</label><br>"
	                + "<input type='email' name='labelEmail' placeholder='exemple@mail.com' ><br>" // Champ adresse mail — name='labelEmail' est la clé qu'on récupérera avec getParameter("labelEmail")
	                
	                // --- mot de passe
	                + "<label>Mot de passe :</label><br>"
	                + "<input type='password' name='mdp' placeholder='Votre mot de passe' ><br>" // Champ mot de passe — name='mdp' est la clé qu'on récupérera avec getParameter("mdp")
	                + "<input type='password' name='confirmMdp' placeholder='Confirmer votre mot de passe' ><br>"
	                
	                // --- Bouton Créer un compte ---
	                + "<input type='submit' value='Créer un compte'>" // Bouton qui déclenche l'instanciation du ControleurCreationCompte
	                
	                // --- Fin formulaire méthode GET
	                + "</form>"
                
                + "</body></html>");
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
