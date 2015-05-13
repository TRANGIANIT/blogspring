<%@page import="models.POJO.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	if(!isAdmin(session)){
		response.sendError(404);
	}else{
		if(request.getAttribute("cateList")==null){
			response.sendRedirect("edit-category?cid="+request.getParameter("cid"));
		}else{
			Category c = (Category) request.getAttribute("cate");
			ArrayList<Category> cateList = (ArrayList<Category>) request.getAttribute("cateList");
			String messages = (String) request.getAttribute("messages");

%>
<%@include file="//includes/header-admin.jsp" %>
			<div id="editCat">
			<h2 class="ttlContent" id="catPageTtl">Sửa thể loại bài viết</h2>
			<%
				if(messages!=null){
					if(messages.equals("successfully")){
						out.println("<span class='success'>Bạn đã sửa thành công thể loại</span>");
					}
					if(messages.equals("error")){
						out.println("<span class='error'>Không thể lưu. Đã có lỗi xảy ra</span>");
					}
				}
			%>
			<form action="edit-category?cid=<%=request.getParameter("cid").toString().trim()%>" method="post">
				<table id="tblEditCat">
				<tr>
					<th>
						<label for="txtCatTtlEdit"><span>Tên thể loại bài viết</span></label>
					</th>
					<td><input id="txtCatTtlEdit" type="text" name="txtCatTtlEdit"  required="required"  value="<%if(c!=null){out.println(c.getCatName().trim());} %>" /></td>
				</tr>
				<tr>
					<th>
						<label for="txtCatSlugEdit"><span>Slug</span></label>
					</th>
					<td><input id="txtCatSlugEdit" type="text" name="txtCatSlugEdit" required="required" value="<%if(c!=null){out.println(c.getSlug().trim());} %>" /></td>
				</tr>
				<tr>
					<th>
						<label for="cbbCatParentsEdit"><span>Thể loại bài viết cha</span></label>
					</th>
					<td>
						<select name="cbbCatParentsEdit" id="cbbCatParentsEdit">
						<option value = "-1">-----Không-----</option>
						<%for(int i=0; i<cateList.size(); i++){%>
							<option value="<%=cateList.get(i).getCatID() %>"
							<%
								if(c!=null && cateList.get(i).getCatID() == c.getParentID()){
									out.println(" selected='selected' ");
								}
							%>
							><%=cateList.get(i).getCatName()%></option>
						<%} %>
					</select>
					</td>
				</tr>
				<tr>
					<th>
						<label for="tarCatDescEdit"><span>Mô tả</span></label>
					</th>
					<td><textarea id="tarCatDescEdit" name="tarCatDescEdit"><%if(c!=null && c.getCatDesc()!=null){out.println(c.getCatDesc().trim());} %></textarea></td>
				</tr>
	
				
				
			</table>
			<p><input type="submit" id="btnEditCat" class="btn" value="Lưu thay đổi" /></p>
			</form>
			</div>
<%@include file="//includes/footer-admin.jsp" %>
<%}} %>