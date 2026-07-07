package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author inseok
 * @date 2026. 7. 6.오후 4:29:22
 * @subject Statement 
 * @content PreparedStatement
 * 			CallabledStatement 저장 프로시저, 저장함수 사용할일없음
 */


public class Ex05 {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.printf("> 중복체크할 ID(empno)를 입력?");
		int id = scanner.nextInt();
		
		//UP_IDCHECK
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		String sql = "{ CALL UP_IDCHECK(pid=>?,pcheck=>?)  }";
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			//IN?
			cstmt.setInt(1, id);
			
			//OUT?
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeQuery();
			int check = cstmt.getInt(2); // 실행 후에 출력용 파라미터 값을 얻어와서 변수에 저장.
			
			if (check == 1) {
				System.out.println("이미 사용중인ID(empno)입니다.");
			} else {
				System.out.println("사용가능한ID(empno)입니다.");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				cstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}//main

}//class
