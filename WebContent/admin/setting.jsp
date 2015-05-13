<%@page import="models.POJO.Option"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(!isAdmin(session)){
		response.sendError(402);
	}else{
		if(request.getAttribute("optionsList")==null){
			response.sendRedirect("setting");
		}else{
			ArrayList<Option> optionsList = (ArrayList<Option>) request.getAttribute("optionsList");
			String messages = (String) request.getAttribute("messages");
%>
<%@include file="//includes/header-admin.jsp" %>
<div id="settingDiv">
	<h2 class="ttlContent" id="settingDivTtl">Cài đặt trang</h2>
	<%
		if(messages!=null){
			if(messages.equals("successfully")){
				out.println("<span class='success'>Lưu thành công</span>");
			}
			if(messages.equals("error")){
				out.println("<span class='error'>Không thể lưu. Đã có lỗi xảy ra</span>");
			}
		}
	%>
	<form action="setting" method="POST">
		<div id="loadPostOption">
			<table id="settingTbl">
				<tr>
					<th>
						<label for="siteTtl"><span>Tên trang web</span></label>
					</th>
					<td><input type="text" name="siteName" id="siteName" value="<%=optionsList.get(0).getOptionValue() %>" class="txt"></td>
				</tr>
				<tr>
					<th>
						<label for="siteSlogan"><span>Slogan trang</span></label>
					</th>
					<td><input type="text" name="siteSlogan" id="siteSlogan" value="<%=optionsList.get(1).getOptionValue() %>" class="txt"></td>
				</tr>
	
				<tr>
					<th>
						<span>Tải thêm Post trên trang chủ</span>
					</th>
					<td>
						<p class="inputCnt">
							<label for="auto">
								<input type="radio" name="rdLoadPostOption" value="auto" id="auto"  <% if(optionsList.get(2).getOptionValue().equals("auto")){out.print("checked = 'checked'");} %>>
								<span>Tải tự động bằng AJAX</span>
							</label>
						</p>
						<p  class="inputCnt">
							<label>
								<span id="postsLoadTtl">Số post load 1 lần:</span>
								<input type="number" id="postsLoad" name="postsLoad" min="1" step="1" value="<%=optionsList.get(4).getOptionValue() %>" <% if(!optionsList.get(2).getOptionValue().equals("auto")){out.print("disabled");}%>>
							</label>
						</p>
						<p  class="inputCnt">
							<label  for="pagesNav">
								<input type="radio" name="rdLoadPostOption" value="pagesNav" id="pagesNav" <% if(optionsList.get(2).getOptionValue().equals("pagesNav")){out.print("checked = 'checked'");} %>>
								<span>Sử dụng phân trang</span>
							</label>
						</p>
						<p  class="inputCnt" id="postPerPageOption">
							<label>
								<span id="postsPerPageTtl">Số post trên 1 trang:</span>
								<input type="number" id="postsPerPage" name="postsPerPage" min="1" step="1" value="<%=optionsList.get(3).getOptionValue() %>" <% if(!optionsList.get(2).getOptionValue().equals("pagesNav")){out.print("disabled");}%>>
							</label>
						</p>
					</td>
				</tr>
				
			</table>
			<p><input type="submit" value="Lưu thay đổi" class="btn" id="btnSaveSetting"></p>
		</div><!--end #loadPostOption-->
	</form>
</div><!--end #settingDiv-->
<!-- JavaScript -->
<script>
	$(document).ready(function(){
		$("#pagesNav").change(function(){
			$("#postsPerPage").removeAttr("disabled");
			$("#postsLoad").attr("disabled", "disabled");
		});	
		$("#auto").change(function(){
			$("#postsPerPage").attr("disabled", "disabled");
			$("#postsLoad").removeAttr("disabled");
		});	
	});
</script>
<%@include file="//includes/footer-admin.jsp" %>
<%}}%>