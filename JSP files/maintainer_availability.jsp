
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 
String jsonResult;
int id_activity = Integer.valueOf(request.getParameter("id"));
int week = Integer.valueOf(request.getParameter("week"));
PostgresDbConnection dbConnection = new PostgresDbConnection();
DatabaseView dbToJson = new DatabaseView();

response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= dbToJson.getMaintainerNameWeek(week, id_activity, dbConnection)%>
