package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import connection.DAOAcces;

/**
 * Servlet implementation class ControleurAjtMatiere
 */
@WebServlet("/ControleurAjtAdherent")
public class ControleurAjtAdherent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurAjtAdherent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "webadherents", "root", ""); 
		Connection conn = null;
		PreparedStatement psAdh = null;
		PreparedStatement psSportif = null;
		PreparedStatement psCategorie = null ;
		
		
		try {
			
			conn = dao.getConn(); 
		    //conn.setAutoCommit(false);
		    
		    
            String nomAdh = request.getParameter("nmAdh");
            String prenomAdh = request.getParameter("pnmAdh");
            String derAnneeLic = request.getParameter("derAnneeLic");
            String numeroLic = request.getParameter("numLic");
            String anneeAdh = request.getParameter("anneeAdh");
            String numTel1 = request.getParameter("numTel1");
            String numTel2 = request.getParameter("numTel2");
            String adresse1 = request.getParameter("adresse1");
            String adresse2 = request.getParameter("adresse2");
            String mail1 = request.getParameter("mail1");
            String mail2 = request.getParameter("mail2");
            String commentaire = request.getParameter("commentaire");
            String[] categories = request.getParameterValues("categories[]");
            String role = request.getParameter("role");
         //   String categ = request.getParameter("categ");  //categ doit devenir un tableau pour pouvoir faire des insertions multiples si lusierurs catégories ont été selectionnées au moment de la création de l'adhérent
         //   ArrayList<String> allcateg = new ArrayList<>();
            for (String indice : categories) {
            	System.out.println(indice);
            }
            
            if(numTel2 == "")
            {
            	numTel2 = null;
            }
            
            if(adresse2 == "")
            {
            	adresse2 = null;
            }
            
            if(mail2 == "")
            {
            	mail2 = null;
            }
            
            if(commentaire == "")
            {
            	commentaire = null;
            }
            
     
            
            
          
            if(nomAdh == "" || prenomAdh == "" || numeroLic == "" || derAnneeLic == "" || anneeAdh == "" || numTel1 == "" || adresse1 == "" || mail1 == "" )	//A voir (verifier si == fonctionne ou si il faut mettre des .equals
            {           	
            	request.setAttribute("cs", "vide");
            	request.getRequestDispatcher("/CreationAdherent").forward(request, response);              
            }
            
            else //Modification pour effectuer une requête préparée, 08/12 10:11
            {
          String sqlAdh = "INSERT INTO adherents (numerolicence, nom, prenom, dernierelicenceactive, annee, tel1, tel2, adresse1, adresse2, mail1, mail2, commentaire, role) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
          
          psAdh = conn.prepareStatement(sqlAdh);
          psAdh.setString(1, numeroLic);
          psAdh.setString(2, nomAdh);
          psAdh.setString(3, prenomAdh);
          psAdh.setString(4, derAnneeLic);
          psAdh.setString(5, anneeAdh);
          psAdh.setString(6, numTel1);
          psAdh.setString(7, numTel2);
          psAdh.setString(8, adresse1);
          psAdh.setString(9, adresse2);
          psAdh.setString(10, mail1);
          psAdh.setString(11, mail2);
          psAdh.setString(12, commentaire);
          psAdh.setString(13, role);
          psAdh.executeUpdate();
          
          String sqlCritere = "INSERT INTO critereadherent (numerolicence, idcritere, valcritere) "
                  			+ "SELECT ?, idcritere, 0 FROM criteres;";          
          
          psSportif = conn.prepareStatement(sqlCritere);
          psSportif.setString(1, numeroLic);
          
          psSportif.executeUpdate(); 
          
          if (categories != null && categories.length != 0) {
        	  System.out.println("entrée dans la requete");
        	  String sqlCat = "";
        	  for (String indice : categories) {
        		  
		          sqlCat = "INSERT INTO categorieadherent (numLic, idcategorie) VALUES (?, ?);";
		          psAdh = conn.prepareStatement(sqlCat);
		          psAdh.setString(1, numeroLic);
		          psAdh.setString(2,  indice); 
		         // Statement  psCategorie = conn.createStatement();
		          psAdh.executeUpdate(); 
		          System.out.println(sqlCat);
        	  }
        	  System.out.println(sqlCat);
          }
        //  conn.commit();
            	
            	
            }

		 }
	    catch(SQLException e) {
			System.out.println("Probleme SQL !!");
			e.printStackTrace();
		}
	
		dao.closeConnection();
		request.getRequestDispatcher("/Accueil").forward(request, response);
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
