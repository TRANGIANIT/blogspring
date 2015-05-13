package models.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.POJO.Library;
import models.UTILS.DatabaseUtils;

public class LibraryDAO {
	public static ArrayList<Library> getImagesList(){
		ArrayList<Library> imgs = new ArrayList<Library>();
		String sqlCommand = "SELECT * FROM libraries";
		ResultSet rs = DatabaseUtils.retrieveData(sqlCommand);
		try {
			while(rs.next()){
				Library l = new Library();
				l.setImageID(rs.getInt(1));
				l.setImageName(rs.getString(2));
				l.setUserID(rs.getInt(3));
				l.setGuid(rs.getString(4));
				l.setMimeType(rs.getString(5));
				l.setUploadDate(rs.getDate(6));
				imgs.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgs;
	}
}
