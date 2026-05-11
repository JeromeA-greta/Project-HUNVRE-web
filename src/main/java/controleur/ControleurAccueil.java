package controleur;

import java.io.IOException;
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

/**
 * Servlet implementation class ControleurAccueil
 */


@WebServlet("/ControleurAccueil")
	public class ControleurAccueil extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //int direction = Integer.parseInt(request.getParameter("direction"));
    	String direction = request.getParameter("direction");

        switch (direction) {

            case "Profil":
                // Profil
                request.getRequestDispatcher("/Profil")
                       .forward(request, response);
                break;

            case "Nouvelle partie":
                // Lancer partie
            	lancerPartie(request, response);
                break;

            case "Tableau des scores":
                // Scores
                afficherScores(request, response);
                break;

            case "Quitter":
                // Quitter (logout)
                //response.sendRedirect("index.jsp");
                //response.sendRedirect("/Connexion");
                request.getRequestDispatcher("/ControleurDeconnexion")
                .forward(request, response);
                break;
        }
    }

    private void lancerPartie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "hunvre", "root", "");

        ArrayList<String> deck = new ArrayList<>();

        try {
            ResultSet rs = dao.getStatement().executeQuery(
                "SELECT id_carte, valeur, recto, couleur FROM carte"
            );

            while (rs.next()) {
                deck.add(rs.getString("recto")); // simplifié
            }

            request.setAttribute("deck", deck);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.closeConnection();
        }

        request.getRequestDispatcher("/Partie")
               .forward(request, response);
    }

    private void afficherScores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAOAcces dao = new DAOAcces("com.mysql.cj.jdbc.Driver", "hunvre", "root", "");

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<String> pseudos = new ArrayList<>();

        try {
            String sql = "SELECT pseudo, score FROM utilisateur ORDER BY score DESC LIMIT 5";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pseudos.add(rs.getString("pseudo"));
                scores.add(rs.getInt("score"));
            }

            request.setAttribute("pseudos", pseudos);
            request.setAttribute("scores", scores);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.closeConnection();
        }

        request.getRequestDispatcher("/Scores")
               .forward(request, response);
    }
}