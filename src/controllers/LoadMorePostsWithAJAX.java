package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DAO.PostDAO;
import models.POJO.Post;

/**
 * Servlet implementation class LoadMorePostsWithAJAX
 */
@WebServlet("/LoadMorePostsWithAJAX")
public class LoadMorePostsWithAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadMorePostsWithAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("numPosts")!=null){
		
			int numPosts = Integer.parseInt(request.getParameter("numPosts").toString().trim());
			int totalPosts = PostDAO.getNumPosts();
			if(numPosts < totalPosts){
				ArrayList<Post> listPost = PostDAO.getListPost(numPosts-1, 5);
				request.setAttribute("listPost", listPost);
				request.setAttribute("hasNext", true);
			}else{
				request.setAttribute("hasNext", false);
			}
			request.getRequestDispatcher("/processor/load_more_posts.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
