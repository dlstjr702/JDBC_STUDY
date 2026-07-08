package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Ex01 {

	public static void main(String[] args) {
		/* [문제❓]
    	각 급여 등급 및 사원수 출력과
    	 ㄴ 하위에 속한 사원들 정보 출력하는 코딩.
    	 ㄴ (조건) 모든 출력 정보를 Map 안에 저장해서 출력하기

    	(출력결과)
    	1등급   ( 700 ~ 1200 ) - 3명
    	    부서번호 부서명    사원번호  사원명   SAL
    	   20   RESEARCH   7369   SMITH   800.00 
    	   20   RESEARCH   7876   ADAMS   1100.00 
    	   30   SALES   7900   JAMES   950.00 

    	2등급   ( 1201 ~ 1400 ) - 3명
    	   10   ACCOUNTING   7934   MILLER   1300.00 
    	   30   SALES   7521   WARD   1250.00 
    	   30   SALES   7654   MARTIN   1250.00 

    	3등급   ( 1401 ~ 2000 ) - 2명
    	   30   SALES   7499   ALLEN   1600.00 
    	   30   SALES   7844   TURNER   1500.00 

    	4등급   ( 2001 ~ 3000 ) - 5명
    	   10   ACCOUNTING   7782   CLARK   2450.00 
    	   20   RESEARCH   7566   JONES   2975.00 
    	   20   RESEARCH   7788   SCOTT   3000.00 
    	   20   RESEARCH   7902   FORD   3000.00 
    	   30   SALES   7698   BLAKE   2850.00 

    	5등급   ( 3001 ~ 9999 ) - 1명
    	   0   null   7839   KING   5000.00 

    	 end  
		 */



		String sql = """
				      SELECT s.grade,s.losal,s.hisal,d.deptno,d.dname,e.empno,e.ename,e.sal
					  FROM dept d LEFT JOIN emp e ON d.deptno = e.deptno
					  LEFT JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal
					  ORDER BY s.grade, d.deptno, e.empno;
				      """;
		
		String eSql = """
				SELECT * 
				FROM emp
				WHERE deptno = ?
				ORDER BY empno ASC
				""";

		Connection conn = null;
		PreparedStatement dPstmt = null, ePstmt = null;
		ResultSet dRs = null, eRs = null;
		DeptVO dVo = null;
		EmpVO eVo = null;

		LinkedHashMap<DeptVO, ArrayList<EmpVO>> map = new LinkedHashMap<>();
		ArrayList<EmpVO> elist = null; 

		int deptno;
		String dname;
		int empCount;

		try {
			conn = DBConn.getConnection();
			dPstmt = conn.prepareStatement(sql);
			dRs = dPstmt.executeQuery();

			if (dRs.next()) {
				elist = new ArrayList<EmpVO>();
				do {
					deptno = dRs.getInt("deptno");
					dname = dRs.getString("dname");
					empCount = dRs.getInt("empCount");
				

					dVo = DeptVO.builder()
							.deptno(deptno)
							.dname(dname)
							.empCount(empCount)
							.build();

					try {
						ePstmt = conn.prepareStatement(eSql);
						ePstmt.setInt(1, deptno);
						eRs = ePstmt.executeQuery();

						int empno;
						String ename;  
						double sal;
						

						if ( eRs.next()  ) {  

							do {
								empno = eRs.getInt("empno");
								ename = eRs.getString("ename");
								sal = eRs.getDouble("sal");
								deptno = eRs.getInt("deptno");

								eVo = EmpVO.builder()
										.empno(empno)
										.ename(ename)
										.sal(sal)
										.deptno(deptno)
										.build();
								elist.add(eVo);

							} while (eRs.next() );



						} else {
							elist = null;
						}

						dispLinkedHashMap(map);

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							eRs.close();
							ePstmt.close();                     
						} catch (SQLException e) { 
							e.printStackTrace();
						}
					}                
					// 끝

					map.put(dVo, elist);

				} while (dRs.next());
			} // if

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				dRs.close();
				dPstmt.close();
				DBConn.close();         
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

	}




	private static void dispLinkedHashMap(LinkedHashMap<DeptVO, ArrayList<EmpVO>> map) {

		map.forEach((dVo, elist) -> {
			//등급   ( 2001 ~ 3000 ) - 5명
			System.out.println("------------------------------");
			System.out.printf("%s등급   (%d)\t %d %n",dVo.getDname(),dVo.getDeptno(),dVo.getEmpCount());
			if (elist == null || elist.isEmpty()) {
				System.out.println("사원존재X");
			} else {
				elist.forEach(eVo -> {
					System.out.printf("  [%d]\t%s\t%s%n",
							eVo.getEmpno(),
							eVo.getEname(),
							eVo.getSal());
				});
			}

		});

	}
}