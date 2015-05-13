package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import models.POJO.Post;
import models.UTILS.DatabaseUtils;

public class PostDAO {
	// Lay tong so post co trong CSDL
	public static int getNumPostsByCategorySlug(String catSlug) {
		int np = 0;
		String sqlCommand = "SELECT count(post_id) FROM posts";
		if (catSlug != null) {
			sqlCommand += " , categories WHERE categories.cat_id=posts.cat_id AND slug = '"
					+ catSlug + "'";
		}
		ResultSet numPosts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if (numPosts.last()) {
				np = numPosts.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			   
		return np;
	}

	public static int getNumPosts() {
		return getNumPostsByCategorySlug(null);
	}
	//Lay so luong post tim duoc khi search
	public static int getNumPostsBySearchQuestion(String q) {
		int np = 0;
		String sqlCommand = "SELECT count(post_id) FROM posts";
		if (q != null) {
			sqlCommand += " WHERE post_name LIKE '%"+q+"%' OR content LIKE '%"+q+"%'";
		}
		ResultSet numPosts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if (numPosts.last()) {
				np = numPosts.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return np;
	}
	
	public static int getNumPostsByFilter(int catID, int month, int year) {
		int numPosts = 0;
		String moreCat = "";
		String moreMonth = "";
		String moreYear = "";
		if(catID==0){
			moreCat = " OR 1 = 1 ";
		}
		if(month==0){
			moreMonth = " OR 1 = 1 ";
		}
		if(year==0){
			moreYear = " OR 1 = 1 ";
		}
		String sqlCommand = "SELECT count(post_id) FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE (cat_id="+ catID+moreCat+ ") AND (MONTH(post_on)="+month+moreMonth+") AND (YEAR(post_on)="+year+moreYear+")";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if(rs.last()){
				numPosts = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numPosts;
	}
	
	public static int getPostIDByPostSlug(String postSlug){
		int catID=-1;
		String sqlCommand = "SELECT post_id FROM posts WHERE post_slug = '"+postSlug+"' LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			rs.last();
			if(rs.getRow()>0){
				catID = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catID;
	}
	public static int getCategoyIDByPostID(int postID){
		int catID = -1;
		String sqlCommand = "SELECT cat_id FROM posts WHERE post_id = '"+postID+"' LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			rs.last();
			if(rs.getRow()>0){
				catID = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catID;
		
	}
	public static int getCategoyIDByPostSlug(String postSlug){
		int postID = getPostIDByPostSlug(postSlug);
		return getCategoyIDByPostID(postID);
	}
	// Lay tat ca cac post co trong CSDL
	public static ArrayList<Post> getListPost() {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) ORDER BY post_on DESC, post_name ASC";
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}

	// Lay so cac post theo vi tri bat dau lay toi vi tri ket thuc xac dinh theo
	// so luong post tren 1 trang(trong phan trang)
	public static ArrayList<Post> getListPost(int startPost, int numPostsOnPage) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) ORDER BY post_on DESC, post_name ASC LIMIT "
				+ startPost + ", " + numPostsOnPage;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}

	public static ArrayList<Post> getListPost(int startPost,
			int numPostsOnPage, String catSlug) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE slug='"
				+ catSlug
				+ "' OR parent_cat=(SELECT cat_id FROM categories WHERE slug='"
				+ catSlug
				+ "') ORDER BY post_on DESC, post_name ASC LIMIT "
				+ startPost
				+ ", "
				+ numPostsOnPage;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	//Lay danh sach Post theo dieu kien lọc là tháng năm và cat_id
	public static ArrayList<Post> getListPost(int startPost,
			int numPostsOnPage, int catID, int month, int year) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String moreCat = "";
		String moreMonth = "";
		String moreYear = "";
		if(catID==0){
			moreCat = " OR 1 = 1 ";
		}
		if(month==0){
			moreMonth = " OR 1 = 1 ";
		}
		if(year==0){
			moreYear = " OR 1 = 1 ";
		}
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE (cat_id="+ catID+moreCat+ ") AND (MONTH(post_on)="+month+moreMonth+") AND (YEAR(post_on)="+year+moreYear+") ORDER BY post_on DESC, post_name ASC LIMIT "	+ startPost	+ ", "+ numPostsOnPage;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	public static ArrayList<Post> getListPost(int startPost,
			int numPostsOnPage, int catID, int month, int year, String orderby, String order) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String moreCat = "";
		String moreMonth = "";
		String moreYear = "";
		String ob = " post_on ";
		String o = "DESC";
		if(orderby!=null && !orderby.equals("")){
			ob = orderby;
		}
		if(order!=null && !order.equals("")){
			o = order;
		}
		if(catID==0){
			moreCat = " OR 1 = 1 ";
		}
		if(month==0){
			moreMonth = " OR 1 = 1 ";
		}
		if(year==0){
			moreYear = " OR 1 = 1 ";
		}
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE (cat_id="+ catID+moreCat+ ") AND (MONTH(post_on)="+month+moreMonth+") AND (YEAR(post_on)="+year+moreYear+") ORDER BY "+ob+" "+o+", post_name ASC LIMIT "	+ startPost	+ ", "+ numPostsOnPage;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	// phuong thuc lay ngau nhien 1 so luong posts
	public static ArrayList<Post> getRandPost(int numPosts) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) ORDER BY  rand() LIMIT "
				+ numPosts;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setCatName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}

	// Lay ngau nhien 10 post
	public static ArrayList<Post> getRandPost() {
		int numPosts = 10;
		return getRandPost(numPosts);
	}

	public static ArrayList<Post> getPostsByCategorySlug(String catSlug) {
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE slug = '"
				+ catSlug
				+ "' OR posts.cat_id=(SELECT parent_cat FROM categories WHERE slug='"
				+ catSlug + "') ORDER BY  post_on, cat_name DESC";
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setCatName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	
	public static ArrayList<Post> searchPost(String q){
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE post_name LIKE '%"+q+"%' OR content LIKE '%"+q+"%' ORDER BY  post_on, cat_name DESC";
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	public static ArrayList<Post> searchPost(int startPost,
			int numPostsOnPage, String q){
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE post_name LIKE '%"+q+"%' OR content LIKE '%"+q+"%' ORDER BY  post_on, cat_name DESC  LIMIT "+ startPost+ ", "+ numPostsOnPage;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	
	public static Post getPostBySlug(String postSlug){
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE post_slug =  '"+postSlug+"' LIMIT 1";
		ResultSet post = DatabaseUtils.retrieveData(sqlCommand);
		Post p = new Post();
		try {
				post.last();
				p.setPostID(post.getInt(1));
				p.setUserID(post.getInt(2));
				p.setUserName(post.getString(3));
				p.setCatID(post.getInt(4));
				p.setCatName(post.getString(5));
				p.setPostName(post.getString(6));
				p.setContent(post.getString(7));
				p.setPostOn(post.getDate(8));
				p.setPostSlug(post.getString(9));
				p.setIllustration(post.getString(10));
				p.setPostDesc(post.getString(11));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(post!=null){
			p.setCatePostStr(CategoryDAO.getParentCategoryStr(p.getCatID()));
			p.setNumComments(CommentDAO.getNumCommentsByPostID(p.getCatID()));
		}
		return p;
	}
	
	public static Post getPostByID(int postID){
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) WHERE post_id =  "+postID+" LIMIT 1";
		ResultSet post = DatabaseUtils.retrieveData(sqlCommand);
		Post p = new Post();
		try {
			if(post.last()){
				p.setPostID(post.getInt(1));
				p.setUserID(post.getInt(2));
				p.setUserName(post.getString(3));
				p.setCatID(post.getInt(4));
				p.setCatName(post.getString(5));
				p.setPostName(post.getString(6));
				p.setContent(post.getString(7));
				p.setPostOn(post.getDate(8));
				p.setPostSlug(post.getString(9));
				p.setIllustration(post.getString(10));
				p.setPostDesc(post.getString(11));
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(post!=null){
			p.setCatePostStr(CategoryDAO.getParentCategoryStr(p.getCatID()));
			p.setNumComments(CommentDAO.getNumCommentsByPostID(p.getCatID()));
		}
		return p;
	}
	
	public static ArrayList<Post> getRelatedPosts(int postID){
		ArrayList<Post> listPost = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id)  WHERE cat_id=(SELECT cat_id FROM posts WHERE post_id = "+postID+" LIMIT 1) AND post_id !="+postID+" ORDER BY RAND() LIMIT 10";
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (posts.next()) {
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				listPost.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listPost.size(); i++){
			listPost.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(listPost.get(i).getCatID()));
			listPost.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(listPost.get(i).getCatID()));
		}
		return listPost;
	}
	public static ArrayList<Date> getAllMonthAndYearOfAllPost(){
		ArrayList<Date> listMnY = new ArrayList<Date>();
		String sqlCommand = "SELECT DISTINCT MONTH(post_on) as m, YEAR(post_on) as y FROM posts ORDER BY y ASC, m ASC";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				Calendar cal =  Calendar.getInstance();
				cal.clear();
				cal.set(Calendar.MONTH, rs.getInt(1)-1);
				cal.set(Calendar.YEAR, rs.getInt(2));
				Date date = cal.getTime();
				listMnY.add(date);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMnY;
	}
	
	public static int addNewPost(Post p){
		int affectedRows = 0;
		String sqlCommand = "INSERT INTO posts(user_id, cat_id, post_name, content,  post_on, post_slug, illustration, post_desc) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = DatabaseUtils.getConnection().prepareStatement(sqlCommand);
			//bind values
			pstmt.setInt(1, p.getUserID());
			pstmt.setInt(2, p.getCatID());
			pstmt.setString(3, p.getPostName());
			pstmt.setString(4, p.getContent());
			pstmt.setTimestamp(5, new java.sql.Timestamp(p.getPostOn().getTime()));
			pstmt.setString(6, p.getPostSlug());
			pstmt.setString(7, p.getIllustration());
			pstmt.setString(8, p.getPostDesc());
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows;
	}
	
	public static ArrayList<Post> getPopularPostByView(int numPost){
		ArrayList<Post> popPosts = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id)  INNER JOIN views USING(post_id) ORDER BY num_views DESC LIMIT "+numPost;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while(posts.next()){
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				popPosts.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<popPosts.size(); i++){
			popPosts.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(popPosts.get(i).getCatID()));
			popPosts.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(popPosts.get(i).getCatID()));
		}
		return popPosts;
	}
	
	public static ArrayList<Post> getPopularPostByComment(int numPost){
		ArrayList<Post> popPosts = new ArrayList<Post>();
		String sqlCommand = "SELECT post_id, posts.user_id, first_name, posts.cat_id, cat_name, post_name, content, post_on, post_slug, illustration, post_desc FROM posts INNER JOIN users USING(user_id) INNER JOIN categories USING(cat_id) INNER JOIN comments USING(post_id) GROUP BY post_id ORDER BY COUNT(post_id) DESC LIMIT "+numPost;
		ResultSet posts = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while(posts.next()){
				Post p = new Post();
				p.setPostID(posts.getInt(1));
				p.setUserID(posts.getInt(2));
				p.setUserName(posts.getString(3));
				p.setCatID(posts.getInt(4));
				p.setCatName(posts.getString(5));
				p.setPostName(posts.getString(6));
				p.setContent(posts.getString(7));
				p.setPostOn(posts.getDate(8));
				p.setPostSlug(posts.getString(9));
				p.setIllustration(posts.getString(10));
				p.setPostDesc(posts.getString(11));
				popPosts.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<popPosts.size(); i++){
			popPosts.get(i).setCatePostStr(CategoryDAO.getParentCategoryStr(popPosts.get(i).getCatID()));
			popPosts.get(i).setNumComments(CommentDAO.getNumCommentsByPostID(popPosts.get(i).getCatID()));
		}
		return popPosts;
	}
	public static int delPostByID(int postID){
		String sqlCommand = "DELETE FROM posts WHERE post_id=? LIMIT 1";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setInt(1, postID);
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static int updatePost(Post p){
		String sqlCommand = "UPDATE posts SET user_id=?, cat_id=?, post_name=?, content = ?, post_on = ?, post_slug = ?, illustration = ?, post_desc = ? WHERE post_id =? LIMIT 1";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setInt(1, p.getUserID());
			pstm.setInt(2, p.getCatID());
			pstm.setString(3, p.getPostName());
			pstm.setString(4, p.getContent());
			pstm.setTimestamp(5, new java.sql.Timestamp(p.getPostOn().getTime()));
			pstm.setString(6, p.getPostSlug());
			pstm.setString(7, p.getIllustration());
			pstm.setString(8, p.getPostDesc());
			pstm.setInt(9, p.getPostID());
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static void main(String [] args){
	}
}
