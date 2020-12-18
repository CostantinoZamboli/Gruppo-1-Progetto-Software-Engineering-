
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>

<%

String jsonInfo = request.getParameter("data");
int idActivity = Integer.valueOf(request.getParameter("activity_id"));

String username = ""; // inserire username dell'email del mittente
String password = ""; // inserire password dell'email del mittente

FacadeEmail facade = new FacadeEmail(username, password, idActivity, jsonInfo);
String result = facade.sendEmailEwo();

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result %>
