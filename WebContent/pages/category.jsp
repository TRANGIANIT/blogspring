<%@page import="libraries.Utilities"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="//includes/header.jsp"%>
<%
	int numPages = (int) request.getAttribute("numPages");
	ArrayList<Post> listPost = (ArrayList<Post>) request.getAttribute("listPost");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	int currentPage = 1;
	if(request.getParameter("p")!=null){
		currentPage = Integer.parseInt(request.getParameter("p").toString());
	}
	String url=(String)request.getAttribute("add");
%>

	<div id="container">
		<div id="content">
			<!---->
			<div id="newPost">
				<h2 class="focus"><%
					if(request.getAttribute("catName")!=null){
						out.println(request.getAttribute("catName"));
					}else{
						out.println("NOT FOUND");
					}
						
				%></h2>
				<!-- in post ra trinh duyet-->
				<%
				if(listPost.size()>0){
					for(int i = 0; i<listPost.size(); i++){
				%>	
				<div class="post">
					<div class="postIll">
						<a href="<%=request.getContextPath()+"/"+listPost.get(i).getCatePostStr()+"/"+listPost.get(i).getPostSlug() %>"><img
							src="<%=request.getContextPath()%>/uploads/thumbnails/<%if(listPost.get(i).getIllustration()!=null){%>
							<%=listPost.get(i).getIllustration() %>
							<%}else{ %>ill_default.jpg<%} %>" alt="<%=listPost.get(i).getPostName()%>" alt="<%=listPost.get(i).getPostName()%>" /></a>
					</div>
					<!--end .postIll-->
					<div class="postDetail">
						<h2 class="ttlPost">
							<a href="<%=request.getContextPath()+"/"+listPost.get(i).getCatePostStr()+"/"+listPost.get(i).getPostSlug() %>"><%=listPost.get(i).getPostName()%></a>
						</h2>
						<div class="postInfo">
							<span>Đăng bởi <a href="#"><%=listPost.get(i).getUserName()%></a> - Ngày <%=dateFormat.format(listPost.get(i).getPostOn())%> -
								Thể loại <a href="<%=request.getContextPath()+"/category/"+listPost.get(i).getCatePostStr() %>"><%=listPost.get(i).getCatName()%></a></span>
						</div>
						<!--end .postInfo-->
						<div class="postContent">
							<p>
								<%=Utilities.theContent(listPost.get(i).getContent(), 60)%>[...]
							</p>
						</div>
						<!--postContent-->
						<div class="readMore">
							<a class="btnReadMore" href="<%=request.getContextPath()+"/"+listPost.get(i).getCatePostStr()+"/"+listPost.get(i).getPostSlug() %>">Đọc thêm</a>
						</div>
						<!--end .readMore-->
					</div>
					<!--end .postDetail-->
				</div>
				<!--end .post-->
				<%
					}
				}
				%>

				<!-- Phan trang -->
				<%
					if(numPages > 1){
						out.println("<div id='pages'>");
						out.println("<ul>");
						if(currentPage != 1){
							out.println("<li><a href='"+url+"?p="+(currentPage-1)+"'>Pre</a></li>");
						}
						for(int i=1; i<=numPages; i++){
							if(i==currentPage){
								out.println("<li><span>"+i+"</span></li>");
							}else{
								out.println("<li><a href='"+url+"?p="+i+"'>"+i+"</a></li>");
							}
						}
						if(currentPage != numPages){
							out.println("<li><a href='"+url+"?p="+(currentPage+1)+"'>Next</a></li>");
						}
						out.println("</ul>");
						out.println("</div>");
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