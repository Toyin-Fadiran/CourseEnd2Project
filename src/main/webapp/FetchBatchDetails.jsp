<%@ page import="com.example.ZumbaRegistry.model.Batch"%>
<%@ page import="com.example.ZumbaRegistry.db.Database"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<%
    // Retrieve batch details based on the ID and batchName
    String batchIdParam = request.getParameter("batchId");
    String batchNameParam = request.getParameter("batchName");

    Batch existingBatch = new Batch();
    Database db = Database.getInstance();

    // TODO: Modify your SQL query to handle both "bid" and "batchName"
    String selectBatchById = "SELECT * FROM batch WHERE bid = ? AND batchName = ?";

    if (batchIdParam != null && !batchIdParam.isEmpty() && batchNameParam != null && !batchNameParam.isEmpty()) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectBatchById)) {

            // Set the batch ID and batchName parameters
            ps.setString(1, batchIdParam);
            ps.setString(2, batchNameParam);

            try (ResultSet resultSet = db.executeQuery(ps)) {
                if (resultSet != null && resultSet.next()) {
                    // Set the batch details in the Batch object
                    existingBatch.setBid(resultSet.getInt("bid"));
                    existingBatch.setBatchName(resultSet.getString("batchName"));
                    existingBatch.setBatchInstructor(resultSet.getString("batchInstructor"));
                    existingBatch.setCapacity( resultSet.getInt("capacity"));
                    existingBatch.setBatchDuration( resultSet.getInt("batchDuration"));
                    // Add more fields as needed
                } else {
                    // Handle the case where no rows were found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display an error message if the batch ID does not exist
    if (existingBatch.getBid() == 0 && request.getMethod().equalsIgnoreCase("post")) {
        request.setAttribute("errorMessage", "Batch with the entered ID does not exist.");
    }
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Update Batch</title>
<style>
.center {
	margin: auto;
	width: 60%;
	border: 3px solid #2222FF;
	padding: 10px;
}
</style>
</head>

<body>
	<div class="center">
		<h3>Update Batch</h3>

		<%-- Display error message if it exists --%>
		<% if (request.getAttribute("errorMessage") != null) { %>
		<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
		<% } else { %>
		<p>
			<strong>Batch Details:</strong>
		</p>
		<p>
			<strong>Batch ID:</strong>
			<%= existingBatch.getBid() %></p>
		<p>
			<strong>Batch Name:</strong>
			<%= existingBatch.getBatchName() %></p>
		<p>
			<strong>Instructor:</strong>
			<%= existingBatch.getBatchInstructor() %></p>
		<p>
			<strong>Capacity:</strong>
			<%= existingBatch.getCapacity() %></p>
		<p>
			<strong>Batch Duration:</strong>
			<%= existingBatch.getBatchDuration() %></p>
		<!-- Add more fields as needed -->
		<% } %>

		<form action="UpdateBatch" method="post">
			<label for="batchId">Batch ID: </label><br> <input type="text"
				id="batchId" name="batchId" placeholder="Enter Batch ID"
				value="<%= (existingBatch != null) ? String.valueOf(existingBatch.getBid()) : "" %>"><br>
			<br>

			<!-- Add batchName input field -->
			<label for="batchName">Batch Name: </label><br> <input
				type="text" id="batchName" name="batchName"
				placeholder="Enter Batch Name"
				value="<%= (existingBatch != null) ? existingBatch.getBatchName() : "" %>"><br>
			<br>

			<!-- Display existing batch details for modification -->
			<label for="instructor">Instructor: </label><br> <input
				type="text" id="instructor" name="instructor"
				placeholder="Enter Instructor"
				value="<%= (existingBatch != null) ? existingBatch.getBatchInstructor() : "" %>"><br>
			<br>
			<!-- Add more fields as needed -->
				<label for="batchDuration">Batch Duration: </label><br> <input
				type="text" id="batchDuration" name="batchDuration"
				placeholder="Enter batch Duration"
				value="<%= (existingBatch != null) ? String.valueOf(existingBatch.getBatchDuration()) : "" %>"><br>
			<br>
			<!-- Display existing batch details for modification -->
			<label for="capacity">Capacity: </label><br> <input
				type="text" id="capacity" name="capacity"
				placeholder="Enter Capacity"
				value="<%= (existingBatch != null) ? existingBatch.getCapacity() : "" %>"><br>
			<br> <input type="submit" value="Update Batch">
		</form>
		</div>
		<br>
</body>

</html>
