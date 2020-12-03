
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 
String jsonResult;
int id_activity = Integer.valueOf(request.getParameter("id"));
int week = Integer.valueOf(request.getParameter("week"));
int id_maintainer = Integer.valueOf(request.getParameter("id_maintainer"));
String giorno = request.getParameter("day");

PostgresDbConnection dbConnection = new PostgresDbConnection();
DatabaseView dbToJson = new DatabaseView();

response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= dbToJson.timeSlotAvailability(dbConnection, id_activity, id_maintainer, week, giorno)%>
