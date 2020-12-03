
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.*" %>

<%
int id_maintainer = Integer.valueOf(request.getParameter("id_maintainer"));
int id_activity = Integer.valueOf(request.getParameter("activity_id"));
int week = Integer.valueOf(request.getParameter("week"));
String day = request.getParameter("day");
String time_slot = request.getParameter("time");
String name_maintainer = request.getParameter("name");
String timestamp = request.getParameter("date");

Email email = new Email(id_maintainer, name_maintainer, id_activity, day, time_slot, week, timestamp);
String result = email.sendEmail();

response.setContentType("application/text");
response.setHeader("Access-Control-Allow-Origin", "*");

%>
<%= result%>
