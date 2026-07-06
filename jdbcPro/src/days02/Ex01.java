package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author inseok
 * @date 2026. 7. 6.오전 9:12:05
 * @date
 * @subject
 * @content
 */


public class Ex01 {

	public static void main(String[] args) {
		
		/*
		 * 1) JDBC드라이버로딩 
		 * Class.forName("oracle.jdbc.driver.OracleDriver");
		 * 2) DB연결
		 * Connection conn = DriverManager.getConnection(url,user,password);
		 * 3) 질의/응답 처리 
		 * String sql = "SQL";
		 * String sql = "SELETCT * FROM dept";
		 * Statement stmt = conn.createStatement();
		 * ResultSet rs = stmt.executeQuery();
		 * 
		 * while(boolean rs.next()){
		 * 	 int deptno = rs.getInt("deptno");
		 * 	//부서명
		 * 	//지역명
		 * 
		 * 	//출력...
		 * }
		 * 
		 * 4) DB닫기
		 * rs.close()
		 * stmt.close();
		 * conn.close();
		 */
		
		
		/*
		 * DBConn.java 싱글톤
		 *  - getConnection(){}
		 *  - Connect getConnection(String url,String user, String password){}
		 *  - close(){}
		 *  
		 *  1+2
		 *  Connection conn = DBConn.getConnection();
		 *  
		 *  3.직접구현
		 *  
		 *  4.
		 *  DBConn.close();
		 * 
		 * */
		
		// 모든 사원 정보를 조회해서 ArrayList<> 저장하고 출력하는 메서드를 만들어서 출력
		// org.doit.domain.EmpVo.java
		// dispEmpInfo() 메서드 구현
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO vo = null;
		
		conn = DBConn.getConnection();
		
		String sql = """
					SELECT *
					FROM emp
					ORDER BY deptno ASC
					""";
		
		 try {
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         int empno;
		     String ename;
		     String job;
		     int mgr;
		     LocalDateTime hiredate;
		     Double sal;
		     Double comm;
		     int deptno;
	         
	         if ( rs.next()  ) { 
	            list = new ArrayList<EmpVO>();
	            do {
	               empno = rs.getInt("empno");
	               ename = rs.getString("ename");
	               job = rs.getString("job");
	               mgr = rs.getInt("mgr");
	               hiredate = rs.getDate("hiredate").toLocalDate().atStartOfDay();
	               // LocalDate -> LocalDateTime 변환
	               //hd.atStartOfDay() == hd.atTime(0,0,0);
	               sal = rs.getDouble("sal");
	               comm = rs.getDouble("comm");
	               deptno = rs.getInt("deptno");
	               vo = EmpVO.builder()
	            		   	.empno(empno)
	            		   	.ename(ename)
	            		   	.job(job)
	            		   	.mgr(mgr)
	            		   	.sal(sal)
	            		   	.hiredate(hiredate)
	            		   	.comm(comm)
	                        .deptno(deptno)
	                        .build();
	               
	               list.add(vo);
	            } while (rs.next() );
	         } // if
	          
	         
	         dispEmpInfo(list);
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            // 4
	            DBConn.close();
	         } catch (SQLException e) { 
	            e.printStackTrace();
	         }
	      }

	      
	      System.out.println(" end ");
		
	

	}

	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		list.forEach( System.out::println );
	}

}
