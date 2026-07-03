package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author inseok
 * @date 2026. 7. 3.오전 9:12:05
 * @date
 * @subject
 * @content
 */


public class Ex02 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		/*
		 * Java 코드
			   ↓
			JDBC API
			   ↓
			Driver (Oracle JDBC Driver)
			   ↓
			Database (Oracle / MySQL)
		 *
		 * 
		 * 
		 * JDBC 동작순서
		 * 1) JDBC드라이버로딩 
		 * 2) DB연결 (OPEN) : DriverManager객체 ->  connection 생성
		 * 3) SQL작성 -> 실행 -> 결과처리 
		 * 	statement생성 -> resultset(결과처리)조회
		 * 4) DB닫기 (CLOSE)
		 * 
		 * 
		 * */
		
		
		//1) JDBC드라이버로딩 
		
		
		//Class.forName("oracle.jdbc.driver.OracleDriver"); -- 최근 드라이버에서는 생략가능한 경우가 많다
		
		//2) DB연결 (OPEN) : DriverManager객체 ->  connection 생성
		Connection conn = DriverManager.getConnection(
				 "jdbc:oracle:thin:@localhost:1521/XEPDB1",
				 "scott",
				 "tiger"
		);
		
		System.out.println(conn.isClosed()); // DB연결 O false
		System.out.println(conn); // DB연결 O false
		
		//4) DB닫기 (CLOSE)
		conn.close();
		
		System.out.println("end");
		
		
		// com.util.DBConn.java
		
		
	}

}
