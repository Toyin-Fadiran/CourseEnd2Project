<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.example.ZumbaRegistry.model.Participant"%>
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

    <%-- Retrieve participant details from the request attribute --%>
  <%
    // Retrieve participant details from request attributes
    int pid = (int) request.getAttribute("pid");
    String name = (String) request.getAttribute("name");
    String phone = (String) request.getAttribute("phone");
    String email = (String) request.getAttribute("email");
    int bid = (int) request.getAttribute("bid");
    String batchName = (String) request.getAttribute("batchName");

    // Create Participant object
    Participant participant = new Participant(pid,  name,  phone,  email,  bid,  batchName);
%>


    <%-- Display participant details --%>
    <p><strong>Participant ID:</strong> <%= participant.getPid() %></p>
    <p><strong>Name:</strong> <%= participant.getName() %></p>
    <p><strong>Phone:</strong> <%= participant.getPhone() %></p>
    <p><strong>Email:</strong> <%= participant.getEmail() %></p>
    <p><strong>Batch ID:</strong> <%= participant.getBid() %></p>
    <p><strong>Batch Name:</strong> <%= participant.getBatchName() %></p>

    <br>
    <a href="index.html">Go back to index</a>

</body>
</html>
