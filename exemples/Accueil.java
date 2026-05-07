package view;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
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

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    //Connexion à la DB et affichage du formulaire
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession h = request.getSession(false);
	    
	    if (h == null) { //Si la session n'existe pas, renvie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
	    
	    Utilisateur activeUser = (Utilisateur) h.getAttribute("activeUser");
		String categorie = null;
		PrintWriter out=response.getWriter();		
			
			out.println("<html><head><meta charset=\"utf-8\"/> "
					+"<link href=\"licence.css\" rel=\"stylesheet\">"
					+"</head><body><table width=100%><tr><td>");
			
			if(activeUser.getRole().equals("admin") || activeUser.getRole().equals("modif"))
			{
				categorie = "";
				out.print("<form action=\"ControleurAccueil\" name=\"boutonajout\" method=\"get\"> <input type = \"submit\" name='direction' value=\"Catégories\"> </form>");
				out.print("<form action=\"ControleurAccueil?direction=2 name= \"boutonajout\" value = \"ajoutAdherent\" method=\"get\"> <input type = \"submit\" name = 'direction' value=\"Créer un adhérent\"> </form>");	
			}
			
					out.println("</td><td><div align=\"right\">"+ activeUser.getPrenom() +" "+activeUser.getNom()+" "+activeUser.getRole()+"</div><br>"
					+ "<div align='right'> <form action='ControleurDeconnexion' name='boutondeconnexion' method='get'> <input type ='submit' name='deconnexion' value='Se déconnecter'> </form><br>"
					+ "<form action='ControleurAccueil' name='accesprofil' method='get'> <input type ='submit' name='direction' value='Profil'> </form></div></td></tr>"
					+ "<tr><td><form action = \"ControleurAccueil\" method='get'> <input type='hidden' name='ficheadmin' value='ficheadmin'> <input type=\"submit\"  value='Consulter les fiches Administratives'> </form><br>"
					+ "<form action=\"RechercheAdherent\" method='get'> <input type='hidden' name='recherche' value='recherche'> <input type=\"submit\" value=\"Rechercher un adhérent\"> </form> <br>"
					+ "<form action=\"ControleurAccueil\" method='get'> <input type='hidden' name='fichesport' value='fichesport'> <input type=\"submit\" value=\"Consulter les fiches sportives "+categorie+"\"> </form><br>"
					+ "<form action =\"ControleurAccueil\" method='GET'> <input type='hidden' name='critere' value='critere'><input type='submit' name='critere' value='Consulter les critères'></form></td>");

	        
	        out.println("<td><div align=center><form align=center action=\"ControleurAccueil\" method=get >"
					+ "<h1>Licenciés par catégorie(s)</h1><br><br><h2>Choisissez une catégorie parmi les suivantes:</h2><br>"+
					"<select multiple=\"multiple\" name=\"categorie[]\" size=\"5\">");

			out.print("<option value='Toutes'>Toutes</option><br><br>"); 
			for(Map.Entry<String, String> entry : activeUser.categoriesUser.entrySet()) {
					out.println();
					out.print("<option value="+entry.getKey()+">"+entry.getValue());  
					out.print("</option><br><br>");  
				}

			out.print("</select><p /><br><br><input type=\"submit\" name=\"categorie\" value=\"Valider\" /> <p /></form></div></td></tr>");
			out.println("</table>");	
			out.print("</body></html>");
	}	
		 
		  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
