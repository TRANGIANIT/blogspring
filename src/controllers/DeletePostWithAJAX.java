package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DAO.CommentDAO;
import models.DAO.PostDAO;

/**
 * Servlet implementation class DeletePostWithAJAX
 */
@WebServlet("/DeletePostWithAJAX")
public class DeletePostWithAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePostWithAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("postID")!=null){
			int postID = Integer.parseInt(request.getParameter("postID"));
			int affectedRows = PostDAO.delPostByID(postID);
			if(affectedRows == 1){
				response.getWriter().print("success");
			}else{
				response.getWriter().print("error");
			}
		}
	}

}
