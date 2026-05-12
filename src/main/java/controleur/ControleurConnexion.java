package controleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarteJeu;
import model.DeckJoueur;
import model.Utilisateur;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DAOAcces;

/**
 * Servlet implementation class ControleurConnexion
 */
@WebServlet("/ControleurConnexion")
public class ControleurConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public static Utilisateur joueur ;

    /**
     * Contrôleur de connexion : gère les actions depuis la vue Connexion.
     * @see HttpServlet#HttpServlet()
     */
    public ControleurConnexion() {
        super();

        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
			//récupére l'identifiant et le mot de passe entrés par l'utilisateur-ice dans la vue Connexion
			String mailCheck = (String)request.getParameter("labelEmail");	
			String mdpCheck = (String)request.getParameter("mdp"); 
			
			//Test
			System.out.println("controleur co instancié & le mail entré est : "+ mailCheck);
			
			//Si les champs sont vides, renvoie sur la vue Connexion (mais il manque l'affichage dans la vue Connewion d'un message d'erreur
			if(mailCheck == null || mailCheck.trim().isEmpty() || mdpCheck == null || mdpCheck.trim().isEmpty())   {
				request.setAttribute("erreur", "Veuillez remplir tous les champs");
				getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
				System.out.println("Veuillez compléter tous les champs svp !");
			}
			
			boolean redirectionOk = false;
			DAOAcces dao = null;
			
			try {	
				//Ouvre la connexion
				dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "hunvre", "root", "");
				PreparedStatement checkUser = null;
				
				String sql = "SELECT * FROM utilisateur WHERE mail = ? AND mdp = ?";
				checkUser = dao.getConn().prepareStatement(sql);
				checkUser.setString(1, mailCheck);
				checkUser.setString(2, mdpCheck);
				ResultSet identification = checkUser.executeQuery();				
				
				if (identification.next()) {
					
					//Crée la variable de session h seulement si la requête d'identification renvoie un résultat positif
					HttpSession h = request.getSession();  
					
					// instanciation de l'objet Utilisateur dans la variable joueur
					joueur = new Utilisateur(identification.getString("pseudo"), identification.getString("mail"),
							new DeckJoueur(), identification.getString("role"));
                	
					// Récupération du deck sauvegardé
                   	chargerDeck(dao, identification);

                	h.setAttribute("joueur", joueur); // Place l'instance de Utilisateur "joueur" dans la variable de session 
                	System.out.println(joueur.getDeck());
                	redirectionOk = true;
				}

			} catch (SQLException e) {
			    e.printStackTrace();
			    System.out.println("Pb connexion SQL - connexion échoué");
			//fermer la connexion
			} finally {
			    dao.closeConnection();
			    if (redirectionOk) {
			        getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
			    } else {
			    	request.setAttribute("erreur", "Identifiants incorrects !");
			        getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
			    }
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void chargerDeck(DAOAcces dao, ResultSet identification) throws SQLException {
	    try {
	    	PreparedStatement pstprofil = dao.getConn().prepareStatement(
	        "SELECT id_carte, valeur, recto, couleur FROM carte;");
	    
	    	ResultSet rsprofil = pstprofil.executeQuery();
	    	
	    	while (rsprofil.next()) {
	    		joueur.getDeck().ajoutercarte(new CarteJeu(
	    			rsprofil.getInt(1),
		            rsprofil.getInt(2),
		            rsprofil.getString(3),
		            1,
		            rsprofil.getString(4)));
	    		System.out.println("rsprofil" + rsprofil.getInt(1));
		    }
	    	//System.out.println(joueur.getDeck());
	    } catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Pb connexion SQL charger deck");
        }
	}
}
