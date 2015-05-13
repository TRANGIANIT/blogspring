package controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.CommentDAO;
import models.POJO.Comment;
import models.POJO.User;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentController() {
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
	    HttpSession session = request.getSession();
		//Lay cac thong tin comment:
	    User user = (User) session.getAttribute("user");
		int postID = Integer.parseInt(request.getParameter("postid"));
		String comment = request.getParameter("comment");
		boolean err = false;
		if(comment==null || comment.trim()==""){
			err = true;
		}
		if(postID <= 0){
			err = true;
		}
		if(user == null){
			err = true;
		}
		if(!err){
			Comment c = new Comment();
			c.setUserID(user.getUserID());
			c.setPostID(postID);
			c.setComment(comment);
			Date d = new Date();
			c.setCommentDate(d);
			CommentDAO.addComment(c);
		}
		String from = request.getHeader("referer");
		if(from == null){
			from = "";
		}
		response.sendRedirect(from);
	}

}
