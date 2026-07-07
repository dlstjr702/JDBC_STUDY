package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class Ex04_03 {

	public static void main(String[] args) {
		// 1. 
		Scanner scanner = new Scanner(System.in);
		int deptno ;

		System.out.print("> 삭제할 부서번호 입력 ? ");		
		deptno = scanner.nextInt(); 

		// 2. 
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = DBConn.getConnection();

		String sql = """
					DELETE FROM dept 
					WHERE deptno = ?
				""";

		int rowCount = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			rowCount = pstmt.executeUpdate();   

			if (rowCount == 1) {
				System.out.println("🤩 부서 삭제 성공!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				pstmt.close(); 
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 

		System.out.println(" end ");

	} // main

}
