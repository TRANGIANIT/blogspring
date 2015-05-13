<%@page import="libraries.CategoryLibrary"%>
<%@page import="models.POJO.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="//includes/functions.jsp" %>
<%
	ArrayList<Category> cate = (ArrayList<Category>) request.getAttribute("categories");
%>
<div id="topBarWrap">
	<%
		if(isLogin(session)){
			User u = (User) session.getAttribute("user");
	%>
	<div id="userBarWrap">
		<div id="userBar">
			<%if(isAdmin(session)){ %>
				<div id="btnAdPanel">
					<a href="<%= request.getContextPath()%>/admin/index-admin.jsp">ADMIN PANEL</a>
				</div>
			<%} %>
			<div  id="userMenu">
				<ul>
					<li><a id="userName" href="#"><%=u.getLastName()+" "+u.getFirstName()%></a>
						<ul>
							<li><a id="btnLogout" href="<%= request.getContextPath()%>/logout">Logout</a></li>
							<li><a id="btnUserInfo" href="#">Thông tin cá nhân</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<%} %>
		<div id="topBar">
			<div id="status" <%if(session.getAttribute("user")!=null){out.println("style='background-color: #0F9D28'");} %>><span>status</span></div>
			<div id="logo">
				<h1>
					<a href="<%=request.getContextPath() %>"><%if(request.getAttribute("blogName")!=null){
						out.print((String) request.getAttribute("blogName"));
					}%></a>
				</h1>
			</div>
			<!--end #logo-->
			<%
				if(cate!=null && cate.size()>0){
			%>
			<div id="mainNav">
				<%=CategoryLibrary.printerCate(cate, request.getContextPath()) %>
			</div>
			<!--end #mainNav-->
			<%} %>
		</div>
		<!--end #topBar-->
	</div>
	<!--end #topBarWrap-->

	<!---->