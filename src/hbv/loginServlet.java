package hbv;

import java.sql.*;
import java.util.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginVrai")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperation des parametres de la requête
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username != null && username.isEmpty() && password != null && password.isEmpty()) {
			response.sendRedirect("login.html?error=missingFields");
			return;
		}
		// Chargement du Driver JDBC
/*		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("La classe Driver n'existe pas");
			e.printStackTrace();
		}
*/
		// Création de la connexion à la base de données
		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://mysql-server:3306/jguimfackjeuna_db", "jguimfackjeuna", "gR7cqZhgai0fATxTMAMO" ) ) {
			// Création de la session utilisateur
			HttpSession session = request.getSession();

			// Création du thread pour la vérification des informations de connexion

			Thread verifyLoginThread = new Thread (new Runnable() {
				@Override
				public void run() {
					// Vérification des informations de connexion
					String sql = "SELECT * FROM usersApp WHERE username=? AND password=?";

					try (PreparedStatement statement = conn.prepareStatement(sql)) {
						statement.setString(1, username);
						statement.setString(2, password);
						ResultSet rs = statement.executeQuery();

						if (rs.next()) {
							session.setAttribute("username", username);
							session.setAttribute("firstName", rs.getString("first_name"));
							session.setAttribute("lastName", rs.getString("last_name"));
							session.setAttribute("email", rs.getString("email"));
							session.setAttribute("city", rs.getString("city"));
							session.setAttribute("postalCode", rs.getString("postal_code"));

							response.sendRedirect("central.html?username=" + username);
						} else {
							response.sendRedirect("login.html?error=invalidLogin");
						}
					} catch (SQLException e) {
						System.out.println("Echec");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Echec de la redirection de la réponse");
						e.printStackTrace();
					}
				}
			});


			// Démarrage du thread pour la vérification des informations de connexion
			verifyLoginThread.start();

			// Attente du thread pour la vérification des informations de connexion
			try {
				verifyLoginThread.join();
			} catch (InterruptedException e) {
				System.out.println("Thread is not working");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Echec");
			e.printStackTrace();
		}
	}
}
