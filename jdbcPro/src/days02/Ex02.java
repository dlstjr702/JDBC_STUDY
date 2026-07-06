package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author inseok
 * @date 2026. 7. 6.오후 3:17:38
 * @subject 
 * @content 1. 부서정보출력
 * 			2. 부서번호를 선택하세요? 20 엔터
 * 			3. 20번 부서원들을 출력. 
 */


public class Ex02 {

	public static void main(String[] args) {


		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null; 
		ArrayList<DeptVO> list = null;
		DeptVO vo = null;
		conn = DBConn.getConnection();

		String sql = """
				 SELECT *
				 FROM dept
				 ORDER BY deptno ASC
				""";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			int deptno;
			String dname, loc;

			if ( rs.next()  ) { 
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = DeptVO.builder()
							.deptno(deptno)
							.dname(dname)
							.loc(loc)
							.build();

					list.add(vo);
				} while (rs.next() );
			} // if


			//dispdmpInfo(list);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

		// 2. 
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 부서번호(deptno)입력?");
		int pDeptno = scanner.nextInt();

		//3. 해당부서원 조회
		ArrayList<EmpVO> elist = null;
		EmpVO evo = null;

		sql = """
				SELECT *
				FROM emp
				WHERE deptno=""" + pDeptno + """
				ORDER BY empno  ASC
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
				elist = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getDate("hiredate").toLocalDate().atStartOfDay();
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					evo = EmpVO.builder()
							.empno(empno)
							.ename(ename)
							.job(job)
							.mgr(mgr)
							.sal(sal)
							.hiredate(hiredate)
							.comm(comm)
							.deptno(deptno)
							.build();

					elist.add(evo);
				} while (rs.next() );
			} // if


			dispEmpInfo(elist,pDeptno);

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


		//end
		System.out.println(" end ");

	}//main


	private static void dispEmpInfo(ArrayList<EmpVO> elist,int deptno) {
		
		//선생님 풀이
		try {
			int deptEmpCount = elist.size();
			System.out.printf("[%d] 부서원(%d명) 조회 \n", deptno,deptEmpCount);
			elist.forEach( System.out::println );
			
		} catch (NullPointerException e) {
			System.out.printf("[%d] 부서원(%d명) 조회 \n", deptno,0);
			System.out.println("\t 사원존재 X");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//내 풀이
//		if(elist == null || elist.isEmpty() ) {
//			System.out.println("사원이 존재하지 않습니다.");
//	        return;
//		};
			        
	}

}//class
