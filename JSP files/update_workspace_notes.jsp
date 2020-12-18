
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>

<% 

String result;
int id_workspace_notes = Integer.valueOf(request.getParameter("id"));
String description = request.getParameter("description");

PostgresDbConnection dbConnection = new PostgresDbConnection();
PlannedActivity plannedActivity = new PlannedActivity();


try{
	if(plannedActivity.updateWorkspaceNotes(id_workspace_notes, description, dbConnection)){
		result = "Workspace Notes update succesfully executed";
	}
	else{
		result = "Workspace Notes update error";
	}
} catch (Exception e){
	result = "Workspace Notes update error";
}

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
