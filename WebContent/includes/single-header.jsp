<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="//includes/functions.jsp" %>
<% pageCounter(application, request); %>
<% appCounter(application); %>
<%
	String title ="";
	if(request.getAttribute("title")!=null){
		title = " | "+(String) request.getAttribute("title");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Khánh Spring<%=title %></title>
<link href="<%= request.getContextPath()%>/css/reset.css" rel='stylesheet' type='text/css' />
<link href='<%= request.getContextPath()%>/css/single-style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-2.0.2.min.js"></script>
</head>
<body>
<jsp:include page="//includes/navigator.jsp" flush="true" />
