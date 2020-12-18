
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 
String jsonResult;
int id_activity = Integer.valueOf(request.getParameter("id"));
int week = Integer.valueOf(request.getParameter("week"));
String giorno = request.getParameter("day");

PostgresDbConnection dbConnection = new PostgresDbConnection();
EwoActivity ewo = new EwoActivity();

String result = "";

try {
	result = ewo.getTimeSlotAvailabilityEwo(dbConnection, id_activity, week, giorno);
} catch(Exception e){
	result = "[{}]";
}

response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
