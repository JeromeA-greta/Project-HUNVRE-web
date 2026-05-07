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
		   
		// Si l'utilisateur-ice a cliqué sur le bouton Connexion
		if ("connexion".equals(request.getParameter("connexion"))) { //ENLEVER ce If si PAS de vue CreationCompte !!!
			
			//récupérer l'identifiant et le mot de passe entrés par l'utilisateur-ice dans la vue Connexion
			String identifiantCheck = (String)request.getParameter("identifiantCo"); //identifiantCo a changé selon le nom de la variable dans Connexion	
			String mdpCheck = (String)request.getParameter("mdpCo"); // idem
			
			//Si les champs sont vides, renvoie sur la vue Connexion (mais il manque l'affichage dans la vue Connewion d'un message d'erreur
			if(identifiantCheck == null || identifiantCheck.trim().isEmpty() || mdpCheck == null || mdpCheck.trim().isEmpty())   {
				getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
				System.out.println("Veuillez compléter tous les champs svp !");
			}
			
			else {
				//Ouvre la connexion
				DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "hunvre", "root", "");
				Connection conn = null;
				PreparedStatement checkUser = null;
				
				try {
					conn = dao.getConn();
					conn.setAutoCommit(false); //Transaction
					
					String sql = "SELECT * FROM utilisateur WHERE mail = ? AND mdp = ?";
					checkUser = conn.prepareStatement(sql);
					checkUser.setString(1, identifiantCheck);
					checkUser.setString(2,  mdpCheck);
					ResultSet identification = checkUser.executeQuery();
					
					if (identification.next()) {
						
						HttpSession h = request.getSession();  //Crée la session seulement si la requête d'identification renvoie un résultat positif
						joueur = new Utilisateur(identification.getString("pseudo"), identification.getString("mail"), new DeckJoueur(), identification.getString("role"));
	                	// Récupération du deck sauvegardé
	                	// Pour l'instant il n'y a pas de bouton pour reprendre une partie
	                	try {
	                		PreparedStatement pstprofil = dao.getConn().prepareStatement(
	                				"SELECT * FROM deck_carte WHERE ref_utilisateur = ?");
	                		pstprofil.setInt(1, identification.getInt(1));
	                		
	                		ResultSet rsprofil = pstprofil.executeQuery();
	                		if (rsprofil.next()) {
	                			joueur.getDeck().add(new CarteJeu(
	        							rsprofil.getInt(1),
	        							rsprofil.getInt(2),
	        							rsprofil.getString(3),
	        							1,
	        							rsprofil.getString(4)));
	                		}
	                		h.setAttribute("joueur", joueur);
	                	} finally {
	            			
	       				 dao.closeConnection();		
	       				}
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Pb connexion SQL");		
				
				 
				 response.sendRedirect("/Accueil");
			}
		
		}
	
	
	 // creationcompte if ("creation.eaquals(request.getParameter("creation"){}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
