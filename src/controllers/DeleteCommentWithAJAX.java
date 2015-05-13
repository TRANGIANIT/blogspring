package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DAO.CommentDAO;

/**
 * Servlet implementation class DeleteCommentWithAJAX
 */
@WebServlet("/DeleteCommentWithAJAX")
public class DeleteCommentWithAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCommentWithAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("commentID")!=null){
			int commentID = Integer.parseInt(request.getParameter("commentID"));
			int affectedRows = CommentDAO.delComment(commentID);
			if(affectedRows == 1){
				response.getWriter().print("success");
			}else{
				response.getWriter().print("error");
			}
		}
		
	}

}
