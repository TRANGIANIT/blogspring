package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.UTILS.DatabaseUtils;

public class CounterDAO {
	public static int increaseHitCounter(String scope){
		String sqlCommand = "UPDATE counter SET num_visits = num_visits+1 WHERE scope = ?";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setString(1, scope);
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static int getHitCounter(String scope){
		String sqlCommand = "SELECT num_visits WHERE scope = '"+scope+"'";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		int num = 0;
		try {
			if(rs.last()){
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
}
