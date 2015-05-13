package models.UTILS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
	private static Connection conn = null;
	private static Statement stm = null;

	
	public static boolean initialize() {
		ConnectionPoolImpl cp = new ConnectionPoolImpl();
		try {
			conn = cp.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn == null) {
			throw new NullPointerException("Connection object is null");
		}
		return true;
	}
	
	
	public static Connection getConnection() {
		initialize();
		return conn;
	}
	
	public static Statement getStatement() {
		initialize();
		createStatement();
		return stm;
	}

	private static void createStatement() {
		try {
			if (stm == null) {
				stm = conn.createStatement();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static ResultSet retrieveData(String sqlCommand) {
		initialize();
		createStatement();
		try {
			ResultSet resultSet = stm.executeQuery(sqlCommand);
			
			return resultSet;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static int executeInsert(String sqlCommand){
		initialize();
		createStatement();
		int effectedRows = 0;
		try {
			effectedRows = stm.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return effectedRows;
	}
	public static void main(String [] args){
	}
}
