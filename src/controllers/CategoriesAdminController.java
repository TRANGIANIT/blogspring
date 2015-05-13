package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DAO.CategoryDAO;
import models.POJO.Category;
import models.POJO.User;

/**
 * Servlet implementation class CategoriesController
 */
// @WebServlet("/CategoriesController")
public class CategoriesAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoriesAdminController() {
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
				// nếu chua submit thi thì chua kiem tra loi
				if (request.getParameter("txtCatTtl") == null
						&& request.getParameter("txtCatSlug") == null
						&& request.getParameter("tarPostDesc") == null
						&& request.getParameter("cbbCatParents") == null) {
					ArrayList<Category> cateList = CategoryDAO.getCategories();
					request.setAttribute("cateList", cateList);
					request.getRequestDispatcher("/admin/categories.jsp")
							.forward(request, response);
				} else {
					ArrayList<String> errors = new ArrayList<String>();
					String catName = null;
					if (request.getParameter("txtCatTtl") == null
							|| request.getParameter("txtCatTtl").trim() == "") {
						errors.add("name");
					} else {
						catName = request.getParameter("txtCatTtl");
					}
					String catSlug = null;
					if (request.getParameter("txtCatSlug") == null
							|| request.getParameter("txtCatSlug")
									.trim() == "") {
						errors.add("slug");
					} else {
						catSlug = request
								.getParameter("txtCatSlug");
					}
					String catDesc = null;
					if (request.getParameter("tarPostDesc") == null
							|| request.getParameter("tarPostDesc").trim() == "") {
						catDesc = null;
					} else {
						catDesc = request.getParameter("tarPostDesc");
					}
					int parentCat = -1;
					if (request.getParameter("cbbCatParents") == null
							|| request.getParameter("cbbCatParents").trim() == "") {
						parentCat = -1;
					} else {
						parentCat = Integer.parseInt(request.getParameter(
								"cbbCatParents").toString());
					}
					if (errors.size() == 0) {
						int userID = u.getUserID();;
						Category c = new Category();
						c.setCatName(catName);
						c.setSlug(catSlug);
						c.setParentID(parentCat);
						c.setUserID(userID);
						c.setCatDesc(catDesc);
						int affectedRows = CategoryDAO.addNewCategory(c);
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
					request.getRequestDispatcher("/admin/categories.jsp")
							.forward(request, response);
				}
			}
		}
	}

}
