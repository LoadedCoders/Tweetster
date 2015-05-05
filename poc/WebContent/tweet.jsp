<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	String message = (String)request.getAttribute("message");
	out.println(message);
	
	List<String> tables = (List)request.getAttribute("tables");
	for(String table : tables) {
		out.println("<br>"+table);
	}
	
	out.println("<h1>"+request.getAttribute("count")+"</h1>");
%>
</body>
</html>