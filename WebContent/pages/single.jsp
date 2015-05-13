<%@page import="libraries.CommonLibrary"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.POJO.User"%>
<%@page import="models.POJO.Post"%>
<%@page import="models.POJO.Comment"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="//includes/single-header.jsp"%>
<%
	Post post = (Post) request.getAttribute("post");
	User author= (User) request.getAttribute("author");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
%>
<!---->	
	<div id="containerWrap">
<!---->
		<div id="container">
			<div id="content">
<!---->				
				<div id="singlePost">
					<div id="singlePostHead">
						<h2 id="ttlSinglePost"><%=post.getPostName() %></h2>
						<span id="singlePostInfo">Tác giả: <a href="#"><%=post.getUserName() %></a> Ngày: <%=dateFormat.format(post.getPostOn()) %></span>
					</div><!--end  #singlePostHead-->
					<div id="singlePostDesc">
						<div id="illSinglePost">
							<img src="<%=request.getContextPath()%>/uploads/thumbnails/<%if(post.getIllustration()!=null){%>
							<%=post.getIllustration() %>
							<%}else{ %>ill_default.jpg<%} %>" alt="<%=post.getPostName() %>"/>
						</div>
						<div id="singlePostDescContent">
							<p>
								<%=post.getPostDesc() %>
							</p>
						</div>
					</div><!--end #singlePostDesc-->
					<div id="singlePostContent">
						<%=post.getContent() %>
					</div><!--end  #singlePostContent-->
				</div><!--end #singlePost-->

				<div id="authorInfo">
					<div id="authorAvata">
						<img src="<%= request.getContextPath()%>/uploads/avatars/<%=author.getAvatar() %>" alt="<%=post.getUserName() %>"/>
					</div>
					<div id="authorDesc">
						<h3 id="authorName"><%=author.getLastName().toUpperCase() +" "+ author.getFirstName().toUpperCase() %></h3>
						<p>
							<%=author.getBio() %>
						</p>
					</div>
					<div id="authorContact">
						<h3>Liên hệ</h3>
						<span><a href="<% if(author.getFacebook()!=null){out.println(author.getFacebook());}else{out.println("#");}%>" target="_blank">Facebook</a></span>
					</div>
				</div><!--end #authorInfo-->
			<%
		    	ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
		    	User user = (User) request.getAttribute("user");
		    	int numComments = (int) request.getAttribute("numComments");
		    %>
			<div id="commentContainer">
					<h3 id="ttlCommentContainer"><span id="numComments"><%=numComments %></span> Bình luận</h3>
					<div id="commentBox">
					<%if(isLogin(session)){ %>
							<div id="userAvataNewCmt">
								<img src="<%= request.getContextPath()%>/uploads/avatars/<%=user.getAvatar() %>" alt="<%=user.getLastName()+" "+user.getFirstName() %>">
							</div>
							<div id="commentForm">
								<form action="<%=request.getContextPath()%>/comment" method="post">
									<textarea id="tarComment" name="comment" required placeholder="Viết bình luận"></textarea>
									<input type="hidden" name="postid" value="<%=post.getPostID()%>"/>
									<input type="submit" id="btnPostComment" name="postComment" value="Bình luận" />
								</form>
							</div><!--end #commentForm-->
					<%}else{ 
						String from = request.getContextPath()+"/"+post.getCatePostStr()+"/"+post.getPostSlug();
					%>
						
						<span id="loginPleaseMsg">Vui lòng <a href="<%=request.getContextPath()%>/login?from=<%=from%>">đặng nhập</a> để có thể bình luận</span>
					<%} %>
					</div><!--end #commentBox-->
					
					<%
						for(int i=0; i<comments.size(); i++){
					%>
						<div class="commentItem">
							<div class="userAvata">
								<img src="<%= request.getContextPath()%>/uploads/avatars/<%=comments.get(i).getUserAvatar() %>" alt="<%=comments.get(i).getUserName() %>">
							</div>
							<div class="commentContent">
								<p>
									<span class="userName"><%=comments.get(i).getUserName() %></span>
									<%=comments.get(i).getComment() %>
								</p>
								<span class="livetimestamp"><%=CommonLibrary.timePrinter(comments.get(i).getCommentDate()) %></span>
								<%if(isAdmin(session)){ %>
									<span class="btnRemove" id="<%=comments.get(i).getCommentID() %>">Remove</span>
									<script type="text/javascript">
										$(document).ready(function(){
											$(".btnRemove").click(function(){
												var commentItem = $(this).parent().parent();
												var numComments = $("#numComments").text();
												var str = "commentID="+$(this).attr("id");
												$.ajax({
													type:"POST",
													url: "/WebAppDemo/DeleteCommentWithAJAX",
													data: str,
													success : function(result){
														if(result=="success"){
															commentItem.css("background","#F76E56").delay(150).animate({opacity:"0"}, 400).slideUp(100, function(){
																commentItem.remove();
															});
															$("#numComments").text(numComments-1);
														}
													}
												});
											});
										});
									</script>
								<%} %>
							</div>
						</div><!--end .commentItem-->
					<%} %>
				</div><!--end #commentContainer-->
				
			</div><!--end #content-->
<!-- -->
			
		<jsp:include page="//includes/sidebar.jsp" flush="true" />
	
		</div><!--end #container-->
		<div class="endLine">
		</div><!--end .endLine-->
		</div><!--end #containerWrap-->
		
	<%@include file="//includes/footer.jsp"%>