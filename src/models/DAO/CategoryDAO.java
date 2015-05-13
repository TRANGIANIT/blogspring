package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.POJO.Category;
import models.POJO.Post;
import models.UTILS.DatabaseUtils;

public class CategoryDAO {
	public static int getNumCategories() {
		int nc = 0;
		String sqlCommand = "SELECT count(cat_id) FROM categories";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				nc = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nc;
	}
	public static ArrayList<Category> getCategories() {
		ArrayList<Category> listCate = new ArrayList<Category>();
		String sqlCommand = "SELECT cat_id, cat_name, user_id, parent_cat, slug, cat_desc FROM categories";
		ResultSet cate = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (cate.next()) {
				Category c = new Category();
				c.setCatID(cate.getInt(1));
				c.setCatName(cate.getString(2));
				c.setUserID(cate.getInt(3));
				c.setParentID(cate.getInt(4));
				c.setSlug(cate.getString(5));
				c.setCatDesc(cate.getString(6));
				listCate.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<listCate.size(); i++){
			listCate.get(i).setNumPosts(CategoryDAO.getNumPostsByCatID(listCate.get(i).getCatID()));
			listCate.get(i).setParentName(CategoryDAO.getParentCatName(listCate.get(i).getCatID()));
		}
		return listCate;
	}

	public static String getCategoryNameByID(int catID) {
		String sqlCommand = "SELECT cat_name FROM categories WHERE cat_id = "
				+ catID + " LIMIT 1";
		ResultSet catName = DatabaseUtils.retrieveData(sqlCommand);
		String name = null;
		try {
			catName.last();
			if (catName.getRow() > 0) {
				name = catName.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	public static String getCategoryNameBySlug(String catSlug) {
		String sqlCommand = "SELECT cat_name FROM categories WHERE slug = '"
				+ catSlug + "' LIMIT 1";
		ResultSet catName = DatabaseUtils.retrieveData(sqlCommand);
		String name = null;
		try {
			catName.last();
			if (catName.getRow() > 0) {
				name = catName.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	private static String catNameStr = "";

	private static String getParentCategoryStrTmp(int catID) {
		String sqlCommand = "SELECT slug, parent_cat FROM categories WHERE cat_id = '"
				+ catID + "' LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				catNameStr += rs.getString(1);
				if (rs.getInt(2) != 0) {
					catNameStr += "/";
					return getParentCategoryStrTmp(rs.getInt(2));
				} else {
					return catNameStr;
				}
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getParentCategoryStr(int catID) {
		catNameStr = "";
		String str = getParentCategoryStrTmp(catID);
		if (str != null && str.length() > 0) {
			String strArr[] = str.split("/");
			str = "";
			for (int i = (strArr.length - 1); i >= 0; i--) {
				if (i > 0) {
					str += strArr[i] + "/";
				} else {
					str += strArr[i];
				}
			}
		}
		return str;
	}

	public static int addNewCategory(Category c) {
		int affectedRows = 0;
		String sqlCommand = "INSERT INTO categories(cat_name, user_id, parent_cat, slug, cat_desc) values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = DatabaseUtils.getConnection()
					.prepareStatement(sqlCommand);
			// bind values
			pstmt.setString(1, c.getCatName());
			pstmt.setInt(2, c.getUserID());
			if (c.getParentID() == -1) {
				pstmt.setString(3, null);
			} else {
				pstmt.setInt(3, c.getParentID());
			}
			pstmt.setString(4, c.getSlug());
			pstmt.setString(5, c.getCatDesc());
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows;
	}

	public static String getParentCatName(int catID) {
		String parentCat = null;
		String sqlCommand = "SELECT cat_name FROM categories WHERE cat_id = (SELECT parent_cat FROM categories WHERE cat_id = "
				+ catID + " LIMIT 1) LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if (rs.last()) {
				parentCat = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parentCat;
	}

	public static int getNumPostsByCatID(int catID) {
		int numPosts = 0;
		String sqlCommand = "SELECT count(post_id) FROM posts WHERE cat_id = "
				+ catID;
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if (rs.last()) {
				numPosts = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numPosts;
	}
	
	public static int delCategoryByID(int catID){
		String sqlCommand = "DELETE FROM categories WHERE cat_id=? LIMIT 1";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setInt(1, catID);
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public static Category getCategoryByID(int catID){
		String sqlCommand = "SELECT cat_id, cat_name, user_id, parent_cat, slug, cat_desc FROM categories WHERE cat_id = "+catID+" LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		Category c = new Category();
		try {
			if(rs.last()){
				c.setCatID(rs.getInt(1));
				c.setCatName(rs.getString(2));
				c.setUserID(rs.getInt(3));
				c.setParentID(rs.getInt(4));
				c.setSlug(rs.getString(5));
				c.setCatDesc(rs.getString(6));
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public static int updateCategory(Category c){
		String sqlCommand = "UPDATE Categories SET cat_name=?, user_id=?, parent_cat=?, slug = ?, cat_desc = ? WHERE cat_id =? LIMIT 1";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setString(1, c.getCatName());
			pstm.setInt(2, c.getUserID());
			if (c.getParentID() == -1) {
				pstm.setString(3, null);
			} else {
				pstm.setInt(3, c.getParentID());
			}
			pstm.setString(4, c.getSlug());
			pstm.setString(5, c.getCatDesc());
			pstm.setInt(6, c.getCatID());
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void main(String[] args) {
		// System.out.println(CategoryDAO.getParentCategoryStr(7));
	}
}
