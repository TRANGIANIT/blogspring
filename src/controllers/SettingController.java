package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.OptionDAO;
import models.POJO.Option;
import models.POJO.User;

/**
 * Servlet implementation class SettingController
 */
//@WebServlet("/SettingController")
public class SettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    // Kiem tra admin
 		HttpSession session = request.getSession();
 		if (session.getAttribute("user") == null) {
 			response.sendError(404);
 		} else {
 			User u = (User) session.getAttribute("user");
 			if (u.getUserLevel() != 3) {
 				response.sendError(404);
 			} else {
				if (request.getParameterNames().hasMoreElements()){
					ArrayList<Option> ol = new ArrayList<Option>();
					String siteName = request.getParameter("siteName");
					Option o1 = new Option();
					o1.setOptionName("blog_name");
					o1.setOptionValue(siteName);
					ol.add(o1);
					
					String siteSlogan = request.getParameter("siteSlogan");
					Option o2 = new Option();
					o2.setOptionName("blog_desc");
					o2.setOptionValue(siteSlogan);
					ol.add(o2);
					
					String rdLoadPostOption = request.getParameter("rdLoadPostOption");
					Option o3 = new Option();
					o3.setOptionName("load_more_posts");
					o3.setOptionValue(rdLoadPostOption);
					ol.add(o3);
					
					String postsPerPage = request.getParameter("postsPerPage");
					if(postsPerPage!=null){
						Option o4 = new Option();
						o4.setOptionName("posts_per_page");
						o4.setOptionValue(postsPerPage);
						ol.add(o4);
					}
					
					String postsLoad = request.getParameter("postsLoad");
					if(postsLoad!=null){
						Option o5 = new Option();
						o5.setOptionName("posts_per_load");
						o5.setOptionValue(postsLoad);
						ol.add(o5);
					}
					
					int affectedRows = OptionDAO.changeOptionsList(ol);
					if (affectedRows > 0) {
						request.setAttribute("messages", "successfully");
					} else {
						request.setAttribute("messages", "error");
					}
				}
 				ArrayList<Option> optionsList = OptionDAO.getOptions();
 				request.setAttribute("optionsList", optionsList);
				request.getRequestDispatcher("/admin/setting.jsp")
				.forward(request, response);
 			}
 		}
	}

}
