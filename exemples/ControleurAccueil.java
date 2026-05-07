package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DAOAcces;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Adherent;
import model.Categorie;
import model.Utilisateur;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Servlet implementation class ControleurAccueil
 */
@WebServlet("/ControleurAccueil")
public class ControleurAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurAccueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession h = request.getSession(false);	//Charge la variable de session si elle existe (false)
		String page;
		if (h == null) { //Si la session n'existe pas, renvie vers la page de connexion
		    response.sendRedirect("/Connexion");
		    return;
		}
		
		Utilisateur activeUser = (Utilisateur) h.getAttribute("activeUser");	//Récupère le profil de l'utilisateur de la session en cours
		String direction =  (String)request.getParameter("direction");		//Sert à savoir sur quel bouton on a cliqué sur la page accueil (catégories, accéder à mon profil, créer un adhérent)
		HashMap<String, String> categoriesAdh = new HashMap<>();		//Créé la HashMap qui stocke les catégorie sportives associées  l'utilisateur en cours
		
		
		if ("ficheadmin".equals(request.getParameter("ficheadmin")) && !activeUser.getRole().equals("admin")) {  //tout ce bloc permet d'afficher seulement les adhérents qui appartiennt à la même catégorie que l'utilisateur actif, si celui ci n'est pas l'admin 
		
			Connection conn = null;
			PreparedStatement psAdhCateg = null;
			page = " 1;";
			
			ArrayList<Adherent> adherents = new ArrayList<Adherent>(); //Instancie une liste vide d'adhérents
			DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
			
			
			
			try { //Prépare la requête qui récupère les infos des adhérents dont les catégories sportives correspondent à l'utilisateur en cours
				
				activeUser.lastseen(activeUser.getIdConnexion(), page);
				
				conn = dao.getConn();
				conn.setAutoCommit(false);

				String sql =
				"SELECT a.numerolicence, a.nom, a.prenom, a.dernierelicenceactive, a.annee, " +
				"a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, " +
				"a.contact1, a.contact2, a.sexe, a.droitimage, " +
				"c.idcategorie, c.nomcategorie " +
				"FROM adherents a " +
				"LEFT JOIN categorieadherent ca ON a.numerolicence = ca.numLic " +
				"LEFT JOIN categoriesportive c ON ca.idcategorie = c.idcategorie " +
				"WHERE ca.idcategorie IN (";
					
					for (String id : activeUser.getCategoriesUser().keySet()) {		//Ajoute un nombre de ? dans la requête égal au nombre de catégories associées à l'utilisateur
						sql += "?, " ;
					}
					 
					sql = sql.substring(0, sql.length() -2);
					sql+=  ") ORDER BY a.numerolicence;";
					  
					psAdhCateg = conn.prepareStatement(sql);
					  
					int i = 1;
					  	
					for (Map.Entry<String, String> entry : activeUser.categoriesUser.entrySet()) {	//Prépare la requête avec les catégories associées à l'utilisateur
							psAdhCateg.setString(i,  entry.getKey());
							i++;
					}
					
					ResultSet rsAdh = psAdhCateg.executeQuery();

					String currentLicence = null;

					while (rsAdh.next()) {

					    String licence = rsAdh.getString("numerolicence");

					    // Nouvel adhérent
					    
					    if (!licence.equals(currentLicence)) {

					        categoriesAdh = new HashMap<>();

					        adherents.add(new Adherent(
					            licence,
					            rsAdh.getString("nom"),
					            rsAdh.getString("prenom"),
					            rsAdh.getString("annee"),
					            rsAdh.getString("tel1"),
					            rsAdh.getString("tel2"),
					            rsAdh.getString("adresse1"),
					            rsAdh.getString("adresse2"),
					            rsAdh.getString("mail1"),
					            rsAdh.getString("mail2"),
					            rsAdh.getString("commentaire"),
					            rsAdh.getString("dernierelicenceactive"),
					            rsAdh.getString("contact1"),
					            rsAdh.getString("contact2"),
					            rsAdh.getString("sexe"),
					            rsAdh.getString("droitimage"),
					            categoriesAdh
					            //dao
					        ));

					        currentLicence = licence;
					    }

					    // Ajout d’une catégorie si elle existe
					    if (rsAdh.getString("idcategorie") != null) {
					        categoriesAdh.put(
					            rsAdh.getString("idcategorie"),
					            rsAdh.getString("nomcategorie")
					        );
					    }
					}	
			}
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				request.setAttribute("adherents", adherents);
				dao.closeConnection();
				getServletContext().getRequestDispatcher("/Tableau").forward(request, response);
		}
		
		if ("ficheadmin".equals(request.getParameter("ficheadmin")) && activeUser.getRole().equals("admin")) {  //ce bloc sert à afficher tous les adhérents independamment de la catégorie de l'utilisateur actif si celui ci est l'admin 
			
			Connection conn = null;
			PreparedStatement psAdh = null;	
			page = " 2;";
			
			ArrayList<Adherent> adherents = new ArrayList<Adherent>();
			DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
			
			try {
				
				conn = dao.getConn();
				conn.setAutoCommit(false);
				
				activeUser.lastseen(activeUser.getIdConnexion(), page);

				String sql = "SELECT a.numerolicence, a.nom, a.prenom, a.dernierelicenceactive, a.annee, " +
							 "a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, " +
							 "a.contact1, a.contact2, a.sexe, a.droitimage, " +
							 "c.idcategorie, c.nomcategorie " +
							 "FROM adherents a " +
							 "LEFT JOIN categorieadherent ca ON a.numerolicence = ca.numLic " +
							 "LEFT JOIN categoriesportive c ON ca.idcategorie = c.idcategorie " +
							 "ORDER BY a.numerolicence";

				psAdh = conn.prepareStatement(sql);
				ResultSet rsAdh = psAdh.executeQuery();

				String currentLicence = null;
				
				while (rsAdh.next()) {

				    String licence = rsAdh.getString("numerolicence");

				    // Nouvel adhérent
				    if (!licence.equals(currentLicence)) {

				        categoriesAdh = new HashMap<>();

				        adherents.add(new Adherent(
				            licence,
				            rsAdh.getString("nom"),
				            rsAdh.getString("prenom"),
				            rsAdh.getString("annee"),
				            rsAdh.getString("tel1"),
				            rsAdh.getString("tel2"),
				            rsAdh.getString("adresse1"),
				            rsAdh.getString("adresse2"),
				            rsAdh.getString("mail1"),
				            rsAdh.getString("mail2"),
				            rsAdh.getString("commentaire"),
				            rsAdh.getString("dernierelicenceactive"),
				            rsAdh.getString("contact1"),
				            rsAdh.getString("contact2"),
				            rsAdh.getString("sexe"),
				            rsAdh.getString("droitimage"),
				            categoriesAdh
				           //dao
				        ));

				        currentLicence = licence;
				    }

				    // Ajout d’une catégorie si elle existe
				    if (rsAdh.getString("idcategorie") != null) {
				        categoriesAdh.put(
				            rsAdh.getString("idcategorie"),
				            rsAdh.getString("nomcategorie")
				        );
				    }
				}
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			} 
			
			request.setAttribute("adherents", adherents);
			dao.closeConnection();
			getServletContext().getRequestDispatcher("/Tableau").forward(request, response);
		}
		
		//Bouton "Consulter les critères" cliqué 
		if("critere".equals(request.getParameter("critere"))) { 
			
			ArrayList<String> criteres= new ArrayList<>();
			
			try {
				DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
				
				String critereSQL = "SELECT nomcritere FROM criteres";
				
				PreparedStatement pstCritere = dao.getConn().prepareStatement(critereSQL);
				
				ResultSet rsCritere = pstCritere.executeQuery();
				
				while(rsCritere.next()) {
					criteres.add(rsCritere.getString("nomcritere"));
				}
				System.out.println("criteres : " + criteres);
				request.setAttribute("critères", criteres);				
				dao.closeConnection();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Problème SQL Critere");
			}
			
			getServletContext().getRequestDispatcher("/Critere").forward(request, response);
		}
		
		if("Catégories".equals(direction)) {  //Ca c'est si on clique sur le bouton Categories en haut à gauche de l'accueil : pour le moment, renvoie une erreur
			
			DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
			ArrayList<Categorie> categories= new ArrayList<Categorie>();
			page = " 3;";
			
			try {
				
				activeUser.lastseen(activeUser.getIdConnexion(), page);
				
				String req = "SELECT * FROM anneecategorie";
				ResultSet rscat = dao.getStatement().executeQuery(req);
				
				while(rscat.next()) {
					categories.add(new Categorie(rscat.getString("categories"), rscat.getString("annee"),  dao));
				}
				
				request.setAttribute("categories", categories);				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dao.closeConnection();
			getServletContext().getRequestDispatcher("/Categories").forward(request, response);
		}
		
		else if("Créer un adhérent".equals(direction)) {
			
			getServletContext().getRequestDispatcher("/CreationAdherent").forward(request, response);
		}
		
		else if ("Profil".equals(direction)) {	//Sert à charger les infos de profil de l'utilisateur en cours
			System.out.println("Entrée vers le profil");
			
			if (h.getAttribute("activeAdherent") == null) {	//ne s'effectue que lors du premier accès au profil, puis stocke les données dans la session
				
				page = " 4;";
				
				Adherent activeAdherent = null; //Initialise l'objet Adherent
				HashMap<String, Integer> activeAdherentCriteres = new LinkedHashMap<>() ; //linkedHashMap pour garder l'ordre d'insertion à l'affichage
				
				System.out.println("entrée dans le if");
				
				Connection conn = null;
				PreparedStatement psProfilAdh = null;			
				DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
								
				try {
					
					activeUser.lastseen(activeUser.getIdConnexion(), page);
					
					conn = dao.getConn();
					conn.setAutoCommit(false);
	
					String sql = "SELECT a.numerolicence, a.nom, a.prenom, a.dernierelicenceactive, a.annee, " +
								 "a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, " +
								 "a.contact1, a.contact2, a.sexe, a.droitimage, " +
								 "c.idcategorie, c.nomcategorie " +
								 "FROM adherents a " +
								 "LEFT JOIN categorieadherent ca ON a.numerolicence = ca.numLic " +
								 "LEFT JOIN categoriesportive c ON ca.idcategorie = c.idcategorie " +
								 "WHERE a.numerolicence = ?";
					
					psProfilAdh = conn.prepareStatement(sql);
					psProfilAdh.setString(1, activeUser.getId());
				
					ResultSet rsProfilAdh = psProfilAdh.executeQuery();
					conn.commit();
					String currentLicence = null;
					
					while (rsProfilAdh.next()) {
	
					    String licence = rsProfilAdh.getString("numerolicence");
	
					    // Nouvel adhérent
					    if (!licence.equals(currentLicence)) {
	
					        categoriesAdh = new HashMap<>();
	
					        activeAdherent = new Adherent (
					            licence,
					            rsProfilAdh.getString("nom"),
					            rsProfilAdh.getString("prenom"),
					            rsProfilAdh.getString("annee"),
					            rsProfilAdh.getString("tel1"),
					            rsProfilAdh.getString("tel2"),
					            rsProfilAdh.getString("adresse1"),
					            rsProfilAdh.getString("adresse2"),
					            rsProfilAdh.getString("mail1"),
					            rsProfilAdh.getString("mail2"),
					            rsProfilAdh.getString("commentaire"),
					            rsProfilAdh.getString("dernierelicenceactive"),
					            rsProfilAdh.getString("contact1"),
					            rsProfilAdh.getString("contact2"),
					            rsProfilAdh.getString("sexe"),
					            rsProfilAdh.getString("droitimage"),
					            categoriesAdh
					           //dao
					        );
					        
					        currentLicence = licence;
					    }
	
					    // Ajout d’une catégorie si elle existe
					    if (rsProfilAdh.getString("idcategorie") != null) {
					        categoriesAdh.put(
					        	rsProfilAdh.getString("idcategorie"),
					        	rsProfilAdh.getString("nomcategorie")
					        );
					    }
					}

					String critereSQL = "SELECT valcritere, nomcritere "
									+ "FROM critereadherent JOIN criteres "
									+ "ON critereadherent.idCritere = criteres.idcritere "
									+ "WHERE numerolicence=? "
									+ "ORDER BY critereadherent.idcritere;"; //ordre ascendant des idcriteres
					
					// Création d'une requête préparée
					PreparedStatement pstactiveAdherentCriteres = dao.getConn().prepareStatement(critereSQL);
					
					pstactiveAdherentCriteres.setString(1, activeUser.getId());
													
					ResultSet rsactiveAdherentCritere = pstactiveAdherentCriteres.executeQuery();
					System.out.println(critereSQL);

					
					//remplir une HashMap avec le résultat de la requête
					while(rsactiveAdherentCritere.next()) {
						
						activeAdherentCriteres.put(rsactiveAdherentCritere.getString("nomcritere"), rsactiveAdherentCritere.getInt("valcritere"));
					
					}	
				} 
				
				catch (SQLException e) {
					e.printStackTrace();
				} 
				
				h.setAttribute("activeAdherent", activeAdherent);
				h.setAttribute("activeAdherentCriteres", activeAdherentCriteres); //stocker la hashmap dans la session
				dao.closeConnection();
			}
			
			System.out.println("redirection vers le profil");
				
			getServletContext().getRequestDispatcher("/Profil").forward(request, response);
		}
		
		else if ("Valider".equals(request.getParameter("categorie"))) {  //si on a choisi une/plusieurs/toutes les catégories sur l'écran d'accueil
			
			h.setAttribute("cat", request.getParameterValues("categorie[]"));
			
			Connection conn = null;
			PreparedStatement psAdh = null;
			String sql;			
			ArrayList<Adherent> adherents = new ArrayList<Adherent>(); //en arrivant d'accueil, instancie une liste vide d'adhérents
			DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", "");
		

				String[] categorie = request.getParameterValues("categorie[]"); 
				
				 //début : ajout du 17/03/2026 - pauline
				if (categorie == null || categorie.length == 0) { /* traite l'erreur où l'utilisateur coche rien et où il accède au site via l'url */
					request.getSession().setAttribute("erreur", "Veuillez sélectionner au moins une catégorie.");
					getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
				    return; // Arrête l'exécution de doGet() ICI, retour à l'accueil, le code reprend au début
				} //fin 
				
				try {
					
					page = " 5;";
					conn = dao.getConn(); 
					conn.setAutoCommit(false);
					boolean flag = false;
					activeUser.lastseen(activeUser.getIdConnexion(), page);
					
				for (String categ : categorie) {
				
					if (categ.equals("Toutes")) {
						
						flag = true;
					
					}
				}
			
			
				if (flag == false) {  //Si on a choisi une ou plusieurs catégories sur le menu déroulant
					
					sql = "SELECT a.numerolicence, a.nom, a.prenom, a.dernierelicenceactive, a.annee, " +
					"a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, " +
					"a.contact1, a.contact2, a.sexe, a.droitimage, GROUP_CONCAT(c.nomcategorie) AS categories " +
					"FROM categoriesportive c " +
					"JOIN categorieadherent ca ON ca.idcategorie = c.idcategorie " +
					"JOIN adherents a ON ca.numLic = a.numerolicence " +
					"WHERE a.numerolicence IN (SELECT ca2.numLic FROM categorieadherent ca2 WHERE ca2.idcategorie IN (";
		
					for (String categ : categorie) {
						
						sql += "?, ";
						
					}
					
					sql = sql.substring(0, sql.length() -2);
					sql += ")) GROUP BY  a.numerolicence, a.nom,  a.prenom, a.dernierelicenceactive, a.annee,"
						+ "    a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, a.contact1,"
						+ "    a.contact2, a.sexe, a.droitimage;";
		 
					psAdh = conn.prepareStatement(sql);
		 
					int i = 1 ;
		  	
					for (String categ : categorie) {  
						
						int valeur = Integer.parseInt(categ);
						psAdh.setLong(i,  valeur);
						i++;
					}	
	
				}
			
				else {		//Si on a choisi toutes les catégories

					sql = "SELECT a.numerolicence, a.nom, a.prenom, a.dernierelicenceactive, a.annee, " +
						  "a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, " +
						  "a.contact1, a.contact2, a.sexe, a.droitimage, " +
						  "GROUP_CONCAT(c.nomcategorie) AS categories " +
						  "FROM adherents a " +
						  "LEFT JOIN categorieadherent ca ON a.numerolicence = ca.numLic " +
						  "LEFT JOIN categoriesportive c ON ca.idcategorie = c.idcategorie " +
						  "WHERE a.numerolicence IN (SELECT ca2.numLic FROM categorieadherent ca2 WHERE ca2.idcategorie IN (";
						
					for (String id : activeUser.getCategoriesUser().keySet()) {
						
							sql += "?, " ;
					}
					
					sql = sql.substring(0, sql.length() -2);
					
					sql+=")) GROUP BY  a.numerolicence, a.nom,  a.prenom, a.dernierelicenceactive, a.annee,"
						+"    a.tel1, a.tel2, a.adresse1, a.adresse2, a.mail1, a.mail2, a.commentaire, a.contact1,"
						+"    a.contact2, a.sexe, a.droitimage;";
						  
					psAdh = conn.prepareStatement(sql);
						  
					int i = 1;
						  	
					for (Map.Entry<String, String> entry : activeUser.categoriesUser.entrySet()) {
							
						int valeur = Integer.parseInt(entry.getKey());
						psAdh.setLong(i,  valeur);
						psAdh.setLong(i, valeur);
						i++;
					}
				}
		
				System.out.println(psAdh);
				
				ResultSet rsAdherent = psAdh.executeQuery();
			
				String currentlicence = null ;
				
				while (rsAdherent.next()) {

					String licence = rsAdherent.getString("numerolicence");
					 
					if (!licence.equals(currentlicence)) {
						
						categoriesAdh = new HashMap<>();

				        adherents.add(new Adherent(
				            licence,
				            rsAdherent.getString("nom"),
				            rsAdherent.getString("prenom"),
				            rsAdherent.getString("annee"),
				            rsAdherent.getString("tel1"),
				            rsAdherent.getString("tel2"),
				            rsAdherent.getString("adresse1"),
				            rsAdherent.getString("adresse2"),
				            rsAdherent.getString("mail1"),
				            rsAdherent.getString("mail2"),
				            rsAdherent.getString("commentaire"),
				            rsAdherent.getString("dernierelicenceactive"),
				            rsAdherent.getString("contact1"),
				            rsAdherent.getString("contact2"),
				            rsAdherent.getString("sexe"),
				            rsAdherent.getString("droitimage"),
				            categoriesAdh
				            //dao
				        ));
	
				       currentlicence = licence;
			      
			       
				       // Ajout d’une catégorie si elle existe
				       	if (rsAdherent.getString("categories") != null) {
				    	   
				       		categoriesAdh.put(rsAdherent.getString("categories"), rsAdherent.getString("categories"));
				    	   
				       	}
					}		    			
				}						
			}
			 		
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dao.closeConnection();
			String arrive = "ContrAcc" ;  //pas ouf
			request.setAttribute("adherents", adherents);
			//response.sendRedirect("Tableau");
			getServletContext().getRequestDispatcher("/Tableau").forward(request, response);

		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}