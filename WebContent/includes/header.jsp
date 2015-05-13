<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="//includes/functions.jsp" %>
<% pageCounter(application, request); %>
<% appCounter(application); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Khánh Spring</title>
<link href="<%= request.getContextPath()%>/css/reset.css" rel='stylesheet' type='text/css' />
<link href='<%= request.getContextPath()%>/css/style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-2.0.2.min.js"></script>
</head>
<body>
	<jsp:include page="//includes/navigator.jsp" flush="true" />
	<div id="banner">
	<%if(session.getAttribute("user")==null){ %>
		<div id="btnLogin">
			<a href="<%=request.getContextPath()%>/login">LOGIN <span>NOW!</span></a>
		</div><!--end #btnLogin-->
	<%} %>
	</div>
	<!--end #banner-->
	<!---->
	<div id="searchBoxWrap">
		<div id="searchBox">
			<form action="<%=request.getContextPath()%>/search" method="get">
				<input id="txtSearch" type="search" value="" name="q"
					placeholder="Nhập từ khóa và Enter để tìm kiếm" size="100"/>
			</form>
		</div>
		<!--end #searchBox-->
	</div>
	<!--end #searchBoxWrap-->
	<div id="btnToggleSearchBoxWrap">
		<div id="btnToggleSearchBox"><span>Tìm kiếm</span></div>
	</div>
	<!---->
	<div id="containerWrap">
	<!---->
	<!-- JavaScript -->
	<script>
		$(document).ready(function(){
		//open or close searchBox
			$("#btnToggleSearchBox").click(function(){
				$("#txtSearch").animate({width:"300px"}, 200).hide(0, function(){
					$("#searchBoxWrap").slideToggle(200, function(){
						$("#txtSearch").show(0).animate({width:"1000px"}, 100).focus();
					});
				});
			});
		});
	</script>