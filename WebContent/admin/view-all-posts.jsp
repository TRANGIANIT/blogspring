<%@page import="java.util.Date"%>
<%@page import="models.POJO.Category"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="libraries.Utilities"%>
<%@page import="libraries.PostLibrary"%>
<%@page import="libraries.CommonLibrary"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="//includes/header-admin.jsp"%>
<%
	if (!isAdmin(session)) {
		response.sendError(404);
	} else {
		if (request.getAttribute("listPost") == null) {
			response.sendRedirect("view-all-posts");
		} else {
			ArrayList<Post> listPost = (ArrayList<Post>) request
					.getAttribute("listPost");
			ArrayList<Category> categories = (ArrayList<Category>) request
					.getAttribute("categories");
			ArrayList<Date> dateList = (ArrayList<Date>) request
					.getAttribute("dateList");
			int totalPosts = (int) request.getAttribute("totalPosts");
			int numPages = (int) request.getAttribute("numPages");
			int currentPage = 1;
			String str = request.getParameter("p");

			if (str != null && str.matches("[+-]?\\d*(\\d+)?")) {
				currentPage = Integer.parseInt(request
						.getParameter("p"));
			}
			if (currentPage < 1) {
				currentPage = 1;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd-MM-yyyy");
%>
<div id="allPost">
	<h2 class="ttlContent" id="addNewPostTtl">Tất Cả Bài Viết</h2>
	<form action="" method="get" name="filterForm" id="filterForm">
		<div id="tblNav">
			<span id="numsPost">Tất Cả: <span id="totalPosts"><%=totalPosts%></span> Bài Viết
			</span>
			<div id="postFilter">
				<select name="catID">
					<option value="all">Tất Cả Thể Loại</option>
					<%
						if (categories != null) {
									for (int i = 0; i < categories.size(); i++) {
					%>
					<option value="<%=categories.get(i).getCatID()%>"
						<%if (request.getParameter("catID") != null
									&& request.getParameter("catID").matches(
											"[+-]?\\d*(\\d+)?")
									&& categories.get(i).getCatID() == Integer
											.parseInt(request
													.getParameter("catID"))) {
								out.println(" selected='selected' ");
							}%>>
						<%=categories.get(i).getCatName()%>
					</option>
					<%
						}
								}
					%>
				</select> <select name="date">
					<option value="all">Tất Cả Ngày Tháng</option>
					<%
						if (dateList != null) {
									for (int i = 0; i < dateList.size(); i++) {
										out.println("<option value='"
												+ CommonLibrary
														.printMonthAndYearOnly(dateList
																.get(i)) + "'");
										if (request.getParameter("date") != null
												&& CommonLibrary.printMonthAndYearOnly(
														dateList.get(i)).equals(
														request.getParameter("date"))) {
											out.println(" selected = 'selected' ");
										}
										out.println(">"
												+ CommonLibrary
														.timePrinterForFilter(dateList
																.get(i)));
										out.println("</option>");
									}
								}
					%>
				</select> <input id="btnPostFilter" type="submit" value="Lọc" />
			</div>
			<!--end #postFilter-->
			<div id="pages">
				<a href="view-all-posts?p=1">&laquo;</a> <a
					href="view-all-posts?p=<%if (currentPage > 1) {
						out.print(currentPage - 1);
					} else {
						out.print(currentPage);
					}%>">&lsaquo;</a>
				<form action="view-all-posts" method="get" id="pageForm">
					<span><input type="text" id="currentPage" name="p"
						value="<%=currentPage%>" size="2" /> / <%=numPages%></span>
				</form>
				<a
					href="view-all-posts?p=<%if (currentPage < numPages) {
						out.print(currentPage + 1);
					} else {
						out.print(currentPage);
					}%>">&rsaquo;</a>
				<a href="view-all-posts?p=<%=numPages%>">&raquo;</a>
			</div>
		</div>
		<!--end #tblNav-->
	</form>
	<%
		String p = request.getParameter("p");
		String date = request.getParameter("date");
		String catID = request.getParameter("catID");
		String prams = "";
		if(catID!=null){
			prams+="?catID="+catID;
		}else{
			prams+="?catID=all";
		}
		if(date!=null){
			prams+="&date="+date;
		}else{
			prams+="&date=all";
		}
		if(p!=null){
			prams+="&p="+p;
		}
		if(prams.equals("")){
			prams="?";
		}else{
			prams+="&";
		}
		String sort = "&sort=";
		if(request.getParameter("txtSort")!=null){
			sort += request.getParameter("txtSort");
		}else{
			sort = "";
		}
	%>
	<div id="postList">
	
	<script type="text/javascript">
	//hàm lấy giá trị parameter theo tên
		function getParameterByName(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
	</script>
	<script type="text/javascript">
		//thay đổi url để sắp xếp theo ngày tháng
		$(document).ready(function(){
			var orderby=getParameterByName("orderby");
			if(orderby == ""){
				$("#txtSort").remove();
		  		$("#sortByDate").attr("href", $("#sortByDate").attr("href")+ "&order=asc");
			}else{
				$("#filterForm").append("<input type='hidden' id='txtSortBy' name='sortby' value='post_on'/>");
				$("#filterForm").append("<input type='hidden' id='txtSort' name='sort' value='desc'/>");
				var order=getParameterByName("order");
				if(order==""){
			  		$("#sortByDate").attr("href", $("#sortByDate").attr("href")+ "&order=asc");
				}else{
				  	if(order == "desc"){
				  		$("#sortByDate").attr("href", $("#sortByDate").attr("href")+ "&order=asc");
				  		$("#txtSort").val("asc");
				  	}else{
				  		$("#sortByDate").attr("href", $("#sortByDate").attr("href")+ "&order=desc");
				  		$("#txtSort").val("desc");
				  	}
				}
			}
		});
	</script>
		<table id="listPosts">
			<tr>
				<th>STT</th>
				<th>Tiêu Đề</th>
				<th>Tác Giả</th>
				<th>Nội Dung</th>
				<th>Thể Loại</th>
				<th><a id="sortByDate" href="<%=request.getContextPath()%>/admin/view-all-posts<%=prams%>orderby=post_on">Ngày</a></th>
				<th>Số bình luận</th>
				<th></th>
			</tr>
			<%
				for (int i = 0; i < listPost.size(); i++) {
			%>
			<tr>
				<td><%=(i + 1)%></td>
				<td><a
					href="<%=request.getContextPath()%>/<%=listPost.get(i).getCatePostStr()%>/<%=listPost.get(i).getPostSlug()%>"><%=listPost.get(i).getPostName()%></a></td>
				<td><%=listPost.get(i).getUserName()%></td>
				<td><%=Utilities.theContent(listPost.get(i)
								.getContent(), 60)
								+ " ..."%>
				</td>
				<td><%=listPost.get(i).getCatName()%></td>
				<td><%=dateFormat
								.format(listPost.get(i).getPostOn())%></td>
				<td><%=listPost.get(i).getNumComments()%></td>
				<td><a href="post?pid=<%=listPost.get(i).getPostID()%>">Sửa</a><span id="<%=listPost.get(i).getPostID()%>" class="btnRemovePost">Xóa</span></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<!--end #postList-->
</div>
<!--end #allPost-->
<%
	}
	}
%>
<!-- JQuery for remove Post -->
<script type="text/javascript">
	$(".btnRemovePost").click(function(){
		var tr = $(this).parent().parent();
		var str = "postID="+$(this).attr("id");
		var totalPosts = $("#totalPosts").text();
		if(confirm("Bạn chắc chắn muốn xóa?")){
			$.ajax({
				type:"POST",
				url:"/WebAppDemo/DeletePostWithAJAX",
				data: str,
				success: function(result){
					if(result.trim()=="success"){
						tr.css("background","#F76E56").delay(150).animate({opacity:"0"},400).slideUp(300, function(){
							tr.remove();
						});
						$("#totalPosts").text(totalPosts-1);
					}
				}
			});
		}
	});
</script>
<%@include file="//includes/footer-admin.jsp"%>