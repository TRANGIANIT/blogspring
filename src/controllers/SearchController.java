package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DAO.CategoryDAO;
import models.DAO.PostDAO;
import models.POJO.Category;
import models.POJO.Post;

/**
 * Servlet implementation class SearchContoller
 */
//@WebServlet("/SearchContoller")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String q = request.getParameter("q");
		if(q==null){
			response.sendRedirect("");
		}else{
			q = q.trim();
			int totalPosts = PostDAO.getNumPostsBySearchQuestion(q);
			// so Post tren 1 trang
			int numPostsOnPage = Integer.parseInt(request.getServletContext()
					.getInitParameter("numPostsOnPage").toString());
			// trang hien tai mac dinh la 1 neu khong co gia tri
			int currentPage = 1;
			if (request.getParameter("p") != null) {
				currentPage = Integer
						.parseInt(request.getParameter("p").toString());
			}
			// mac dinh post bat dau de lay ra la 0 <=> dang o trang 1
			int startPost = 0;
			// tinh post bat dau neu khong phai trang 1
			startPost = (currentPage - 1) * numPostsOnPage;
			ArrayList<Post> listPost = new ArrayList<Post>();
			listPost = PostDAO.searchPost(startPost, numPostsOnPage, q);
			int numPages = (int) (totalPosts - numPostsOnPage / 2) / numPostsOnPage
					+ 1;
			// Gan vao attribute de chuyen cho trang index
			request.setAttribute("listPost", listPost);
			request.setAttribute("numPages", numPages);
			request.setAttribute("add", request.getRequestURI());

			// Lay random Post hien thi len sidebar
			// So luong so luong random post se lay
			int numRandPosts = Integer.parseInt(request.getServletContext().getInitParameter(
					"numRandPosts").toString());
			ArrayList<Post> listRandPost = new ArrayList<Post>();
			listRandPost = PostDAO.getRandPost(numRandPosts);
			// Gan vao attribute de chuyen cho trang index
			request.setAttribute("listRandPost", listRandPost);

			ArrayList<Category> cate = new ArrayList<Category>();
			cate = CategoryDAO.getCategories();
			request.setAttribute("categories", cate);
			request.setAttribute("totalPosts", totalPosts);
			request.getRequestDispatcher("//result").forward(request,
					response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
