package com.example.ZumbaRegistry.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.ZumbaRegistry.db.Database;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParticipantUpdateServlet
 */
public class ParticipantUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParticipantUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve participant details from the form
		Integer participantId = Integer.parseInt(request.getParameter("participantId"));
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String batchName = request.getParameter("batchname");
		Integer bid =  Integer.parseInt(request.getParameter("bid"));

		boolean pidExists = false;

		Database db = Database.getInstance();
		String checkIF_PID_unique = "SELECT COUNT(*) AS count FROM participant WHERE pid = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(checkIF_PID_unique)) {

			ps.setString(1, String.valueOf(participantId));

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");
					// turns boolean to true
					pidExists = count > 0;
				}
			}

			// Continue with the rest of your logic here
			if (pidExists) {
				// Handle the case where the pid already exists

				// SQL query to update participant data into the database
				String updateSql = "UPDATE participant SET name = ?, phone = ?, email = ?, bid = ?, batchName = ? WHERE pid = ?";

				try (PreparedStatement ps2 = connection.prepareStatement(updateSql)) {

					// Set parameters for the participant insertion
					ps2.setString(1, name);
					ps2.setString(2, phone);
					ps2.setString(3, email);
					ps2.setInt(4, bid);
					ps2.setString(5, batchName);
					ps2.setInt(6, participantId);
					
					// Execute the update
					int result = db.executeUpdate(ps2);

					if (result > 0) {
						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "Participant updated successfully!");
						request.setAttribute("pid", participantId);
						request.setAttribute("name", name);
						request.setAttribute("phone", phone);
						request.setAttribute("email", email);
						request.setAttribute("bid", bid);
						request.setAttribute("batchName", batchName);

						// Forward the request to the JSP for rendering the view
						RequestDispatcher dispatcher = request.getRequestDispatcher("/participantView.jsp");
						dispatcher.forward(request, response);
					} else {
						// Handle the case where no rows were found
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("this PID does not exist, please navigate to AddParticipant module");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
