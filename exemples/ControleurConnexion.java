package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Adherent;
import model.Categorie;
import model.Utilisateur;
import connection.DAOAcces;

/**
 * Servlet implementation class ControleurConnexion
 */
@WebServlet("/ControleurConnexion")
public class ControleurConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       

	/**
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
		// TODO Auto-generated method stub
	
		
		String nameCheck = (String)request.getParameter("nameCo");	
		String mdpCheck = (String)request.getParameter("mdpCo");
		

		
		if(nameCheck.equals("") || mdpCheck.equals("")) {
			getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
			System.out.println("Veuillez compléter tous les champs svp !");

		}
		
		else {
			//Ouvre la connexion
			DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
			Connection conn = null;
			PreparedStatement checkUser = null;
			PreparedStatement userCateg = null;
			PreparedStatement allCateg = null ;
			String idUser = null;
			
			try {
				conn = dao.getConn();
				conn.setAutoCommit(false);
				
				String sql = "SELECT nom, prenom, role, numerolicence FROM adherents WHERE login = ? and motdepasse = ?";
				checkUser = conn.prepareStatement(sql);
				checkUser.setString(1, nameCheck);
				checkUser.setString(2,  mdpCheck);
				ResultSet identification = checkUser.executeQuery();
				
				if (identification.next()) {
					
					HttpSession h = request.getSession();  //Crée la session seulement si la requête d'identification renvoie un résultat positif
					h.setMaxInactiveInterval(300);			//Regle un timer qui détruit la session si aucune requête n'est faite au bout de 5 minutes ; chaque requête effectuée rénitialise ce délai 
					
					Timestamp tslogin = new Timestamp(System.currentTimeMillis());
					tslogin.setNanos(0);
					
					String sqLloginTime = "INSERT INTO log VALUES(DEFAULT, ?, ?, NULL, NULL, '0;');";	//Ce bloc sert à insérer en BDD un timeStamp dans la table log avec les infos de connexion de l'utilisateur
					PreparedStatement insertLoginTime = conn.prepareStatement(sqLloginTime);
					String idconnexion = ""+ identification.getString("nom") +" "+identification.getString("prenom")+" "+identification.getString("numerolicence")+"";
					insertLoginTime.setString(1, idconnexion);
					insertLoginTime.setTimestamp(2, tslogin);
					insertLoginTime.executeUpdate();
					conn.commit();
					
					PreparedStatement getIdConnexion = conn.prepareStatement("SELECT idlog FROM log WHERE idconnexion = ? AND logintime = ? ;");
					getIdConnexion.setString(1,  idconnexion);	//On récupère l'idlog correspondant  l'insertion qu'on vient de faire, qui servira plus tard pour insérer logouttime
					getIdConnexion.setTimestamp(2, tslogin);
					ResultSet rsIdConnexion = getIdConnexion.executeQuery();
					
					if (rsIdConnexion.next()); {
					
						System.out.println(tslogin);
						HashMap<String, String> categoriesUser;
						categoriesUser = new HashMap<String, String>() ;
							
						if ("admin".equals(identification.getString("role"))) {
						
							String sql3 ="select idcategorie, nomcategorie from categoriesportive order by idcategorie;";
							allCateg = conn.prepareStatement(sql3);
							ResultSet selectCategories = allCateg.executeQuery();
								
							while(selectCategories.next()) {
								
							categoriesUser.put(selectCategories.getString("idcategorie"), selectCategories.getString("nomcategorie"));
									
							}
									
							Utilisateur activeUser = new Utilisateur(identification.getString("nom"), identification.getString("prenom"),
							identification.getString("role"), identification.getString("numerolicence"), rsIdConnexion.getInt("idlog"), categoriesUser );
							h.setAttribute("activeUser", activeUser);
							System.out.println("CHECK : " + identification.getString("role"));
											
							System.out.println(rsIdConnexion.getInt("idlog"));
						}
							
						else if (!"admin".equals(identification.getString("role"))) {
							String sql3 ="SELECT categoriesportive.idcategorie, nomcategorie FROM categoriesportive INNER JOIN categorieadherent "
							+ "ON categoriesportive.idcategorie=categorieadherent.idcategorie "
							+ "INNER JOIN adherents ON categorieadherent.numLic=adherents.numerolicence "
							+ "WHERE numerolicence=? ORDER BY categoriesportive.idcategorie;" ;
									
							allCateg = conn.prepareStatement(sql3);
							allCateg.setString(1, identification.getString("numerolicence"));
							ResultSet selectCategories = allCateg.executeQuery();
														
							while(selectCategories.next()) {
							
							categoriesUser.put(selectCategories.getString("idcategorie"), selectCategories.getString("nomcategorie"));
									
							}
									
							Utilisateur activeUser = new Utilisateur(identification.getString("nom"), identification.getString("prenom"),
							identification.getString("role"), identification.getString("numerolicence"), rsIdConnexion.getInt("idlog"), categoriesUser );
							h.setAttribute("activeUser", activeUser);		
									
						}
					}
					
				}	
				
				else {
					 System.out.println("Connexion échouée");
					 dao.closeConnection();
					 getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
				 } 			
				

				 dao.closeConnection();				 
				 response.sendRedirect("Accueil");

			}				
			
			catch (SQLException e) {
			
				e.printStackTrace();
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

}
