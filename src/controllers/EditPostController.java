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
 * Servlet implementation class EditPostController
 */
// @WebServlet("/EditPostController")
public class EditPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditPostController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
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
				String postIDTmp = (String) request.getParameter("pid");
				if (postIDTmp != null && postIDTmp.matches("[+-]?\\d*(\\d+)?")) {
					int postID = Integer.parseInt(postIDTmp);
					if (postID > 0) {
						Post post = PostDAO.getPostByID(postID);
						if(post==null){
							response.sendError(404);
						}else{
							request.setAttribute("post", post);
							ArrayList<Category> cateList = CategoryDAO
									.getCategories();
							request.setAttribute("cateList", cateList);
							request.getRequestDispatcher("/admin/post.jsp?pid="+postID)
							.forward(request, response);
						}
					}else{
						response.sendError(404);
					}
				} else {
					response.sendError(404);
				}
			}
		}
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
				String postIDTmp = (String) request.getParameter("pid");
				if (postIDTmp != null && !postIDTmp.trim().equals("") && postIDTmp.matches("[+-]?\\d*(\\d+)?")) {
					int postID = Integer.parseInt(postIDTmp.trim());
					if (postID > 0) {

						Post p = PostDAO.getPostByID(postID);

						String postTtl = null;
						if (request.getParameter("txtPostTtl") == null
								|| request.getParameter("txtPostTtl").trim() == "") {
							postTtl = p.getPostName();
						} else {
							postTtl = request.getParameter("txtPostTtl");
						}
						String postContent = null;
						if (request.getParameter("tarPostContentBody") == null
								|| request.getParameter("tarPostContentBody")
										.trim() == "") {
							postContent = p.getContent();
						} else {
							postContent = request
									.getParameter("tarPostContentBody");
						}
						String postDesc = null;
						if (request.getParameter("tarPostDesc") == null
								|| request.getParameter("tarPostDesc").trim() == "") {
							postDesc = p.getPostDesc();
						} else {
							postDesc = request.getParameter("tarPostDesc");
						}
						int catID = -1;
						if (request.getParameter("rdCategory") == null
								|| request.getParameter("rdCategory").trim() == "") {
							catID = p.getCatID();
						} else {
							catID = Integer.parseInt(request.getParameter(
									"rdCategory").toString());
						}
						String illPost = null;
						if (request.getParameter("illPostName") == null
								|| request.getParameter("illPostName").trim() == "") {
							illPost = p.getIllustration();
						} else {
							illPost = request.getParameter("illPostName")
									.toString().trim();
						}

						String postSlug = Utilities.toUrlFriendly(postTtl)
								+ ".html";
						
						int userID = u.getUserID();
						Date postOn = new Date();
						Post pTmp = new Post();
						pTmp.setPostID(postID);
						pTmp.setPostName(postTtl);
						pTmp.setCatID(catID);
						pTmp.setContent(postContent);
						pTmp.setUserID(userID);
						pTmp.setPostDesc(postDesc);
						pTmp.setPostOn(postOn);
						pTmp.setPostSlug(postSlug);
						pTmp.setIllustration(illPost);
						int affectedRows = PostDAO.updatePost(pTmp);
						if (affectedRows == 1) {
							request.setAttribute("messages", "successfully");
						} else {
							request.setAttribute("messages", "error");
						}

						Post post = PostDAO.getPostByID(postID);
						request.setAttribute("post", post);
						ArrayList<Category> cateList = CategoryDAO
								.getCategories();
						request.setAttribute("cateList", cateList);
						request.getRequestDispatcher("/admin/post.jsp?pid="+postID)
								.forward(request, response);
					}else{
						response.sendError(404);
					}
				} else {
					response.sendError(404);
				}
			}
		}
	}

}
