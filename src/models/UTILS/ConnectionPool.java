package models.UTILS;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
	// Chuc nang cung cap mot ket noi cho doi tuong
	// Hoi thi cho
	public Connection getConnection() throws SQLException;

	// Chuc nang de nghi tra lai ket noi
	// Tra lai
	public void releaseConnection(Connection con, String objectName)
			throws SQLException;
}
