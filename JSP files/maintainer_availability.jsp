
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 
String result;
int id_activity = Integer.valueOf(request.getParameter("id"));
int week = Integer.valueOf(request.getParameter("week"));
PostgresDbConnection dbConnection = new PostgresDbConnection();
PlannedActivity plannedActivity = new PlannedActivity();

try {
	result = plannedActivity.getMaintainerAvailability(week, id_activity, dbConnection);
} catch(Exception e){
	result = "[{}]";
}
response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
