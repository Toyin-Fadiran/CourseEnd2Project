package com.example.ZumbaRegistry.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.ZumbaRegistry.db.Database;
import com.example.ZumbaRegistry.model.Participant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParticipantControllerServlet
 */
public class ParticipantControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParticipantControllerServlet() {
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

	    // TODO: Extract common code to a separate method if needed

	    // Create a participant object and set its properties
	    Participant participant = new Participant();
	    participant.setName(request.getParameter("name"));
	    participant.setPhone(request.getParameter("phone"));
	    participant.setEmail(request.getParameter("email"));
	    participant.setBatchName(request.getParameter("batchname"));
	    
	  System.out.println( (request.getParameter("batchname")));

	    // Use the database singleton instance
	    Database db = Database.getInstance();

	    // SQL query to select batch ID by batch name
	    String selectIDbyBatchName = "SELECT bid FROM batch WHERE batchName = ?";
	    
	    

	    try (Connection connection = db.getConnection();
	         PreparedStatement ps = connection.prepareStatement(selectIDbyBatchName)) {

	        // Set the batch name parameter
	        ps.setString(1, participant.getBatchName());

	        try (ResultSet resultSet = db.executeQuery(ps)) {
	            if (resultSet.next()) {
	                // Set the batch ID in the participant object
	            System.out.println("sanity check: " +	resultSet.getInt("bid"));
	                participant.setBid(resultSet.getInt("bid"));
	            } else {
	                // Handle the case where no rows were found
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // SQL query to insert participant data into the database
	    String insertParticipantSql = "INSERT INTO Participant (name, phone, email, batchname, bid) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = db.getConnection();
	         PreparedStatement ps = connection.prepareStatement(insertParticipantSql)) {

	        // Set parameters for the participant insertion
	        ps.setString(1, participant.getName());
	        ps.setString(2, participant.getPhone());
	        ps.setString(3, participant.getEmail());
	        ps.setString(4, participant.getBatchName());
	        System.out.println("sanityChk2" + participant.getBid());
	        ps.setInt(5, participant.getBid());

	        // Execute the update
	        int result = db.executeUpdate(ps);

	        if (result > 0) {
	            // Set attributes for data that the JSP will use to generate the view
	            request.setAttribute("successMessage", "Participant added successfully!");
	            request.setAttribute("participantName", participant.getName());
	            request.setAttribute("participantPhone", participant.getPhone());
	            request.setAttribute("participantEmail", participant.getEmail());
	            request.setAttribute("participantBatchName", participant.getBatchName());
	            
	            // Forward the request to the JSP for rendering the view
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/add-participant.jsp");
	            dispatcher.forward(request, response);
	        } else {
	            // Handle the case where no rows were found
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
