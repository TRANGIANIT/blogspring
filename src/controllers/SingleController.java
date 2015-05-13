package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libraries.CommonLibrary;
import libraries.PostLibrary;
import models.DAO.CategoryDAO;
import models.DAO.CommentDAO;
import models.DAO.OptionDAO;
import models.DAO.PostDAO;
import models.DAO.UserDAO;
import models.DAO.ViewDAO;
import models.POJO.Category;
import models.POJO.Comment;
import models.POJO.Post;
import models.POJO.User;

/**
 * Servlet implementation class SingleController
 */
// @WebServlet("/SingleController")
public class SingleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingleController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String postSlug = CommonLibrary.getSlugByURL(request.getRequestURI());
		// Lay cat id cua post qua slug:
		int catID = PostDAO.getCategoyIDByPostSlug(postSlug);
		if (catID == -1) {
			response.sendError(404);
		} else {
			//update số lượt view page:
			ViewDAO.updatePostViews(PostDAO.getPostIDByPostSlug(postSlug));
			// lay post qua slug vua get duoc
			Post post = PostDAO.getPostBySlug(postSlug);
			request.setAttribute("post", post);
			//post title
			request.setAttribute("title", post.getPostName());
			
			// lay duong dan categories toi post:
			String cateStr = CategoryDAO.getParentCategoryStr(catID);
			if (!cateStr.equals(PostLibrary.getParentCategoryPostByURL(request
					.getRequestURI()))
					&& !cateStr.equals(PostLibrary
							.getParentCategoryPostByURL("single/"
									+ request.getRequestURI()))) {
				response.sendRedirect(request.getContextPath() + "/" + cateStr
						+ "/" + postSlug);
			} else {
				request.setAttribute("cateStr", cateStr);
				// Lay random Post hien thi len sidebar
				// So luong so luong random post se lay
				int numRandPosts = Integer.parseInt(request.getServletContext()
						.getInitParameter("numRandPosts").toString());
				ArrayList<Post> listRandPost = new ArrayList<Post>();
				listRandPost = PostDAO.getRandPost(numRandPosts);
				// Gan vao attribute de chuyen cho trang index
				request.setAttribute("listRandPost", listRandPost);

				ArrayList<Category> cate = new ArrayList<Category>();
				cate = CategoryDAO.getCategories();
				request.setAttribute("categories", cate);
				
				//Lay thong tin tac gia
				User author = UserDAO.getUserByPostID(PostDAO.getPostIDByPostSlug(postSlug));
				request.setAttribute("author", author);
				
				//Lay danh sach cac post lien quan
				ArrayList<Post> relatedPosts = new ArrayList<Post>();
				relatedPosts = PostDAO.getRelatedPosts(PostDAO.getPostIDByPostSlug(postSlug));
				request.setAttribute("relatedPosts", relatedPosts);
				
				//lay cac comment cua post theo postID
				ArrayList<Comment> comments = CommentDAO.getCommentByPostID(PostDAO.getPostIDByPostSlug(postSlug));
				request.setAttribute("comments", comments);
				
				//Lay thong tin nguoi dung neu da dang nhap
				//Lay ma luu trong session
				HttpSession session = request.getSession();
				if(session.getAttribute("user")!=null){
					User user = (User) session.getAttribute("user");
					request.setAttribute("user", user);
				}
				
				//Lay so luong comment:
				int numComments = CommentDAO.getNumCommentsByPostID(PostDAO.getPostIDByPostSlug(postSlug));
				request.setAttribute("numComments", numComments);
				
				//Lay ten trang va slogan
				String blogName = OptionDAO.getOptionByName("blog_name");
				request.setAttribute("blogName", blogName);
				String blogDesc = OptionDAO.getOptionByName("blog_desc");
				request.setAttribute("blogDesc", blogDesc);
				request.getRequestDispatcher(
						"//single/" + cateStr + "/" + postSlug).forward(
						request, response);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
