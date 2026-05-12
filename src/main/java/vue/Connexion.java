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

        response.setContentType("text/html"); // Dit au navigateur : ce que je t'envoie est du HTML
        PrintWriter out = response.getWriter(); // Ouvre le flux d'écriture vers le navigateur

        // Récupérer le message d'erreur du controleurConnexion
        String erreur = (String) request.getAttribute("erreur");
       
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

                    // Afficher le message d'erreur
                    + (erreur !=null ? "<p style='color:red'>" + erreur + "</p>": "")
                    
                    // --- Formulaire connexion en POST ---
                    + "<form id='form-connexion' action='ControleurConnexion' method='POST'>"
                    + "<table id='table-connexion'>"

                        // --- Ligne email ---
                        + "<tr>"
                        +     "<td><label>Adresse email :</label></td>"
                        +     "<td><input id='input-email' type='email' name='labelEmail' placeholder='exemple@mail.com' /></td>"
                        + "</tr>"

                        // --- Ligne mot de passe ---
                        + "<tr>"
                        +     "<td><label>Mot de passe :</label></td>"
                        +     "<td><input id='input-mdp' type='password' name='mdp' placeholder='Votre mot de passe' /></td>"
                        + "</tr>"

                        // --- Ligne bouton connexion ---
                        + "<tr>"
                        +     "<td colspan='2'><button id='btn-connexion' type='submit' name='connexion'>Se connecter</button></td>"
                        + "</tr>"

                    + "</table>"
                    + "</form>"

                    // --- Formulaire création de compte en GET - modif pauline 7/05/26 14:38 ---
                    + "<form id='form-creation' action='CreationCompte' method='GET'>"
                    +     "<button id='btn-creation' type='submit' name='creation'>Créer un compte</button>"
                    + "</form>"

                + "</body></html>");
    }
}