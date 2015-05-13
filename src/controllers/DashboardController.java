package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.CategoryDAO;
import models.DAO.CommentDAO;
import models.DAO.PostDAO;
import models.DAO.UserDAO;
import models.POJO.Post;
import models.POJO.User;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Kiem tra admin
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.sendError(404);
		} else {
			User u = (User) session.getAttribute("user");
			if (u.getUserLevel() != 3) {
				response.sendError(404);
			} else {
				int numPosts = PostDAO.getNumPosts();
				request.setAttribute("numPosts", numPosts);
				int numComments = CommentDAO.getNumComments();
				request.setAttribute("numComments", numComments);
				int numUsers = UserDAO.getNumUsers();
				request.setAttribute("numUsers", numUsers);
				int numCategories = CategoryDAO.getNumCategories();
				request.setAttribute("numCategories", numCategories);
				request.setAttribute("numPages", 0);
				ArrayList<Post> popPostsView = PostDAO.getPopularPostByView(3);
				request.setAttribute("popPostsView", popPostsView);
				ArrayList<Post> popPostsComment = PostDAO.getPopularPostByComment(3);
				request.setAttribute("popPostsComment", popPostsComment);
				request.getRequestDispatcher("/admin/index-admin.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
