<%@ page import="com.example.ZumbaRegistry.model.Participant"%>
<%@ page import="com.example.ZumbaRegistry.db.Database"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<%
    // Retrieve participant details based on the ID
    String participantIdParam = request.getParameter("participantId");

    Participant existingParticipant = new Participant();
    
    Database db = Database.getInstance();
    String selectIDbyBatchName = "SELECT * FROM participant WHERE pid = ?";
    
    if (participantIdParam != null && !participantIdParam.isEmpty()) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectIDbyBatchName)) {

            // Set the batch name parameter
            ps.setString(1, participantIdParam);

            try (ResultSet resultSet = db.executeQuery(ps)) {
                if (resultSet != null && resultSet.next()) {
                    // Set the batch ID in the participant object
                    existingParticipant.setPid(resultSet.getInt("pid"));
                    existingParticipant.setName(resultSet.getString("name"));
                    existingParticipant.setPhone(resultSet.getString("phone"));
                    existingParticipant.setEmail(resultSet.getString("email"));
                    existingParticipant.setBid(resultSet.getInt("bid"));
                    existingParticipant.setBatchName(resultSet.getString("batchName"));
                } else {
                    // Handle the case where no rows were found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display an error message if the participant ID does not exist
    if (existingParticipant.getPid() == 0 && request.getMethod().equalsIgnoreCase("post")) {
        request.setAttribute("errorMessage", "Participant with the entered ID does not exist.");
    }
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Update Participant</title>
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
		<h3>Update Participant</h3>

		<%-- Display error message if it exists --%>
		<% if (request.getAttribute("errorMessage") != null) { %>
		<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
		<% } %>

		<form action="UpdateParticipant" method="post">
			<label for="participantId">Participant ID: </label><br> <input
				type="text" id="participantId" name="participantId"
				placeholder="Enter Participant ID"
				value="<%= (existingParticipant != null) ? String.valueOf(existingParticipant.getPid()) : "" %>"><br>
			<br>

			<!-- Display existing participant details for modification -->
			<label for="name">Full Name: </label><br> <input type="text"
				id="name" name="name" placeholder="John Watson"
				value="<%= (existingParticipant != null) ? existingParticipant.getName() : "" %>"><br>

			<label for="phone">Phone: </label><br> <input type="text"
				id="phone" name="phone" placeholder="310-945-3758"
				value="<%= (existingParticipant != null) ? existingParticipant.getPhone() : "" %>"><br>
			<br> <label for="email">Email: </label><br> <input
				type="text" id="email" name="email" placeholder="john@example.com"
				value="<%= (existingParticipant != null) ? existingParticipant.getEmail() : "" %>"><br>
			<br> <label for="batchname">Batch Name: </label><br> <select
				id="batchname" name="batchname">
				<option value="AquaZumba">Aqua Zumba</option>
				<option value="ChairZumba">Chair Zumba</option>
				<option value="ToneZumba">Tone Zumba</option>
				<!-- Add more options as needed -->
			</select><br>
			<br> <label for="bid">Batch ID: </label><br> <input
				type="text" id="bid" name="bid" placeholder="Enter Batch ID"
				value="<%= (existingParticipant != null) ? String.valueOf(existingParticipant.getBid()) : "" %>"><br>
			<br> <input type="submit" value="Update Participant">
		</form>

	</div>
</body>

</html>
