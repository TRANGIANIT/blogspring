<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
<link href="<%= request.getContextPath()%>/css/reset.css" rel='stylesheet' type='text/css' />
<link href='<%= request.getContextPath()%>/css/style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-2.0.2.min.js"></script>
</head>
<body>
<%
	int statusCode = (Integer)request.getAttribute("statusCode");
%>
<div id="errorPageWrap">
<span id="errorTtl">ERROR <%=statusCode%></span>
<span id="goBack">Go back</span>
<a id="backHomeLink" href="<%=request.getContextPath() %>">Go to home page</a>
</div>
<script>
	$(document).ready(function(){
		$("#goBack").click(function(){
			window.history.back();
		});
	});
</script>
</body>
</html>