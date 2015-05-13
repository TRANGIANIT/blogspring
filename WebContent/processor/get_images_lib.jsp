<%@page import="models.POJO.Library"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(request.getAttribute("imgs")==null){
		request.getRequestDispatcher("../ImagesLibraryController").forward(request, response);
	}else{
		ArrayList<Library> imgs = (ArrayList<Library>) request.getAttribute("imgs");
		for(int i=0; i<imgs.size(); i++){
			out.println("<li><div class='imagesItem'>");
			out.println("<img src='"+imgs.get(i).getGuid()+"' alt='"+imgs.get(i).getImageName()+"' />");
			out.println("</div></li>");
		}
	}
%>
<script>
	$(document).ready(function(){
		$(".imagesItem > img").click(function(){
			$(".imagesItem").not($(this).parent()).removeClass("selectedDiv");
			$(this).parent().toggleClass("selectedDiv");
			if($(".selectedDiv > img").attr("alt")){
				var src = $(".selectedDiv > img").attr("src");
				var alt = $(".selectedDiv > img").attr("alt");
				var str = "<h2 id='imgDetails'>CHI TIẾT ẢNH</h2>";
					str += "<img src='"+src+"'/>";
					str += "<span id='imgName'>"+alt+"</span>";
				$("#optionImagesLib").html(str);
				$("#btnSetIll").show(0);
			}else{
				$("#optionImagesLib").empty();
				$("#btnSetIll").hide(0);
			}
		});
		
	});
</script>