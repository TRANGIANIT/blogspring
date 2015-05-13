<%@page import="libraries.Utilities"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if(request.getAttribute("listPost")==null){
	response.sendRedirect("");
}else{ %>
<%@include file="//includes/header.jsp"%>
<%
	String loadOption = request.getAttribute("loadOption").toString().trim();
	ArrayList<Post> listPost = (ArrayList<Post>) request.getAttribute("listPost");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
%>

	<div id="container">
		<div id="content">
			<!---->
			<div id="newPost">
				<h2 class="focus">Tiêu điểm</h2>
				<!-- in post ra trinh duyet-->
				<%
					for(int i = 0; i<listPost.size(); i++){
				%>	
				<div class="post">
					<div class="postIll">
						<a href="<%=request.getContextPath()+"/"+listPost.get(i).getCatePostStr()+"/"+listPost.get(i).getPostSlug() %>"><img
							src="<%=request.getContextPath()%>/uploads/thumbnails/<%if(listPost.get(i).getIllustration()!=null){%><%=listPost.get(i).getIllustration().trim() %>
							<%}else{ %>ill_default.jpg<%} %>" alt="<%=listPost.get(i).getPostName()%>" /></a>
					</div>
					<!--end .postIll-->
					<div class="postDetail">
						<h2 class="ttlPost">
							<a href="<%=request.getContextPath()%>/<%=listPost.get(i).getCatePostStr()%>/<%=listPost.get(i).getPostSlug() %>"><%=listPost.get(i).getPostName()%></a>
						</h2>
						<div class="postInfo">
							<span>Đăng bởi <a href="#"><%=listPost.get(i).getUserName()%></a> - Ngày <%=dateFormat.format(listPost.get(i).getPostOn())%> -
								Thể loại <a href="<%=request.getContextPath()+"/category/"+listPost.get(i).getCatePostStr() %>"><%=listPost.get(i).getCatName()%></a></span>
						</div>
						<!--end .postInfo-->
						<div class="postContent">
							<p>
								<%=Utilities.theContent(listPost.get(i).getContent(), 50)%>[...]
							</p>
						</div>
						<!--postContent-->
						<div class="readMore">
							<a class="btnReadMore" href="<%=request.getContextPath()%>/<%=listPost.get(i).getCatePostStr()%>/<%=listPost.get(i).getPostSlug() %>">Đọc thêm</a>
						</div>
						<!--end .readMore-->
					</div>
					<!--end .postDetail-->
				</div>
				<!--end .post-->
				<%} %>
<%
	
	if(!loadOption.equals("auto")){
		int numPages = (int) request.getAttribute("numPages");
		int currentPage = 1;
		if(request.getParameter("p")!=null){
			currentPage = Integer.parseInt(request.getParameter("p").toString());
		}
%>
			
<!-- Phan trang -->
<%
//phan trang

	if(numPages > 1){
		out.println("<div id='pages'>");
		out.println("<ul>");
		if(currentPage != 1){
			out.println("<li><a href='"+request.getContextPath()+"/view?p="+(currentPage-1)+"'>Pre</a></li>");
		}
		for(int i=1; i<=numPages; i++){
			if(i==currentPage){
				out.println("<li><span>"+i+"</span></li>");
			}else{
				out.println("<li><a href='"+request.getContextPath()+"/view?p="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage != numPages){
			out.println("<li><a href='"+request.getContextPath()+"/view?p="+(currentPage+1)+"'>Next</a></li>");
		}
		out.println("</ul>");
		out.println("</div>");
	}

%>
<%
	}else{
%>

			<div id="loadingPosts">
			</div>
<script>
$(document).ready(function(){
	var flag = true;
	$(window).on("scroll", function(){
		var pos = $(this).scrollTop();
		var lastPost = $(".post").last().position().top;
		var numPosts = "numPosts="+$(".post").length.toString();
		if(pos > (lastPost - 300)){
			$(window).off("scroll");
			$.ajax({
				type:"GET",
				url:"/WebAppDemo/processor/load_more_posts.jsp",
				data:numPosts,
				beforeSend: function(){
					$("#loadingPosts").show(0);
				},
				success: function(result){
					$("#loadingPosts").hide(0);
					if(result.trim()!="noMore"){
						$("#loadingPosts").before(result);
					}else{
						flag = false;
					}
				}
			});
		}
	});
	
	$(document).ajaxSuccess(function(){
		if(flag){
			$(window).on("scroll", function(){
				var pos = $(this).scrollTop();
				var lastPost = $(".post").last().position().top;
				var numPosts = "numPosts="+$(".post").length.toString();
				if(pos > (lastPost - 300)){
					$(window).off("scroll");
					$.ajax({
						type:"GET",
						url:"/WebAppDemo/processor/load_more_posts.jsp",
						data:numPosts,
						beforeSend: function(){
							$("#loadingPosts").show(0);
						},
						success: function(result, status){
							$("#loadingPosts").hide(0);
							if(result.trim()!="noMore"){
								$("#loadingPosts").before(result);
							}else{
								flag = false;
							}
						}
					});
				}
			});
		}
	});
});
</script>
<%
	}
}
%>
		</div>
		<!--end #newPost-->

		</div>
		<!--end #content-->
		
		<jsp:include page="//includes/sidebar.jsp" flush="true" />
		</div><!--end #container-->
		<div class="endLine">
		</div><!--end .endLine-->
		</div><!--end #containerWrap-->
<%@include file="//includes/footer.jsp"%>