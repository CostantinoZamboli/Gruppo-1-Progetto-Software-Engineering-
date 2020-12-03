
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

String jsonResult;
int id_activity = Integer.valueOf(request.getParameter("id"));
PostgresDbConnection dbConnection = new PostgresDbConnection();
DatabaseView dbToJson = new DatabaseView();

response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= dbToJson.getSkillByIdtoJson(id_activity, dbConnection)%>