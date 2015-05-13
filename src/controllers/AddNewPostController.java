package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libraries.Utilities;
import models.DAO.CategoryDAO;
import models.DAO.PostDAO;
import models.POJO.Category;
import models.POJO.Post;
import models.POJO.User;

/**
 * Servlet implementation class AddNewPostController
 */
// @WebServlet("/AddNewPostController")
public class AddNewPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewPostController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
				ArrayList<String> errors = new ArrayList<String>();
				// nếu là lần đầu request thì chua gán lỗi
				if (request.getParameter("txtPostTtl") == null
						&& request.getParameter("tarPostContentBody") == null
						&& request.getParameter("tarPostDesc") == null
						&& request.getParameter("rdCategory") == null) {
					ArrayList<Category> cateList = CategoryDAO.getCategories();
					request.setAttribute("cateList", cateList);
					request.getRequestDispatcher("/admin/add-new-post.jsp")
							.forward(request, response);
				} else {
					String postTtl = null;
					if (request.getParameter("txtPostTtl") == null
							|| request.getParameter("txtPostTtl").trim() == "") {
						errors.add("title");
					} else {
						postTtl = request.getParameter("txtPostTtl");
					}
					String postContent = null;
					if (request.getParameter("tarPostContentBody") == null
							|| request.getParameter("tarPostContentBody")
									.trim() == "") {
						errors.add("content");
					} else {
						postContent = request
								.getParameter("tarPostContentBody");
					}
					String postDesc = null;
					if (request.getParameter("tarPostDesc") == null
							|| request.getParameter("tarPostDesc").trim() == "") {
						errors.add("description");
					} else {
						postDesc = request.getParameter("tarPostDesc");
					}
					int catID = -1;
					if (request.getParameter("rdCategory") == null
							|| request.getParameter("rdCategory").trim() == "") {
						errors.add("category");
					} else {
						catID = Integer.parseInt(request.getParameter(
								"rdCategory").toString());
					}
					String illPost = request.getParameter("illPostName").toString().trim();
					if (errors.size() == 0) {
						String postSlug = Utilities.toUrlFriendly(postTtl)+".html";
						int userID = u.getUserID();
						Date postOn = new Date();
						Post p = new Post();
						p.setPostName(postTtl);
						p.setCatID(catID);
						p.setContent(postContent);
						p.setUserID(userID);
						p.setPostDesc(postDesc);
						p.setPostOn(postOn);
						p.setPostSlug(postSlug);
						p.setIllustration(illPost);
						int affectedRows = PostDAO.addNewPost(p);
						if (affectedRows == 1) {
							request.setAttribute("messages", "successfully");
						} else {
							request.setAttribute("messages", "error");
						}
					} else {
						request.setAttribute("errors", errors);
					}
					ArrayList<Category> cateList = CategoryDAO.getCategories();
					request.setAttribute("cateList", cateList);
					request.getRequestDispatcher("/admin/add-new-post.jsp")
							.forward(request, response);
				}
			}
		}
	}
}
