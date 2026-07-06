package days02;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author inseok  
 * @date 2026. 7. 6. 오후 5:19:37
 * @subject  삭제할 부서번호를 입력받아서 부서를 삭제..
 * @content
 */
public class Ex04_03 {

	public static void main(String[] args) {
		// 1. 
		Scanner scanner = new Scanner(System.in);
		int deptno ;

		System.out.print("> 삭제할 부서번호 입력 ? ");		
		deptno = scanner.nextInt(); 

		// 2. 
		Connection conn = null;
		Statement  stmt = null;

		conn = DBConn.getConnection();

		String sql = String.format(
				          "DELETE FROM dept "
						+ " WHERE deptno = %d"
						, deptno);

		int rowCount = 0;
		try {
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);   

			if (rowCount == 1) {
				System.out.println("🤩 부서 삭제 성공!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				stmt.close(); 
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 

		System.out.println(" end ");

	} // main

} // class
