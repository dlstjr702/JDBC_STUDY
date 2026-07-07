package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author 양인석  
 * @date 2026. 7. 7. 오후 12:04:31
 * @subject
 * @content
 */
public class Ex03_02 {

	public static void main(String[] args) {
		// 1.
		String sql = """
				SELECT d.deptno, dname, COUNT(e.empno) empCount
				FROM dept d LEFT JOIN emp e ON d.deptno = e.deptno
				GROUP BY d.deptno, dname
				ORDER BY d.deptno ASC
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
		ArrayList<EmpVO> elist = null; // value값으로 들어가는 ArrayList

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

					//System.out.printf("[%s(%d)\t%d명]\n", dname, deptno, empCount);
					// [key]
					dVo = DeptVO.builder()
							.deptno(deptno)
							.dname(dname)
							.empCount(empCount)
							.build();

					// 시작 deptno 속한 사원들 SELECT 출력
					try {
						ePstmt = conn.prepareStatement(eSql);
						ePstmt.setInt(1, deptno);
						eRs = ePstmt.executeQuery();

						// Alt + Shift + A
						int empno;
						String ename;
						String job;
						int mgr;
						LocalDateTime hiredate;  
						double sal;
						double comm;

						if ( eRs.next()  ) {  // 첫 번째 레코드는 존재한다.

							do {
								empno = eRs.getInt("empno");
								ename = eRs.getString("ename");
								job = eRs.getString("job");
								mgr = eRs.getInt("mgr"); 
								hiredate = eRs.getDate("hiredate").toLocalDate().atStartOfDay();    
								sal = eRs.getDouble("sal");
								comm = eRs.getDouble("comm");
								deptno = eRs.getInt("deptno");

								//System.out.printf("  [%d]\t%s\t%s\n", empno, ename, job);
								eVo = EmpVO.builder()
										.empno(empno)
										.ename(ename)
										.job(job)
										.hiredate(hiredate)
										.sal(sal)
										.comm(comm)
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


	} // main

	private static void dispLinkedHashMap(LinkedHashMap<DeptVO, ArrayList<EmpVO>> map) {

	    map.forEach((dVo, elist) -> {

	        System.out.printf("[%s(%d)\t%d명]%n",
	                dVo.getDname(),
	                dVo.getDeptno(),
	                dVo.getEmpCount());

	        if (elist == null || elist.isEmpty()) {
	            System.out.println("사원존재X");
	        } else {
	            elist.forEach(eVo -> {
	                System.out.printf("  [%d]\t%s\t%s%n",
	                		eVo.getEmpno(),
	                		eVo.getEname(),
	                		eVo.getJob());
	            });
	        }

	    });

	}

} // class

//Map<key,value> 클래스 안에 저장
//  DeptVO    ArrayList<EmpVO>
//-------------------------------------
//부서번호    부서명           사원수
//-------------------------------------
//10         ACCOUNTING      3
//20         RESEARCH        5
//30         SALES           6
//40         OPERATIONS      0