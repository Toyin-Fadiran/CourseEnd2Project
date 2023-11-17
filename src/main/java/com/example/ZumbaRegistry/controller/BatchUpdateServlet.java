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
 * Servlet implementation class BatchUpdateServlet
 */
public class BatchUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // Retrieve batch details from the form
	    Integer batchId = Integer.parseInt(request.getParameter("batchId"));
	    String batchName = request.getParameter("batchName");
	    String instructor = request.getParameter("instructor");
	    Integer capacity = Integer.parseInt (request.getParameter("capacity"));
	    Integer batchDuration = Integer.parseInt (request.getParameter("batchDuration"));
	    // Add more batch-related fields as needed

	    boolean batchExists = false;

	    Database db = Database.getInstance();
	    String checkIfBatchExists = "SELECT COUNT(*) AS count FROM batch WHERE bid = ? AND batchName = ?";

	    try (Connection connection = db.getConnection();
	         PreparedStatement ps = connection.prepareStatement(checkIfBatchExists)) {

	        ps.setString(1, String.valueOf(batchId));
	        ps.setString(2, batchName);

	        try (ResultSet resultSet = db.executeQuery(ps)) {
	            if (resultSet != null && resultSet.next()) {
	                int count = resultSet.getInt("count");
	                // turns boolean to true
	                batchExists = count > 0;
	            }
	        }

	        // Continue with the rest of your logic here
	        if (batchExists) {
	            // Handle the case where the batch already exists

	            // SQL query to update batch data into the database
	        	String updateSql = "UPDATE batch SET batchInstructor = ?, capacity = ?, batchDuration = ? WHERE bid = ? AND batchName = ?";


	            try (PreparedStatement ps2 = connection.prepareStatement(updateSql)) {

	                // Set parameters for the batch update
	               // ps2.setString(1, batchName);
	                ps2.setString(1, instructor);
	                ps2.setInt(2, capacity);
	                ps2.setInt(3, batchDuration);
	                // Set more parameters for additional batch-related fields

	                ps2.setInt(4, batchId);
	                ps2.setString(5, batchName);
	                // Execute the update
	                int result = db.executeUpdate(ps2);

	                if (result > 0) {
	                    // Set attributes for data that the JSP will use to generate the view
	                    request.setAttribute("successMessage", "Batch updated successfully!");
	                    request.setAttribute("batchId", batchId);
	                    request.setAttribute("batchName", batchName);
	                    request.setAttribute("instructor", instructor);
	                    request.setAttribute("capacity", capacity);
	                    request.setAttribute("batchDuration", batchDuration);
	                    // Set more attributes for additional batch-related fields

	                    // Forward the request to the JSP for rendering the view
	                    RequestDispatcher dispatcher = request.getRequestDispatcher("/batchView.jsp");
	                    dispatcher.forward(request, response);
	                } else {
	                    // Handle the case where no rows were found
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        } else {
	            System.out.println("This batch does not exist, please navigate to AddBatch module");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
