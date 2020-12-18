<%--
  Created by IntelliJ IDEA.
  User: ciost
  Date: 24/11/2020
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>

 


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

String result;
PostgresDbConnection dbConnection = new PostgresDbConnection();
PlannedActivity plannedActivity = new PlannedActivity();

try{
	result = plannedActivity.getPlannedActivities(dbConnection);
} catch(Exception ex){
	result = "[{}]";
}
response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
