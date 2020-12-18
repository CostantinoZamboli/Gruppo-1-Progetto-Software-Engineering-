
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

int id_activity = Integer.valueOf(request.getParameter("id_activity"));
int time = Integer.valueOf(request.getParameter("time"));
String intervention_description = request.getParameter("intervention_description");


PostgresDbConnection dbConnection = new PostgresDbConnection();
EwoActivity ewo = new EwoActivity();

String result = "";

try {
	if(ewo.insertEwoActivity(dbConnection, id_activity, intervention_description, time)){
		result = "Insert completed";
	}
	else {
		result = "Insert failed";
	}
} catch(Exception e){
	result = "Insert failed";
}

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
