package models.DAO;

import java.sql.SQLException;

import models.UTILS.DatabaseUtils;

public class ViewDAO {
	public static boolean updatePostViews(int postID){
		boolean b = false;
		String sqlCommand = "INSERT INTO views VALUES("+postID+", 1) ON DUPLICATE KEY UPDATE num_views=num_views+1 ";
		try {
			b = DatabaseUtils.getStatement().execute(sqlCommand);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
