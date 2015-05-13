<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(request.getAttribute("status")==null){
		request.getRequestDispatcher("../DeleteCommentWithAJAX").forward(request, response);
	}else{
		String status = (String)request.getAttribute("status");
		System.out.println(status);
		out.print(status);
	}
%>