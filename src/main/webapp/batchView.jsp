<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.example.ZumbaRegistry.model.Batch"%>
<html>
<head>
    <title>Batch View</title>
</head>
<body>

    <h2>Batch View</h2>

    <%-- Display a success message if available --%>
    <% if (request.getAttribute("successMessage") != null) { %>
        <p style="color: green;"><%= request.getAttribute("successMessage") %></p>
    <% } %>

    <%-- Retrieve batch details from the request attribute --%>
    <%
        // Retrieve batch details from request attributes
        int batchId = (int) request.getAttribute("batchId");
        String batchName = (String) request.getAttribute("batchName");
        String instructor = (String) request.getAttribute("instructor");
        int capacity = (int) request.getAttribute("capacity");
        int batchDuration = (int) request.getAttribute("batchDuration");
        // Add more batch-related fields as needed

        // Create Batch object
      Batch batch =  new Batch( batchId,  batchName,  instructor,  capacity,  batchDuration);
    %>

    <%-- Display batch details --%>
    <p><strong>Batch ID:</strong> <%= batch.getBid() %></p>
    <p><strong>Batch Name:</strong> <%= batch.getBatchName() %></p>
    <p><strong>Instructor:</strong> <%= batch.getBatchInstructor() %></p>
    <p><strong>Capacity:</strong> <%= batch.getCapacity() %></p>
    <p><strong>Batch Duration:</strong> <%= batch.getBatchDuration() %></p>
    <!-- Add more fields as needed -->

    <br>
    <a href="index.html">Go back to index</a>

</body>
</html>
