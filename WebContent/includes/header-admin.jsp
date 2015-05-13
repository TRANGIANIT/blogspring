<%@page import="models.POJO.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="//includes/functions.jsp" %>
<%
	if(!isAdmin(session)){
		response.sendError(404);
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv= "content-type" content= "text/html" charset="utf-8" />
	<title>Khanh Spring</title>
	<link href="<%= request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="<%= request.getContextPath()%>/css/admin-style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-2.0.2.min.js"></script>
</head>
<body>
<%
	User u = (User) session.getAttribute("user");
%>
<div id="topBarWrap">
<div id="topBar">
	
	<div id="pageLogo">
		<a href="<%= request.getContextPath()%>">KHANHSPRING</a>
	</div><!--end #pageLogo-->
	<div id="userBar">
		<ul>
			<li><a id="user" href="#"><img id="userAvatar" src="<%= request.getContextPath()%>/uploads/avatars/<%=u.getAvatar() %>" alt="avatar"/><span id="userName"><%=u.getLastName()+" "+u.getFirstName() %></span></a></li>
			<li>
				<a id="option" href="#">option</a>
				<ul id="subUserMenu">
					<li><a href="<%= request.getContextPath()%>/logout">Đăng xuất</a></li>
					<li><a href="#">Thông tin cá nhân</a></li>
				</ul>
			</li>
		</ul>
	</div><!--end #userBar-->
</div><!--end #topBarWrap-->
</div><!--end #topBar-->
<%
	String uri = request.getRequestURI();
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
%>
<div id="containerWrap">
	<div id="pageMenuWrap">
		<div id="mainMenu">
			<ul>
				<li><a id="generalMenu" href="<%= request.getContextPath()%>/admin/dashboard">TỔNG QUÁT</a></li>
				<li><a class="parentMainMenu" id="postMenu" href="#">POST</a>
					<ul class="subMainMenu <%if(pageName.lastIndexOf("post")!=-1 || pageName.lastIndexOf("categories")!=-1){out.print("openned");}%>">
						<li><a href="<%= request.getContextPath()%>/admin/view-all-posts"><span class="numOrder">1.</span>Tất cả</a></li>
						<li><a href="<%= request.getContextPath()%>/admin/add-new-post"><span class="numOrder">2.</span>Thêm mới</a></li>
						<li><a href="<%= request.getContextPath()%>/admin/categories"><span class="numOrder">3.</span>Thể loại</a></li>
					</ul>
				</li>
				<li><a class="parentMainMenu" id="pageMenu" href="#">PAGE</a>
					<ul class="subMainMenu <%if(pageName.lastIndexOf("page")!=-1){out.print("openned");}%>">
						<li><a href="#"><span class="numOrder">1.</span>Tất cả</a></li>
						<li><a href="#"><span class="numOrder">2.</span>Thêm mới</a></li>
					</ul>
				</li>
				<li><a class="parentMainMenu" id="mediaMenu" href="#">MEDIA</a>
					<ul class="subMainMenu <%if(pageName.lastIndexOf("image")!=-1){out.print("openned");}%>">
						<li><a href="#"><span class="numOrder">1.</span>Thư viện</a></li>
						<li><a href="#"><span class="numOrder">2.</span>Thêm mới</a></li>
					</ul>
				</li>
				<li><a id="commentMenu" href="#">COMMENT</a></li>
				<li><a class="parentMainMenu"  id="userMenu" href="#">USER</a>
					<ul class="subMainMenu <%if(pageName.lastIndexOf("user")!=-1){out.print("openned");}%>">
						<li><a href="#"><span class="numOrder">1.</span>Tất cả</a></li>
						<li><a href="#"><span class="numOrder">2.</span>Thêm mới</a></li>
						<li><a href="#"><span class="numOrder">3.</span>Trang cá nhân</a></li>
					</ul>
				</li>
				<li><a id="sittingMenu" href="<%= request.getContextPath()%>/admin/setting">CÀI ĐẶT</a></li>
			</ul>
		</div><!--end #mainMenu-->
	</div><!--end #pageMenuWrap-->
	<div id="contentWrap">