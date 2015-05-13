<%@page import="models.POJO.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(!isAdmin(session)){
		response.sendError(404);
	}else{
		if(request.getAttribute("cateList")==null){
			response.sendRedirect("categories");
		}else{
			ArrayList<Category> cateList = (ArrayList<Category>) request.getAttribute("cateList");
			ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
			String messages = (String) request.getAttribute("messages");
%>
<%@include file="//includes/header-admin.jsp" %>
<div id="categories">
			<h2 class="ttlContent" id="catPageTtl">Thể loại bài viết</h2>
			<%
				if(messages!=null){
					if(messages.equals("successfully")){
						out.println("<span class='success'>Bạn đã thêm thành công thể loại</span>");
					}
					if(messages.equals("error")){
						out.println("<span class='error'>Không thể lưu. Đã có lỗi xảy ra</span>");
					}
				}
			%>
			<div id="addCat">
			<form action="categories" method="post">
				<h2 class="subTtlContent" id="addCatTtl">Thêm thể loại</h2>
				<div id="catTtlDiv">
					<label for="txtCatTtl">Tên thể loại</label>
					<%
						if(errors!=null && errors.size()>0 && errors.contains("name")){
							out.println("<span class='warning'>Bạn chưa điền tên thể loại</span>");
						}
					%>
					<p><input id="txtCatTtl" type="text" name="txtCatTtl"  required="required"  value="<%if(request.getParameter("txtCatTtl")!=null){out.println(request.getParameter("txtCatTtl"));} %>" /></p>
				</div>
				<div id="catSlugDiv">
					<label for="txtCatSlug">Slug</label>
					<%
						if(errors!=null && errors.size()>0 && errors.contains("slug")){
							out.println("<span class='warning'>Bạn chưa điền slug</span>");
						}
					%>
					<p><input id="txtCatSlug" type="text" name="txtCatSlug" required="required" value="<%if(request.getParameter("txtCatSlug")!=null){out.println(request.getParameter("txtCatSlug"));} %>" /></p>
				</div>
				<div id="catParentDiv">
					<label for="cbbCatParents">Thể loại cha</label>
					<p>
					<select name="cbbCatParents" id="cbbCatParents">
						<option value = "-1">-----Không-----</option>
						<%for(int i=0; i<cateList.size(); i++){%>
							<option value="<%=cateList.get(i).getCatID() %>"
							<%
								if(request.getParameter("cbbCatParents")!=null && cateList.get(i).getCatID() == Integer.parseInt(request.getParameter("cbbCatParents"))){
									out.println(" selected='selected' ");
								}
							%>
							><%=cateList.get(i).getCatName()%></option>
						<%} %>
					</select>
					</p>
				</div>
				<div id="catDescDiv">
					<label for="tarCatDesc">Mô tả</label>
					<p><textarea id="tarCatDesc" name="tarCatDesc"><%if(request.getParameter("tarCatDesc")!=null){out.println(request.getParameter("tarCatDesc"));} %></textarea></p>
				</div>
				<input type="submit" id="btnAddNewCat" class="btn" value="Thêm Thể Loại Mới" />
			</form>
			</div><!--end #addCat-->

			<div id="viewCat">
				<table>
					<tr>
						<th>Thể Loại</th>
						<th>Mô tả</th>
						<th>Cha</th>
						<th>Slug</th>
						<th>Bài Viết</th>
						<th></th>
					</tr>
					<%
						for(int i=0; i<cateList.size(); i++){
							out.println("<tr>");
							out.println("<td>"+cateList.get(i).getCatName()+"</td>");
							out.println("<td>");
								if(cateList.get(i).getCatDesc()!=null){
									out.println(cateList.get(i).getCatDesc());
								}
							out.println("</td>");
							out.println("<td>");
								if(cateList.get(i).getParentName()!=null){
									out.println(cateList.get(i).getParentName());
								}
							out.println("</td>");
							out.println("<td>"+cateList.get(i).getSlug()+"</td>");
							out.println("<td>"+cateList.get(i).getNumPosts()+"</td>");
							out.println("<td><a href='edit-category?cid="+cateList.get(i).getCatID()+"'>Sửa</a><span class='btnRemoveCate' id='"+cateList.get(i).getCatID()+"'>Xóa</span></td>");
							out.println("</tr>");
						}
					%>
				</table>
			</div><!--end #viewCat-->
		</div><!--end #categories-->
<%@include file="//includes/footer-admin.jsp" %>
<!-- JavaScript -->
<script>
	$(document).ready(function(){
		$(".btnRemoveCate").click(function(){
			if(confirm("Bạn chắc chắn muốn xóa?")){
				var trWrap = $(this).parent().parent();
				var str = "catID="+$(this).attr("id");
				$.ajax({
					type:"POST",
					url:"/WebAppDemo/DeleteCategoryWithAJAX",
					data:str,
					success: function(result){
						if(result.trim()=="success"){
							trWrap.css({"background":"#F76E56"}).delay(150).animate({opacity:"0"}, 400).slideUp(300, function(){
								trWrap.remove();
							});
						}
					}
				});
			}
		});
	});
</script>
<%}} %>