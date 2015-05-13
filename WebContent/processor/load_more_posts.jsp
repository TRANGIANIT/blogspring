<%@page import="java.text.SimpleDateFormat"%>
<%@page import="libraries.Utilities"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (request.getAttribute("hasNext") == null) {
		request.getRequestDispatcher("../LoadMorePostsWithAJAX")
				.forward(request, response);
	} else {
		boolean hasNext = (boolean) request.getAttribute("hasNext");
		if (hasNext) {
			ArrayList<Post> listPost = (ArrayList<Post>) request
					.getAttribute("listPost");
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd-MM-yyyy");
			for (int i = 0; i < listPost.size(); i++) {
%>
<div class="post">
	<div class="postIll">
		<a
			href="<%=request.getContextPath() + "/"
								+ listPost.get(i).getCatePostStr() + "/"
								+ listPost.get(i).getPostSlug()%>"><img
			src="<%=request.getContextPath()%>/uploads/thumbnails/<%if (listPost.get(i).getIllustration() != null) {%><%=listPost.get(i).getIllustration().trim()%>
			<%} else {%>ill_default.jpg<%}%>"
			alt="<%=listPost.get(i).getPostName()%>" /></a>
	</div>
	<!--end .postIll-->
	<div class="postDetail">
		<h2 class="ttlPost">
			<a
				href="<%=request.getContextPath()%>/<%=listPost.get(i).getCatePostStr()%>/<%=listPost.get(i).getPostSlug()%>"><%=listPost.get(i).getPostName()%></a>
		</h2>
		<div class="postInfo">
			<span>Đăng bởi <a href="#"><%=listPost.get(i).getUserName()%></a>
				- Ngày <%=dateFormat
								.format(listPost.get(i).getPostOn())%> - Thể
				loại <a
				href="<%=request.getContextPath() + "/category/"
								+ listPost.get(i).getCatePostStr()%>"><%=listPost.get(i).getCatName()%></a></span>
		</div>
		<!--end .postInfo-->
		<div class="postContent">
			<p>
				<%=Utilities.theContent(listPost.get(i)
								.getContent(), 50)%>[...]
			</p>
		</div>
		<!--postContent-->
		<div class="readMore">
			<a class="btnReadMore"
				href="<%=request.getContextPath()%>/<%=listPost.get(i).getCatePostStr()%>/<%=listPost.get(i).getPostSlug()%>">Đọc
				thêm</a>
		</div>
		<!--end .readMore-->
	</div>
	<!--end .postDetail-->
</div>
<!--end .post-->
<%
	}
		} else {
			%>noMore<%
		}
	}
%>
