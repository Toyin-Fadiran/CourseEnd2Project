<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Participant View</title>
</head>
<body>

	<h2>Participant View</h2>

	<%-- Display a success message if available --%>
	<% if (request.getAttribute("successMessage") != null) { %>
	<p style="color: green;"><%= request.getAttribute("successMessage") %></p>
	<% } %>

	<%-- Your HTML content here, displaying participant data, etc. --%>
	<p><strong>BatchName:</strong> <%= request.getAttribute("BatchName") %></p>
	<p><strong>BatchInstructor:</strong> <%= request.getAttribute("BatchInstructor") %></p>
	<p><strong>Capacity:</strong> <%= request.getAttribute("Capacity") %></p>
	<p><strong>BatchDuration:</strong> <%= request.getAttribute("BatchDuration") %></p>
	
	<br>
	<a href="index.html">Go back to index</a>

</body>
</html>
	
	
			
				