
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

String jsonResult;
PostgresDbConnection dbConnection = new PostgresDbConnection();
EwoActivity ewo = new EwoActivity();

String result = "";

try {
	result = ewo.getSkillToJson(dbConnection);
} catch(Exception e){
	result = "[{}]";
}

response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
