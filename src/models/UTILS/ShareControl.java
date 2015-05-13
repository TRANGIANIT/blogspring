package models.UTILS;

public interface ShareControl {
	// Truyen tham so bo quan ly ket noi
	public ConnectionPool getConnectionPool();

	// Yeu cau tat ca doi tuong tra lai ket noi
	public void releaseConnection();
}
