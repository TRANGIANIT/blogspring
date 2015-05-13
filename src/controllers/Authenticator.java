package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.UserDAO;
import models.POJO.User;

/**
 * Servlet implementation class Authenticator
 */
// @WebServlet("/Authenticator")
public class Authenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authenticator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("txtEmail");
		String passWord = request.getParameter("txtPassWord");
		boolean err = false;
		if(email==null || email.trim()==""){
			err =true;
		}else{
			request.setAttribute("email", email);
		}
		if(email==null || passWord.trim() ==""){
			err =true;
		}else{
			request.setAttribute("passWord", passWord);
		}
		if (!err) {
			User user = UserDAO.login(email, passWord);
			if (user != null) {
				String from = request.getParameter("from");
				if(from==null){
					from="";
				}
				HttpSession session = request.getSession();
				
				session.setAttribute("user", user);
				response.sendRedirect(from);
			} else {
				request.setAttribute("error", "Lỗi đăng nhập, vui lòng thử lại!");
				RequestDispatcher dispatcher = request.getRequestDispatcher("login");
				dispatcher.forward(request, response);
			}
		}else{
			request.setAttribute("p", "yes");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login");
			dispatcher.forward(request, response);
		}
	}

}
