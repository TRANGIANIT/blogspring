package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.CategoryDAO;
import models.POJO.User;

/**
 * Servlet implementation class DeleteCategoryWithAJAX
 */
@WebServlet("/DeleteCategoryWithAJAX")
public class DeleteCategoryWithAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCategoryWithAJAX() {
        super();
        // TODO Auto-generated constructor stub
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
				if(request.getParameter("catID")!=null){
					int catID = Integer.parseInt(request.getParameter("catID").toString().trim());
					int affectedRows = CategoryDAO.delCategoryByID(catID);
					if(affectedRows == 1){
						response.getWriter().print("success");
					}else{
						response.getWriter().print("error");
					}
				}
			}
		}
	}

}
