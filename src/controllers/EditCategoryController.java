package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.CategoryDAO;
import models.DAO.PostDAO;
import models.POJO.Category;
import models.POJO.Post;
import models.POJO.User;

/**
 * Servlet implementation class EditCategoryController
 */
//@WebServlet("/EditCategoryController")
public class EditCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				String catIDTmp = (String) request.getParameter("cid").trim();
				if (catIDTmp != null && catIDTmp.matches("[+-]?\\d*(\\d+)?")) {
					int catID = Integer.parseInt(catIDTmp);
					if (catID > 0) {
						Category cate = CategoryDAO.getCategoryByID(catID);
						if(cate==null){
							response.sendError(404);
						}else{
							request.setAttribute("cate", cate);
							ArrayList<Category> cateList = CategoryDAO
									.getCategories();
							request.setAttribute("cateList", cateList);
							request.getRequestDispatcher("/admin/edit-category.jsp?cid="+catID)
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
				String catIDTmp = (String) request.getParameter("cid").trim();
				if (catIDTmp != null && catIDTmp.matches("[+-]?\\d*(\\d+)?")) {
					int catID = Integer.parseInt(catIDTmp);
					if (catID > 0) {
						Category c = CategoryDAO.getCategoryByID(catID);
						if(c==null){
							response.sendError(404);
						}else{
							String catName = null;
							if (request.getParameter("txtCatTtlEdit") == null
									|| request.getParameter("txtCatTtlEdit").trim() == "") {
								catName = c.getCatName();
							} else {
								catName = request.getParameter("txtCatTtlEdit");
							}
							
							String catSlug = null;
							if (request.getParameter("txtCatSlugEdit") == null
									|| request.getParameter("txtCatSlugEdit").trim() == "") {
								catSlug = c.getSlug();
							} else {
								catSlug = request.getParameter("txtCatSlugEdit");
							}
							
							String catDesc = null;
							if (request.getParameter("tarCatDescEdit") == null
									|| request.getParameter("tarCatDescEdit").trim() == "") {
								catDesc = c.getCatDesc();
							} else {
								catDesc = request.getParameter("tarCatDescEdit");
							}
							
							int catParent = -1;
							if (request.getParameter("cbbCatParentsEdit") == null
									|| request.getParameter("cbbCatParentsEdit").trim() == "" || !catIDTmp.matches("[+-]?\\d*(\\d+)?")) {
								if(c.getParentID() >0){
									catParent = c.getParentID();
								}else{
									catParent = -1;
								}
							} else {
								catParent = Integer.parseInt(request.getParameter("cbbCatParentsEdit"));
							}
							
							int userID = u.getUserID();
							
							Category cat = new Category();
							cat.setCatID(catID);
							cat.setCatName(catName);
							cat.setSlug(catSlug);
							cat.setCatDesc(catDesc);
							cat.setUserID(userID);
							cat.setParentID(catParent);
							
							int affectedRows = CategoryDAO.updateCategory(cat);
							
							if (affectedRows == 1) {
								request.setAttribute("messages", "successfully");
							} else {
								request.setAttribute("messages", "error");
							}
							
							Category category = CategoryDAO.getCategoryByID(catID);
							request.setAttribute("cate", category);
							ArrayList<Category> cateList = CategoryDAO
									.getCategories();
							request.setAttribute("cateList", cateList);
							request.getRequestDispatcher("/admin/edit-category.jsp?cid="+catID)
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

}
