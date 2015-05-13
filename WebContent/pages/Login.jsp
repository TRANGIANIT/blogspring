<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="//includes/functions.jsp" %>
<% pageCounter(application, request); %>
<% appCounter(application); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iWeb - Login</title>
<link href='<%= request.getContextPath()%>/css/reset.css' rel='stylesheet' type='text/css' />
<link href='<%= request.getContextPath()%>/css/login-style.css' rel='stylesheet' type='text/css' />
</head>
<body>
	<%
		String error = (String) request.getAttribute("error");
		String email = (String) request.getAttribute("email");
		String pass = (String) request.getAttribute("passWord");
		String p = (String) request.getAttribute("p");
	%>
	
	<div id="loginFormWrap">
	<div id="logo">
		<h1><a href="<%=request.getContextPath() %>">Khanh Spring</a></h1>
	</div>
	<div id="headLoginForm">
		<h2 id="ttlLoginForm">ĐĂNG NHẬP</h2>
		<%
			if (error != null) {
				out.println("<span id='err'>"+error+"</span>");
			}
		%>
	</div>
	<div id="loginForm">
	<%
		String from="";
		if(request.getHeader("referer")!=null){
			from = request.getHeader("referer");
		}
		System.out.print("rf:"+from);
	%>
		<form method="post" action="authen">
			<label for="txtEmail">Email</label><%
						if (email == null && p!=null) {
							out.println("<span id='message'>Bạn chưa điền email</span>");
						}
					%>
			<p><input type="email" name="txtEmail" id="txtEmail" required value="<%
						if (email != null) {
							out.println(email);
						}
				%>" /></p>
			<label for="txtPass">Mật khẩu</label><%
						if (pass == null && p!=null) {
							out.println("<span id='message'>Bạn chưa điền mật khẩu</span>");
						}
					%>
			<p><input type="password" name="txtPassWord" id="txtPass" required value="" /></p>
			<input type="hidden" name="from" value="${param.from}">
			<p><input type="submit" name="btnLogin" id="btnLogin" value="Đăng nhập" /></p>
		</form>
		<a id="forgotPass" href="#">Quên mật khẩu?</a>
	</div>
	<div id="endLoginForm">
		<a id="registerLink" href="#">Đăng ký</a>
		<a id="goHomeLink" href="<%=request.getContextPath() %>">Về trang chủ</a>
	</div>
</div>
</body>
</html>