package controleur;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import connection.DAOAcces;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utilisateur;


/**
 * Servlet implementation class ControleurDeconnexion
 */
@WebServlet("/ControleurDeconnexion")
public class ControleurDeconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurDeconnexion() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession h = request.getSession(false);	
	
		if (h != null) {
			
			h.invalidate();
		
		}
		
		getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
		
		
	}
	//Fin déconnexion	
}

