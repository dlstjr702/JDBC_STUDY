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


public class Ex06_02 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.printf("> 로그인 체클할 ID(empno), PWD(ename)를 입력 ?");
		int id = scanner.nextInt();
		String pwd = scanner.next();

		//UP_LOGIN

		Connection conn = null;
		CallableStatement cstmt = null;

		String sql = "{ CALL UP_LOGIN(?,?)  }";

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			//IN?
			cstmt.setInt(1, id);
			//IN?
			cstmt.setString(2, pwd);
		
			cstmt.execute();

			System.out.println("로그인성공!!!");
			
			
		} catch (SQLException e) {
			
			if (e.getErrorCode()==20001) {
				System.out.println("아이디가 존재하지 않습니다.");				
			} else if(e.getErrorCode()==20002) {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}else {
				//그 외 예외 처리 구현...
				e.printStackTrace();
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
