
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<% 

int id_activity = Integer.valueOf(request.getParameter("id_activity"));
String jsonData= request.getParameter("data");

List skillList = new ArrayList();
JSONArray jsonArray = new JSONArray(jsonData);

for(int i=0; i<jsonArray.length(); i++){

	JSONObject jsonObject = jsonArray.getJSONObject(i);
	int id_skill = Integer.valueOf(jsonObject.getString("value"));
	skillList.add(id_skill);
}

PostgresDbConnection dbConnection = new PostgresDbConnection();
EwoActivity ewo = new EwoActivity();

String result = "";

try{
	result = ewo.insertSkillsEwo(dbConnection, id_activity, skillList);
} catch(Exception e){
	result = "Insert failed";
}

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>