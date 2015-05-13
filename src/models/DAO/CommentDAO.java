package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.POJO.Comment;
import models.UTILS.DatabaseUtils;

public class CommentDAO {
	public static int getNumComments() {
		int nc = 0;
		String sqlCommand = "SELECT count(comment_id) FROM comments";
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
	public static int getNumCommentsByPostID(int postID) {
		int nc = 0;
		String sqlCommand = "SELECT count(comment_id) FROM comments WHERE post_id = "
				+ postID;
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

	public static ArrayList<Comment> getCommentByPostID(int postID) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String sqlCommand = "SELECT comment_id, user_id, post_id, CONCAT_WS(' ', last_name, first_name), avatar, comment, comment_date FROM comments INNER JOIN users USING(user_id) WHERE post_id = "
				+ postID+" ORDER BY comment_date DESC";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				Comment c = new Comment();
				c.setCommentID(rs.getInt(1));
				c.setUserID(rs.getInt(2));
				c.setPostID(rs.getInt(3));
				c.setUserName(rs.getString(4));
				c.setUserAvatar(rs.getString(5));
				c.setComment(rs.getString(6));
				c.setCommentDate(rs.getTimestamp(7));
				comments.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	public static ArrayList<Comment> getCommentsList() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String sqlCommand = "SELECT comment_id, user_id, post_id, CONCAT_WS(' ', last_name, first_name), avatar, comment, comment_date FROM comments INNER JOIN users USING(user_id) ORDER BY comment_date DESC";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while (rs.next()) {
				Comment c = new Comment();
				c.setCommentID(rs.getInt(1));
				c.setUserID(rs.getInt(2));
				c.setPostID(rs.getInt(3));
				c.setUserName(rs.getString(4));
				c.setUserAvatar(rs.getString(5));
				c.setComment(rs.getString(6));
				c.setCommentDate(rs.getTimestamp(7));
				comments.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	public static int addComment(Comment aComment) {
		String sqlCommand = "INSERT INTO comments(user_id, post_id, comment, comment_date) VALUES(?, ?, ?, ?)";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setInt(1, aComment.getUserID());
			pstm.setInt(2, aComment.getPostID());
			pstm.setString(3, aComment.getComment());
			pstm.setTimestamp(4, new java.sql.Timestamp(aComment.getCommentDate().getTime()));
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static int delComment(int commentID) {
		String sqlCommand = "DELETE FROM comments WHERE comment_id = ? LIMIT 1";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setInt(1, commentID);
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
