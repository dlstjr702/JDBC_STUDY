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


public class Ex06 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.printf("> 로그인 체클할 ID(empno), PWD(ename)를 입력 ?");
		int id = scanner.nextInt();
		String pwd = scanner.next();

		//UP_LOGIN

		Connection conn = null;
		CallableStatement cstmt = null;

		String sql = "{ CALL UP_LOGIN(?,?,?)  }";

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			//IN?
			cstmt.setInt(1, id);
			//IN?
			cstmt.setString(2, pwd);
			//OUT?
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.executeQuery();
			int check = cstmt.getInt(3); // 실행 후에 출력용 파라미터 값을 얻어와서 변수에 저장.

			if (check == 0) {
				System.out.println("로그인성공!!!");
			} else if(check == 1){
				System.out.println("로그인 실패!!! \n 아이디는 존재하지만 비밀번호가 잘못되었다");
			}else if(check == -1) {
				System.out.println("로그인 실패!!! \n 존재하지 않는 아이디입니다.");
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
