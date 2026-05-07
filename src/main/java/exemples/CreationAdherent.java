package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Categorie;
import model.Utilisateur;
import connection.DAOAcces;
import controller.ControleurConnexion;

/*
 * Servlet implementation class CreationMatiere
 */
@WebServlet("/CreationAdherent")
public class CreationAdherent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#HttpServlet()
     */
    public CreationAdherent() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession h = request.getSession(false);
    	
    	if (h == null || h.getAttribute("activeUser") == null) { //Si la session n'existe pas, renvie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
    	
        PrintWriter out =response.getWriter();
        Utilisateur activeUser = (Utilisateur) h.getAttribute("activeUser");
        if( request.getAttribute("cs") == "vide") {
	        out.println("<script language=\"javascript\">");
	    	out.println("alert('Veuillez renseigner les champs obligatoires.')");
	    	out.println("</script>");
        }
 
			//Formulaire d'ajout d'adhérent
	        out.println("<html><head><meta charset=\"utf-8\"/>"
	        		+ "<link href=\"licence.css\" rel=\"stylesheet\"></head><body>"  //revoir l'alignement des boutons en haut de la page
	        		+ "<div align='right'> <form action='ControleurDeconnexion' name='boutondeconnexion' method='get'> <input type ='submit' name='deconnexion' value='Se déconnecter'> </form><br>"
	        		+ "<div> <form action=\"Accueil\" name=\"boutonAccueil\" value=\"accueil\" method=\"get\">"
					+ "<input text-align='left' type=\"submit\" name=\"accueil\" value=\"Accueil\"></form>"
	        		+ "<form action=\"ControleurDeconnexion\" name=\"boutondeconnexion\" method=\"get\"> <input text-align='right' type = \"submit\" name=\"deconnexion\" value=\"Se déconnecter\"></form>");
	        out.println("<div align=center><form action=\"ControleurAjtAdherent\" method=\"get\"><h1>Création d'un adhérent</h1><br><br><br>");
	        out.println("<table border=1>");
	        out.println("<tr><td>Nom de l'adhérent (*): </td><td><input type=\"text\" name=\"nmAdh\"></td></tr>");
	        out.println("<tr><td>Prénom de l'adhérent (*): </td><td><input type=\"text\" name=\"pnmAdh\"></td></tr>");
	        out.println("<tr><td>Numéro de licence (*): </td><td><input type=\"text\" name=\"numLic\"></td></tr>");
	        out.println("<tr><td>Dernière licence active (*): </td><td><input type=\"text\" name=\"derAnneeLic\"></td></tr>");
	        out.println("<tr><td>Année de naissance de l'adhérent (*): </td><td><input type=\"text\" name=\"anneeAdh\"></td></tr>");
	        out.println("<tr><td>Numéro de téléphone 1 (*): </td><td><input type=\"text\" name=\"numTel1\"></td></tr>");
	        out.println("<tr><td>Numéro de téléphone 2: </td><td><input type=\"text\" name=\"numTel2\"></td></tr>");
	        out.println("<tr><td>Adresse postale 1 (*): </td><td><input type=\"text\" name=\"adresse1\"></td></tr>");
	        out.println("<tr><td>Adresse postale 2: </td><td><input type=\"text\" name=\"adresse2\"></td></tr>");
	        out.println("<tr><td>Adresse mail 1 (*): </td><td><input type=\"text\" name=\"mail1\"></td></tr>");
	        out.println("<tr><td>Adresse mail 2: </td><td><input type=\"text\" name=\"mail2\"></td></tr>");
	        out.println("<tr><td>Contact 1: </td><td><input type=\"text\" name=\"contact1\"></td></tr>");
	        out.println("<tr><td>Contact 2: </td><td><input type=\"text\" name=\"contact2\"></td></tr>");
	        out.println("<tr><td>Sexe : </td><td><input type=\"text\" name=\"sexe\"></td></tr>");
	        out.println("<tr><td>Droit à l'image : </td><td><input type=\"text\" name=\"droitImage\"></td></tr>");
	        out.println("<tr><td>Catégorie(s) (*):</td><td><div>");	
	       //
	        out.println("<select name='categories[]' id='choix-categorie' multiple>");
	        
	        for(Map.Entry<String, String> entry : activeUser.categoriesUser.entrySet()) {
				
				out.print("<option value="+entry.getKey()+">"+entry.getValue()+"</option>");  
			}
	        
	        out.println("</select></div>");
	        
	        if (activeUser.getRole().equals("admin")) { //Rajout de la sélection du rôle au moment de la création de l'adhérent (accessible seulement par l'admin)
	        	
	        	out.println("<div>"
	        			+ "<tr><td>Rôle</td>"
	        			+ "<td><select name='role' id='choixrole'>"
	        			+ "<option value ='adherent'> Adhérent </option>"
	        			+ "<option value ='modif'> Responsable de catégorie </option>"
	        			+ "<option value = 'admin'> Administrateur </option>"
	        			+ "</select></td></tr></div>");	        	
	        }
	        

	        out.println("</table>");
	        out.println("Commentaire: <br> <textarea rows=4 cols=40 name=\"commentaire\"></textarea><br>");

	        out.print("<br><br><input type=\"submit\" value=\"Valider\"/></form></div>");
	        out.print("Renseignements obligatoires: (*)");


			out.println("<form action=\"ControleurDeconnexion\" name=\"boutondeconnexion\" method=\"get\"> <input type = \"submit\" name=\"deconnexion\" value=\"Se déconnecter\"> </form>"
						+ "</div></body></html>");      
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);

    }

}