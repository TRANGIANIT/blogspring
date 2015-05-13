package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DAO.CategoryDAO;
import models.DAO.OptionDAO;
import models.DAO.PostDAO;
import models.POJO.Category;
import models.POJO.Post;

/**
 * Servlet implementation class ViewPosts
 */
//@WebServlet("/ViewPosts")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public View() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//load post option:
		String loadOption = OptionDAO.getOptionByName("load_more_posts").trim();
		ArrayList<Post> listPost = new ArrayList<Post>();
		request.setAttribute("loadOption", loadOption);
		if(loadOption.equals("auto")){
			// so Post tren 1 trang
			int numPostsOnPage = Integer.parseInt(OptionDAO.getOptionByName("posts_per_load").trim());
			listPost = PostDAO.getListPost(0, numPostsOnPage);
			//Gan vao attribute de chuyen cho trang index
			request.setAttribute("listPost", listPost);
		}else{
			// so luong post trong CSDL
			int totalPosts = PostDAO.getNumPosts();
			// so Post tren 1 trang
			int numPostsOnPage = Integer.parseInt(OptionDAO.getOptionByName("posts_per_page").trim());
			// trang hien tai mac dinh la 1 neu khong co gia tri
			int currentPage = 1;
			if (request.getParameter("p") != null) {
				currentPage = Integer
						.parseInt(request.getParameter("p").toString());
			}
			//mac dinh post bat dau de lay ra la 0 <=> dang o trang 1
			int startPost = 0;
			//tinh post bat dau neu khong phai trang 1
			startPost = (currentPage-1)*numPostsOnPage;
			listPost = PostDAO.getListPost(startPost, numPostsOnPage);
			int numPages = (int) Math.ceil(Math.ceil((totalPosts-numPostsOnPage/2))/numPostsOnPage + 1);
			//Gan vao attribute de chuyen cho trang index
			request.setAttribute("listPost", listPost);
			request.setAttribute("numPages", numPages);
		}
		//Lay random Post hien thi len sidebar
		//So luong so luong random post se lay
		ServletConfig config = this.getServletConfig();
		int numRandPosts = Integer.parseInt(config.getInitParameter(
				"numRandPosts").toString());
		ArrayList<Post> listRandPost = new ArrayList<Post>();
		listRandPost=PostDAO.getRandPost(numRandPosts);
		//Gan vao attribute de chuyen cho trang index
		request.setAttribute("listRandPost", listRandPost);
		
		ArrayList<Category> cate = new ArrayList<Category>();
		cate = CategoryDAO.getCategories();
		request.setAttribute("categories", cate);
		
		//Lay ten trang va slogan
		String blogName = OptionDAO.getOptionByName("blog_name");
		request.setAttribute("blogName", blogName);
		String blogDesc = OptionDAO.getOptionByName("blog_desc");
		request.setAttribute("blogDesc", blogDesc);
		request.getRequestDispatcher("index").forward(request, response);
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
