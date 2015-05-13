<%@page import="models.POJO.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- kiem tra dang nhap chua --> 
<%!
	public boolean isLogin(HttpSession session){
		if (session.getAttribute("user")!=null) {
			return true;
		}else{
			return false;
		}
	}
%>
<!-- Kiem tra admin -->
<%!
	public boolean isAdmin(HttpSession session){
		if(isLogin(session)){
			User u = (User)session.getAttribute("user");
			if (u.getUserLevel()== 3) {
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
%>
<!-- đếm số lượt truy cập -->
<%!
	public void appCounter(ServletContext application){
		if(application.getAttribute("counter")!=null){
			int counter = (int)application.getAttribute("counter");
			counter ++;
			application.setAttribute("counter", counter);
		}else{
			application.setAttribute("counter", 1);
		}
	}
%>
<!-- đếm số lượt truy cập từng trang -->
<%!
	public void pageCounter(ServletContext application, HttpServletRequest request){
		String servletPath = request.getServletPath().trim();
		String attName = servletPath.substring(1)+"counter";
		if(application.getAttribute(attName)!=null){
			int counter = (int)application.getAttribute(attName);
			counter ++;
			application.setAttribute(attName, counter);
		}else{
			application.setAttribute(attName, 1);
		}
	}
%>
<!-- lấy số lượng truy cập -->
<%!
	public int getPageCounter(String pageName, ServletContext application){
		pageName+="counter"; 
		int pageCounter = 0;
		if(application.getAttribute(pageName)!=null){
			pageCounter = (int)application.getAttribute(pageName);
		}
		return pageCounter;
	}
%>
<%!
	public int getAppCounter(ServletContext application){
		int counter = 0;
		if(application.getAttribute("counter")!=null){
			counter = (int)application.getAttribute("counter");
		}
		return counter;
	}
%>

