package controleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DAOAcces;

@WebServlet("/ControleurCreationCompte")
public class ControleurCreationCompte extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Vérifie que la chaîne correspond au pattern regex (je vais mettre cette méthode tout en bas quand j'aurais terminé de coder ce controleur)
    public boolean patternMatches(String userInput, String regexPattern) {
        return Pattern.compile(regexPattern)
            .matcher(userInput)
            .matches();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Récupération des infos saisies dans la vue CreationCompte
        String pseudoCheck = request.getParameter("pseudo");
        String labelEmailCheck = request.getParameter("labelEmail");
        String mdpCheck = request.getParameter("mdp");
        String confirmMdpCheck = request.getParameter("confirmMdp");
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // cas 1 : champs vides
        if (pseudoCheck.equals("") || labelEmailCheck.equals("") || mdpCheck.equals("") || confirmMdpCheck.equals("")) {
            request.setAttribute("erreur", "Veuillez remplir tous les champs.");
            getServletContext().getRequestDispatcher("/CreationCompte").forward(request, response);

        // cas 2 : format mail invalide
        } else if (!patternMatches(labelEmailCheck, regexPattern)) {
            request.setAttribute("erreur", "Format de l'adresse mail invalide.");
            getServletContext().getRequestDispatcher("/CreationCompte").forward(request, response);

        // cas 3 : mots de passe différents
        } else if (!mdpCheck.equals(confirmMdpCheck)) {
            request.setAttribute("erreur", "Les mots de passe ne correspondent pas.");
            getServletContext().getRequestDispatcher("/CreationCompte").forward(request, response);

        // cas 4 : tout est OK → vérification BDD puis insertion
        } else {
            try {
            	// Punaise ce serait cool un DBAcces avec les identifiants écrit à un seul endroit et utiliser des variables à chaque new DAOAcces...
                DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "hunvre", "root", "");
                
                // On écrit la question en SQL
                String sqlVerifMail = "SELECT mail FROM utilisateur WHERE mail = ?";
                // On la prépare (on l'envoie à MySQL pour compilation)
                PreparedStatement pstVerifMail = dao.getConn().prepareStatement(sqlVerifMail); 
                // On remplace le ? par le vrai mail saisi dans le formulaire
                pstVerifMail.setString(1, labelEmailCheck); 
                // On envoie la question, MySQL répond,
                // on stocke la réponse dans rsVerifMail
                ResultSet rsVerifMail = pstVerifMail.executeQuery();
                
                if (rsVerifMail.next()) {
                	
                	request.setAttribute("erreur", "L'adresse mail que vous avez saisie existe déjà");
                    getServletContext().getRequestDispatcher("/CreationCompte").forward(request, response);
                
                } else {
                	
                	// 
                	String strInsertNouveauCompte = "INSERT INTO utilisateur (pseudo, mdp, mail, role) VALUES (?, ?, ?, ?)";

                    // Création d'une requête préparée
    	    		PreparedStatement pstNouveauCompte = dao.getConn().prepareStatement(strInsertNouveauCompte);
    	    		
    	    		pstNouveauCompte.setString(1, pseudoCheck);
    	    		pstNouveauCompte.setString(2, mdpCheck);
    	    		pstNouveauCompte.setString(3, labelEmailCheck);
    	    		// J'ai écrit "joueur" en dur...
    	    		pstNouveauCompte.setString(4, "joueur");
    	    		// Askip executeUpdate() c'est mieux pour un INSERT
    	    		pstNouveauCompte.executeUpdate();
    	    		
    	    		dao.closeConnection();
    	    		
    	    		getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
                }         
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}