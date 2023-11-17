package com.example.ZumbaRegistry.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.ZumbaRegistry.db.Database;
import com.example.ZumbaRegistry.model.Batch;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BatchControllerServlet
 */
public class BatchControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BatchControllerServlet() {
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
		// TODO Auto-generated method stub
		String servletPath = request.getServletPath();
		System.out.println("Servlet Path: " + servletPath);

		Batch newBatch = new Batch();
		newBatch.setBid(Integer.parseInt(request.getParameter("bid")));
		newBatch.setBatchName(request.getParameter("batchName"));
		newBatch.setBatchInstructor(request.getParameter("batchInstructor"));
		newBatch.setCapacity(50);
		newBatch.setBatchDuration(Integer.parseInt(request.getParameter("batchDuration")));

		// Assuming you have a Batch object with batchid, batchName, and other
		// properties
		Database db = Database.getInstance();

		boolean combinationDoesExist = false;

		String checkIFBatchName_ID_unique = "SELECT COUNT(*) AS count FROM Batch WHERE bid = ? AND batchName = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(checkIFBatchName_ID_unique)) {

			ps.setInt(1, newBatch.getBid());
			ps.setString(2, newBatch.getBatchName());

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");
					//turns boolean to true
					combinationDoesExist = count > 0;
				}

			}

			// Check if the combination already exists in the database
			// Remember, if (!exists) is equivalent to saying "if exists is not true" or "if
			// the combination does not exist in the database."

			if (!combinationDoesExist) {
				// Execute the SQL insert statement

				// SQL query to insert participant data into the database
				String insertBatchSql = "INSERT INTO batch (bid, batchName, batchInstructor, capacity, batchDuration) VALUES (?, ?, ?, ?, ?)";

				try (PreparedStatement ps2 = connection.prepareStatement(insertBatchSql)) {

					// Set parameters for the participant insertion
					ps2.setInt(1, newBatch.getBid());
					ps2.setString(2, newBatch.getBatchName());
					ps2.setString(3, newBatch.getBatchInstructor());
					ps2.setInt(4, newBatch.getCapacity());
					ps2.setInt(5, newBatch.getBatchDuration());

					// Execute the update
					int result = db.executeUpdate(ps2);

					if (result > 0) {
						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "NewBatch added successfully!");
						request.setAttribute("BatchName", newBatch.getBatchName());
						request.setAttribute("BatchInstructor", newBatch.getBatchInstructor());
						request.setAttribute("Capacity", newBatch.getCapacity());
						request.setAttribute("BatchDuration", newBatch.getBatchDuration());

						// Forward the request to the JSP for rendering the view
						RequestDispatcher dispatcher = request.getRequestDispatcher("/add-batch.jsp");
						dispatcher.forward(request, response);
					} else {
						// Handle the case where no rows were found
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				// Handle the case where the combination already exists
				System.out.println("Batch with the same batchid and batchName already exists.");
			}

			// Implement the batchExists and insertBatch methods accordingly

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
