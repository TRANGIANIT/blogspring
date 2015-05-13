<%@page import="models.POJO.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(!isAdmin(session)){
		response.sendError(404);
	}else{
		ArrayList<Category> cateList = (ArrayList<Category>) request.getAttribute("cateList");
		ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
		String messages = (String) request.getAttribute("messages");
%>
<%@include file="//includes/header-admin.jsp" %>
<div id="addNewPost">
	<h2 class="ttlContent" id="addNewPostTtl">Thêm Bài Viết Mới</h2>
	<%
		if(messages!=null){
			if(messages.equals("successfully")){
				out.println("<span class='success'>Bạn đã thêm thành công bài viết</span>");
			}
			if(messages.equals("error")){
				out.println("<span class='error'>Không thể lưu. Đã có lỗi xảy ra</span>");
			}
		}
	%>
	<form action="add-new-post" method="post">
		<div id="postContent">
			<script type="text/javascript">
				i=0;
				$(document).ready(function(){
					if($("#txtPostTtl").val() != ""){
						$("#postSlug").text("Permalink: /"+toFrendlyURL($("#txtPostTtl").val())+".html");
					}else{
						$("#postSlug").text("");
					}
					$("#txtPostTtl").keyup(function(){
						if($("#txtPostTtl").val() != ""){
							$("#postSlug").text("Permalink: /"+toFrendlyURL($("#txtPostTtl").val())+".html");
						}else{
							$("#postSlug").text("Bạn chưa điền tiêu đề");
						}
					});
				});
			</script>
			<div id="postTtl">
				<label for="txtPostTtl">Tiêu đề</label>
				<%
					if(errors!=null && errors.size()>0 && errors.contains("title")){
						out.println("<span class='warning'>Bạn chưa điền tên bài viết</span>");
					}
				%>
				<p><input id="txtPostTtl" type="text" name="txtPostTtl" required value="<%if(request.getParameter("txtPostTtl")!=null){out.println(request.getParameter("txtPostTtl"));} %>" /></p>
				<span id="postSlug"></span>
			</div>
			<div id="postDesc">
				<label for="txtPostpostDesc">Mô tả bài viết</label>
				<%
					if(errors!=null && errors.size()>0 && errors.contains("description")){
						out.println("<span class='warning'>Bạn chưa điền mô tả bài viết</span>");
					}
				%>
				<p><textarea id="tarPostDesc" name="tarPostDesc" required><%if(request.getParameter("tarPostDesc")!=null){out.println(request.getParameter("tarPostDesc"));} %></textarea></p>
			</div>
			<div id="postContentBody">
				<label for="tarPostContentBody">Nội dung</label>
				<span class="note" id="para" onclick="addTtl(this)">Đoạn văn</span>
				<span class="note" id="imgTag" onclick="addTtl(this)">Chèn hình ảnh</span>
				<span class="note" id="spSubTtl" onclick="addTtl(this)">Tiêu đề con</span>
				<span class="note" id="spImgTtl" onclick="addTtl(this)">Tiêu đề ảnh</span>
				<span class="note" id="spSourceTtl" onclick="addTtl(this)">Nguồn bài viết</span>
				<span class="note" id="resetTarContent" onmousedown="saveContent()" onclick="addTtl(this)">Viết lại</span>
				<span class="note" id="undoTarContent" onclick="addTtl(this)">Undo</span>
				<%
					if(errors!=null && errors.size()>0 && errors.contains("content")){
						out.println("<span class='warning'>Bạn chưa viết nội dung bài viết</span>");
					}
				%>
				<p><textarea id="tarPostContentBody" name="tarPostContentBody" required><%if(request.getParameter("tarPostContentBody")!=null){out.println(request.getParameter("tarPostContentBody"));} %></textarea></p>
			</div>
		</div><!--end #postContent-->
		<div id="postOption">
			<div id="categoryDiv">
				<h3 class="postOptionDivTtl">Thể loại</h3>
				<%
					if(errors!=null && errors.size()>0 && errors.contains("category")){
						out.println("<span class='warning'>Bạn chưa chọn thể loại</span>");
					}
				%>
				<ul id="categoriesList">
					<%for(int i=0; i<cateList.size(); i++){%>
						<li><label><input type="radio" name="rdCategory" required value="<%=cateList.get(i).getCatID() %>"
							<%
								if(request.getParameter("rdCategory")!=null && cateList.get(i).getCatID() == Integer.parseInt(request.getParameter("rdCategory"))){
									out.println(" checked='checked' ");
								}
							%>
						/><%=cateList.get(i).getCatName() %></label></li>
					<%} %>
				</ul>
			</div>
			<div id="pulishDiv">
				<h3 class="postOptionDivTtl">Publish</h3>
				<span id="note">Đăng bài viết này lên trang chủ</span>
				<input type="submit" id="btnPublish" class="btn" value="Publish" />
				<input type="reset" id="btnReset" class="btn" value="Làm lại" />
			</div>
			<div id="illustrationDiv">
				<h3 class="postOptionDivTtl">Hình ảnh đại diện bài viết</h3>
					<a href="#" class="btnChoseImgIll">Chọn ảnh từ thư viện</a>
					<span id='btnRemoveImgIll'>Bỏ ảnh này</span>
				    <div id="toPopup"> 
				       <div id="popUpTtl">
				       		<h1>Thư viện ảnh</h1>
				       		<span id="btnClose">close</span>
				       </div>
						<div id="popUpContent"> <!--your content start-->
				           <div id="imagesLib">
				           		<ul id="imagesList">

				           		</ul>
				           </div><!--end #imagesLib-->
				           <div id="optionImagesLib">
				           	
				           </div>
				        </div> <!--end #popUpContent-->
				    	<div id="endPopUpDiv">
				    		<a id="btnSetIll" class="btn" href="#" disabled="disabled">Đặt làm hình đại diện</a>
				    	</div><!--end #endPopUpDiv-->
				    </div> <!--toPopup end-->
				    
				   	<div id="backgroundPopup"></div>
			</div>
		</div><!--end #postOption-->
		</form>
</div><!--end #addNewPost-->
<!-- JavaScript -->

<!-- mở thư viện ảnh -->

<script type="text/javascript">
	 //Chuyển đổi không dấu
	toFrendlyURL = function(str) {
	    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
	    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ắ|Ằ|Ặ|Ẳ|Ẵ/g, "A");
	    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
	    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ế|Ề|Ệ|Ể|Ễ/g, "E");
	    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
	    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
	    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
	    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
	    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
	    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
	    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
	    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
	    str = str.replace(/đ/g, "d");
	    str = str.replace(/Đ/g, "D");
	    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'| |\"|\&|\#|\[|\]|~|$|_/g, "-");
	    /* tìm và thay thế các kí tự đặc biệt trong chuỗi sang kí tự - */
	    str = str.replace(/-+-/g, "-"); //thay thế 2- thành 1-
	    str = str.replace(/^\-+|\-+$/g, "");
	    //cắt bỏ ký tự - ở đầu và cuối chuỗi
	    return str;
	};
</script>
<script>
	var flag = true;
	var content = document.getElementById("tarPostContentBody").value;
	function saveContent(){
			content = document.getElementById("tarPostContentBody").value;
	}
</script>
<script>
	function addTtl(id){
		var stt = document.getElementById("spSubTtl");
		var sit = document.getElementById("spImgTtl");
		var st = document.getElementById("spSourceTtl");
		var p = document.getElementById("para");
		var im = document.getElementById("imgTag");
		var rs = document.getElementById("resetTarContent");
		var ud = document.getElementById("undoTarContent");
		switch(id){
		case stt:
			document.getElementById("tarPostContentBody").value += '\n<span class="subTtl"></span>\n';
			break;
		case sit:
			document.getElementById("tarPostContentBody").value += '\n<span class="imageTtl"></span>\n';
			break;
		case st:
			document.getElementById("tarPostContentBody").value += '\n<span id="source"></span>\n';
			break;
		case p:
			document.getElementById("tarPostContentBody").value += '\n<p>\n\t\n</p>\n';
			break;
		case im:
			document.getElementById("tarPostContentBody").value += '\n<img src="" alt=""/>\n';
			break;
		case rs:
			document.getElementById("tarPostContentBody").value = "";
			break;
		case ud:
			document.getElementById("tarPostContentBody").value = content;
			break;
		}
		
	}
</script>
<!-- mở popUp -->
<script>
	$(".btnChoseImgIll").click(function(){
		$.ajax({
			type:"GET",
			url:"/WebAppDemo/processor/get_images_lib.jsp",
			success: function(result){
				$("#imagesList").html(result);
			}
		});
	});
	$("#btnRemoveImgIll").click(function(){
		$(this).hide(0);
		$(".btnChoseImgIll").text("Chọn ảnh từ thư viện");
		$("#txtIllPostName").remove();
	});
</script>
<script type="text/javascript">
	jQuery(function($) {
		$(".btnChoseImgIll").click(function(even) {
			even.preventDefault();
			setTimeout(function(){ // then show popup, deley in .5 second
				loadPopup(); // function show popup
			}, 0); // .5 second
			return false;
		});

		$("#btnClose").click(function(){
			disablePopup();
		});

		$(this).keydown(function(event) {
			if (event.which == 27) { // 27 is 'Ecs' in the keyboard
				disablePopup();  // function close pop up
			}
		});

	        $("div#backgroundPopup").click(function() {
			disablePopup();  // function close pop up
		});

		 /************** start: functions. **************/
		var popupStatus = 0; // set value

		function loadPopup() {
			if(popupStatus == 0) { // if value is 0, show popup
				$("#optionImagesLib").empty();
				$("#toPopup").fadeIn(0); // fadein popup div
				$("#backgroundPopup").css("opacity", "0.7"); // css opacity, supports IE7, IE8
				$("#backgroundPopup").fadeIn(0);
				popupStatus = 1; // and set value to 1
			}
		}

		function disablePopup() {
			if(popupStatus == 1) { // if value is 1, close popup
				$("#toPopup").fadeOut(0);
				$("#backgroundPopup").fadeOut(0);
				popupStatus = 0;  // and set value to 0
			}
		}
		/************** end: functions. **************/

		$("#btnSetIll").click(function(even){
			even.preventDefault();
			var src = $(".selectedDiv > img").attr("src");
			var alt = $(".selectedDiv > img").attr("alt");
			disablePopup(); 
			$(".btnChoseImgIll").html("<img src='"+src+"'/>");
			$("#illustrationDiv").append("<input type='hidden' name='illPostName' id='txtIllPostName' value='"+alt+"'/>");
			$(this).hide(0);
			$("#btnRemoveImgIll").show(0);
		});
	}); // jQuery End
	

</script>
<%@include file="//includes/footer-admin.jsp" %>
<%}%>