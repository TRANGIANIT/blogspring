package models.UTILS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool {
	// Thong tin tai khoan
	private String username;
	private String userpass;
	// Thong tin trinh dieu khien
	private String driver;
	// Thong tin duong dan MYSQL
	private String url;
	// Doi tuong chua cac ket noi
	private Stack pool;

	public ConnectionPoolImpl() {
		Properties p = new Properties();
		try {
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("info.properties"));
			
			// Thiet lap thong tin tai khoan
			// Phai trung voi CSDL
			this.username = p.getProperty("USER");
			this.userpass = p.getProperty("PASSWORD");
			// Thong tin duong dan MySQL
			this.url =  p.getProperty("URL");
			// Thong tin chinh dieu khien
			this.driver = "com.mysql.jdbc.Driver";
			// Khoi tao luu tru
			this.pool = new Stack();
			// Lap trinh dieu khien
			this.loadDriver();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Phuong thuc lap trinh dieu khien
	private void loadDriver() {
		try {
			Class.forName(this.driver).newInstance();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		if (this.pool.isEmpty()) {
			return DriverManager.getConnection(this.url, this.username,
					this.userpass);
		} else {
			return (Connection) this.pool.pop();
		}

	}

	public void releaseConnection(Connection con, String objectName)
			throws SQLException {
		this.pool.push(con);
	}

	// Dua ra cho no
	// Nhung gi khoi tao trong contructor thi bi huy tai day
	protected void finallize() throws Throwable {
		this.username = null;
		this.userpass = null;
		this.url = null;
		this.pool.clear();
		this.pool = null;
		super.finalize();
	}
}
