package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
 * Servlet implementation class ViewAllPostsAdmin
 */
// @WebServlet("/ViewAllPostsAdmin")
public class ViewAllPostsAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewAllPostsAdmin() {
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
				// Lay tham so truyen qua phương thức Get: catID và date để lọc
				// dữ
				// liệu:
				String catIDTmp = request.getParameter("catID");
				// kiểm tra nếu là số thì gán và biến
				int catID = 0;// mặc định là 0
				if (catIDTmp != null && catIDTmp.matches("[+-]?\\d*(\\d+)?")) {
					catID = Integer.parseInt(request.getParameter("catID")
							.toString());
				}
				// neu truyen vao catID là số 0 hoặc âm thì:
				if (catID < 1) {
					catID = 0;// mặc định là 0
				}
				String dateTmp = request.getParameter("date");
				String date = "";// mặc định là ""
				// kiem tra chuoi khong null va phu hop số không dấu
				if (dateTmp != null && dateTmp.matches("[01]?\\d*(\\d+)?")) {
					date = request.getParameter("date").toString();
				}
				// hai bien luu gia tri tháng và năm
				int month = 0;
				int year = 0;
				if (!date.equals("")) {
					String monthTmp = date.substring(0, 2);
					month = Integer.parseInt(monthTmp);
					String yearTmp = date.substring(2);
					year = Integer.parseInt(yearTmp);
				}
				String orderby = null;
				if (request.getParameter("orderby") != null
						&& request.getParameter("orderby").equals("post_on")) {
					orderby = "post_on";
				}
				String order = null;
				if (request.getParameter("order") != null
						&& (request.getParameter("order").equals("asc") || request
								.getParameter("order").equals("desc"))) {
					order = request.getParameter("order");
				}
				// so luong post trong CSDL
				int totalPosts = PostDAO
						.getNumPostsByFilter(catID, month, year);
				// so Post tren 1 trang
				int numPostsOnTable = Integer.parseInt(request
						.getServletContext()
						.getInitParameter("numPostsOnTableAdmin").toString());
				request.setAttribute("totalPosts", totalPosts);
				ArrayList<Post> listPost = new ArrayList<Post>();
				// trang hien tai mac dinh la 1 neu khong co gia tri
				int currentPage = 1;
				String str = request.getParameter("p");
				// kiểm tra nếu là số thì gán và biến
				if (str != null && str.matches("[+-]?\\d*(\\d+)?")) {
					currentPage = Integer.parseInt(request.getParameter("p")
							.toString());
				}
				// nếu giá trị nhập vào là số âm thì hoặc bằng 0:
				if (currentPage < 1) {
					currentPage = 1;
				}
				// mac dinh post bat dau de lay ra la 0 <=> dang o trang 1
				int startPost = 0;
				// tinh post bat dau neu khong phai trang 1
				startPost = (currentPage - 1) * numPostsOnTable;
				listPost = PostDAO.getListPost(startPost, numPostsOnTable,
						catID, month, year, orderby, order);
				int numPages = (int) Math.ceil((totalPosts - Math.ceil(numPostsOnTable / 2))/ numPostsOnTable + 1);
				// Gan vao attribute de chuyen cho trang index
				request.setAttribute("listPost", listPost);
				request.setAttribute("numPages", numPages);

				ArrayList<Category> cate = new ArrayList<Category>();
				cate = CategoryDAO.getCategories();
				request.setAttribute("categories", cate);

				// Lay danh sach thang nam cho bộ lọc
				ArrayList<Date> dateList = PostDAO
						.getAllMonthAndYearOfAllPost();
				request.setAttribute("dateList", dateList);

				request.getRequestDispatcher("/admin/view-all-posts.jsp")
						.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
