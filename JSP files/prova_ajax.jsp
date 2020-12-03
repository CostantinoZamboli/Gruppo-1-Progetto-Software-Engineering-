
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

String result;
int id_workspace_notes = Integer.valueOf(request.getParameter("id"));
String description = request.getParameter("description");

PostgresDbConnection dbConnection = new PostgresDbConnection();
DatabaseView dbToJson = new DatabaseView();
dbToJson.updateWorkspaceNotes(id_workspace_notes, description, dbConnection);
result = String.valueOf(id_workspace_notes) + description;

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result%>
