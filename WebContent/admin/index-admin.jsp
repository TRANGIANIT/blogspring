<%@page import="libraries.Utilities"%>
<%@page import="libraries.PostLibrary"%>
<%@page import="libraries.CommonLibrary"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if(!isAdmin(session)){
		response.sendError(404);
	}else{
		if(request.getAttribute("numUsers")==null){
			response.sendRedirect("dashboard");
		}else{
			int numUsers = (int) request.getAttribute("numUsers");
			int numPages = (int) request.getAttribute("numPages");
			int numComments = (int) request.getAttribute("numComments");
			int numCategories = (int) request.getAttribute("numCategories");
			int numPosts = (int) request.getAttribute("numPosts");
			
			int appCounter = (int) getAppCounter(application);
			int indexCounter = (int) getPageCounter("index", application);
			int singleCounter = (int) getPageCounter("single", application);
			int loginCounter = (int) getPageCounter("login", application);
			int cateCounter = (int) getPageCounter("cate", application);
			int searchCounter = (int) getPageCounter("result", application);
			
			float app =200.00f;
			float index =200.00f;
			float single =200.00f;
			float login =200.00f;
			float cate =200.00f;
			float search =200.00f;
			if(appCounter > 0){
				app = 350f;
				index = ((float)indexCounter/(float) appCounter)*app;
				single = ((float)singleCounter/(float)appCounter)*app;
				login = ((float)loginCounter/(float)appCounter)*app;
				cate = ((float)cateCounter/(float)appCounter)*app;
				search = ((float)searchCounter/(float)appCounter)*app;
			}
			
			ArrayList<Post> popPostsComment = (ArrayList<Post>) request.getAttribute("popPostsComment");
			ArrayList<Post> popPostsView = (ArrayList<Post>) request.getAttribute("popPostsView");
%>

<%@include file="//includes/header-admin.jsp"%>
<div id="dashboard">
				<h2 class="welcomeTtl">Chào mừng bạn đến với trang quản trị của KHANHSPRING</h2>

				<div id="glanceDiv">
					<h3 class="subTtlDashboard">Tổng quan về trang Web</h3>
					<ul>
						<li><a href="#"><%=numPosts %> Bài Viết</a></li>
						<li><a href="#"><%=numPages %> Trang</a></li>
						<li><a href="#"><%=numComments %> Lượt Bình Luần</a></li>
						<li><a href="#"><%=numCategories %> Thể Loại</a></li>
						<li><a href="#"><%=numUsers %> Người Dùng</a></li>
					</ul>
				</div><!--end #glanceDiv-->

				<div id="statisticsDiv1">
					<h3 class="subTtlDashboard">Thống kê truy cập</h3>
					<ul>
						<li><span class="onPage">Tổng số</span><span class="num" id="numTotal" style="width:<%=app%>px;"><%=appCounter %> (Lượt)</span></li>
						<li><span class="onPage">Trang chủ</span><span class="num" id="numIndex" style="width:<%=index%>px;"><%=indexCounter %></span></li>
						<li><span class="onPage">Trang con</span><span class="num" id="numSingle" style="width:<%=single%>px;"><%=singleCounter %></span></li>
						<li><span class="onPage">Đăng nhập</span><span class="num" id="numLogin" style="width:<%=login%>px;"><%=loginCounter %></span></li>
						<li><span class="onPage">Tìm kiếm</span><span class="num" id="numSearch" style="width:<%=search%>px;"><%=searchCounter %></span></li>
					</ul>
				</div><!--end #glanceDiv-->

				<div id="statisticsDiv2">
					<h3 class="subTtlDashboard">Bài viết nổi bật</h3>
					<div id="popPostsView">
						<span class="subTtlStatistics">Những bài viết đọc nhều</span>
						<%
							if(popPostsView!=null){
								for(int i=0; i<popPostsView.size(); i++){
									out.println("<p><span class='no'>"+(i+1)+"</span><a href='");
									out.println(request.getContextPath()+"/"+popPostsView.get(i).getCatePostStr()+"/"+popPostsView.get(i).getPostSlug());
									out.println("'>");
									out.println(Utilities.theContent(popPostsView.get(i).getPostName(), 9)+" ...");
									out.println("</a></p>");
								}
							}
						%>
					</div>
					<div id="popPostsComment">
						<span class="subTtlStatistics">Những bài viết có nhiều bình luận</span>
						<%
							if(popPostsComment!=null){
								for(int i=0; i<popPostsComment.size(); i++){
									out.println("<p><span class='no'>"+(i+1)+"</span><a href='");
									out.println(request.getContextPath()+"/"+popPostsComment.get(i).getCatePostStr()+"/"+popPostsComment.get(i).getPostSlug());
									out.println("'>");
									out.println(Utilities.theContent(popPostsComment.get(i).getPostName(),9)+" ...");
									out.println("</a></p>");
								}
							}
						%>
					</div>
				</div><!--end #statisticsDiv-->

		</div><!--end #dashboard-->
<%@include file="//includes/footer-admin.jsp"%>
<%}}%>