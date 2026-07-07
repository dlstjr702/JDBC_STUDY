package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author inseok
 * @date 2026. 7. 6.오후 4:29:22
 * @subject CRUD
 * @content 부서 테이블 조회 days01.Ex04.java
 * 			부서 테이블 추가 deptno, dname, loc
 * 			부서 테이블 검색 days02.Ex04_02.java
 * 			부서 테이블 수정
 * 			부서 테이블 삭제 days02.Ex04_03.java
 */


public class Ex04 {

	public static void main(String[] args) {
		//1.
		Scanner scanner = new Scanner(System.in);
		String dname, loc;


		System.out.print("> 부서명 , 지역명 입력 ?");
		dname = scanner.next();
		loc = scanner.next();
		
		//2.
		Connection conn = null;
		PreparedStatement pstmt = null;
	    conn = DBConn.getConnection();
		
	    String sql =
	    		
	    		"""
	    		INSERT INTO dept (deptno,dname,loc)
	    		VALUES(SEQ_DEPT.NEXTVAL,?,?)
	    		""";
	    
	    int rowCount = 0;
	    
	    try {
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, dname);
	    	pstmt.setString(2, loc);
	         //rs = stmt.executeQuery(sql);//SELECT 절 가져올때
	         rowCount = pstmt.executeUpdate();//Insert 절 가져올때
	         
	         if (rowCount ==1) {
				System.out.println("부서 추가 성공!!!");
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
		

	}//main


	//		SELECT *
	//		FROM user_sequences;
	//		--
	//		CREATE SEQUENCE seq_dept
	//		START WITH 50
	//		INCREMENT BY 10
	//		NOCACHE;

}//class
