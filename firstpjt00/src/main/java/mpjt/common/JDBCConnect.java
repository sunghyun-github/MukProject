package mpjt.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnect {
		
	public static Connection getConnection() {
		System.out.println("DB연결중!!");
		Connection conn = null;
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3307/mpjt?serverTimezone=UTC";
			String user = "root";
			String pw = "rpass";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("DB로그인 성공!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(PreparedStatement pstmt, Connection conn) {
		
		try {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
}
