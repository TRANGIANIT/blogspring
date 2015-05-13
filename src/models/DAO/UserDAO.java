package models.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.POJO.User;
import models.UTILS.DatabaseUtils;

public class UserDAO {
	
	public static int getNumUsers() {
		int nu = 0;
		String sqlCommand = "SELECT count(user_id) FROM users";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				nu = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nu;
	}
	
	public static User login(String email, String passWord) {
		User user = new User();
		try {
			String sqlCommand = "SELECT * FROM users WHERE email = '" + email
					+ "' AND pass = '" + passWord + "' LIMIT 1";
			ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
			if(rs.last()){
				user.setUserID(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassWord(rs.getString(5));
				user.setWebsite(rs.getString(6));
				user.setYahoo(rs.getString(7));
				user.setFacebook(rs.getString(8));
				user.setBio(rs.getString(9));
				user.setAvatar(rs.getString(10));
				user.setUserLevel(rs.getByte(11));
				user.setActive(rs.getString(12));
				user.setRegistrationDate(rs.getDate(13));
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static ArrayList<User> getUsersList() {
			ArrayList<User> users = new ArrayList<User>();
			String sqlCommand = "SELECT * FROM users";
			ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
			try {
				while(rs.next()){
					User user = new User();
					user.setUserID(rs.getInt(1));
					user.setFirstName(rs.getString(2));
					user.setLastName(rs.getString(3));
					user.setEmail(rs.getString(4));
					user.setPassWord(rs.getString(5));
					user.setWebsite(rs.getString(6));
					user.setYahoo(rs.getString(7));
					user.setFacebook(rs.getString(8));
					user.setBio(rs.getString(9));
					user.setAvatar(rs.getString(10));
					user.setUserLevel(rs.getByte(11));
					user.setActive(rs.getString(12));
					user.setRegistrationDate(rs.getDate(13));
					users.add(user);
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return users;
	}
	
	public static String getUserNameByID(int userID){
		String sqlCommand = "SELECT first_name, last_name FROM users WHERE user_id = "+userID+" LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		String userName =null;
		try {
			rs.last();
			userName = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userName;
		
	}
	
	public static User getUserByID(int userID){
		String sqlCommand = "SELECT * FROM users WHERE user_id = "+userID+" LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		User user =new User();
		try {
			rs.last();
			user.setUserID(rs.getInt(1));
			user.setFirstName(rs.getString(2));
			user.setLastName(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setPassWord(rs.getString(5));
			user.setWebsite(rs.getString(6));
			user.setYahoo(rs.getString(7));
			user.setFacebook(rs.getString(8));
			user.setBio(rs.getString(9));
			user.setAvatar(rs.getString(10));
			user.setUserLevel(rs.getByte(11));
			user.setActive(rs.getString(12));
			user.setRegistrationDate(rs.getDate(13));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	
	public static User getUserByPostID(int postID){
		String sqlCommand = "SELECT * FROM users, posts WHERE users.user_id = posts.user_id AND post_id = "+postID+" LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		User user =new User();
		try {
			rs.last();
			user.setUserID(rs.getInt(1));
			user.setFirstName(rs.getString(2));
			user.setLastName(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setPassWord(rs.getString(5));
			user.setWebsite(rs.getString(6));
			user.setYahoo(rs.getString(7));
			user.setFacebook(rs.getString(8));
			user.setBio(rs.getString(9));
			user.setAvatar(rs.getString(10));
			user.setUserLevel(rs.getByte(11));
			user.setActive(rs.getString(12));
			user.setRegistrationDate(rs.getDate(13));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	
	public static boolean isAdmin(String email, String pass){
		String sqlCommand = "SELECT user_level FROM users WHERE email ='"+email+"' AND pass='"+pass+"'";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		boolean admin = false;
		try {
			if(rs.last()){
				if(rs.getInt(1)==3){
					admin=true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	public static void main(String[] args) {
	  }
	
}
