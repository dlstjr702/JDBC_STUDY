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


public class Ex05_02 {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.printf("> 중복체크할 ID(empno)를 입력?");
		int id = scanner.nextInt();
		
		//UP_IDCHECK
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		
		String sql = "{ CALL UP_IDCHECK(?) }";

		try {
		    conn = DBConn.getConnection();

		    cstmt = conn.prepareCall(sql);

		    cstmt.setInt(1, id);

		    //cstmt.executeQuery();
		    cstmt.execute(); //boolean 저장 프로시저 실행

		    System.out.println("사용가능한 아이디입니다.");

		} catch (SQLException e) {
			
			if (e.getErrorCode()==20001) {
				System.out.println("이미 사용중인 ID입니다.");				
			} else {
				//그외 예외처리 구문추가
			}
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
