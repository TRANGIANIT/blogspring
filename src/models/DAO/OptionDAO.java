package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.POJO.Option;
import models.UTILS.DatabaseUtils;

public class OptionDAO {
	public static ArrayList<Option> getOptions(){
		ArrayList<Option> options = new ArrayList<Option>();
		String sqlCommand = "SELECT * FROM options ORDER BY option_id";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while(rs.next()){
				Option o = new Option();
				o.setOptionID(rs.getInt(1));
				o.setOptionName(rs.getString(2));
				o.setOptionValue(rs.getString(3));
				options.add(o);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return options;
	}
	public static String getOptionByName(String optionName){
		String optionValue = null;
		String sqlCommand = "SELECT option_value FROM options WHERE option_name = '"+optionName+"' LIMIT 1";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			if(rs.last()){
				optionValue = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionValue;
	}
	public static int changeOption(Option o) {
		String sqlCommand = "UPDATE options SET option_value = ? WHERE option_name = ?";
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			// bind values
			pstm.setString(1, o.getOptionValue());
			pstm.setString(2, o.getOptionName());
			// execute
			return pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static int changeOptionsList(ArrayList<Option> ol) {
		String sqlCommand = "UPDATE options SET option_value = ? WHERE option_name = ?";
		int affectedRows=0;
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sqlCommand);
			for(int i=0; i<ol.size(); i++){
				// bind values
				pstm.setString(1, ol.get(i).getOptionValue());
				pstm.setString(2, ol.get(i).getOptionName());
				// execute
				affectedRows+= pstm.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows;
	}
}
