package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.doit.domain.EmpVO;
import org.doit.domain.SalgradeVO;

import com.util.DBConn;

/**
 * @author inseok  
 * @date 2026. 7. 8. 오전 7:05:39
 * @subject (복습문제)  
 * @content 각 급여 등급 및 사원수 출력과
 *           ㄴ 하위에 속한 사원들 정보 출력하는 코딩.
 *           ㄴ (조건) 모든 출력 정보를 Map 안에 저장해서 출력하기
 *           Ex01_02.java
 */
public class Ex01_02 {

	public static void main(String[] args) {

	      LinkedHashMap<SalgradeVO, ArrayList<EmpVO>> map = new LinkedHashMap<>();
	      ArrayList<EmpVO> elist = null; // value

	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      conn = DBConn.getConnection();
	      String sql = """
	            SELECT empno, ename, hiredate, sal + NVL(comm, 0) pay , dname , e.deptno  ,sal , grade , losal, hisal
	                FROM emp e LEFT JOIN dept d ON e.deptno = d.deptno
	                JOIN salgrade s ON sal BETWEEN losal AND hisal
	            order by grade
	               """;
	      
	      int rowCount = 0;

	      EmpVO eVo = null;
	      int oldGrade = -1, grade=0;
	      SalgradeVO sVo = null;

	      try {
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         if (rs.next()) {
	            do {
	               grade = rs.getInt("grade");
	               eVo = EmpVO.builder()
	                     .empno(rs.getInt("empno"))
	                     .ename(rs.getString("ename"))
	                     .deptno(rs.getInt("deptno"))
	                     .sal(rs.getDouble("sal"))
	                     //X.dname(rs.getString("dname"))
	                     .build();

	               if(grade != oldGrade) {
	                  sVo = SalgradeVO.builder()
	                        .grade(grade)
	                        .losal(rs.getInt("losal"))
	                        .hisal(rs.getInt("hisal"))
	                        .build();
	                  elist = new ArrayList<>();
	                  elist.add(eVo);
	                  map.put(sVo, elist);
	                  oldGrade = grade;
	               }
	               else {
	                  elist.add(eVo);
	                  
	               }
	            } while (rs.next());
	         }

	         displayGradeEmp(map);
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	         try {
	            pstmt.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }

	         DBConn.close();
	      }

	   }

	   private static void displayGradeEmp(LinkedHashMap<SalgradeVO, ArrayList<EmpVO>> map) {
	      map.forEach((sVo,elist)-> {
	         System.out.println("====================================");;
	         System.out.printf("%d 등급 (%d ~ %d) - %d명\n",sVo.getGrade(),sVo.getLosal(),sVo.getHisal(),elist.size());
	            if (elist == null || elist.isEmpty()) {
	               System.out.println("사원 존재 X");
	            } else {
	               System.out.printf("부서번호 부서명 사원번호 사원명 SAL \n");
	               elist.forEach(eVo -> {
	                 //X System.out.printf("%d %s %d %s %6.2f\n", eVo.getDeptno(),eVo.getDname(),eVo.getEmpno(),eVo.getEname(),eVo.getSal());
	               });
	            }
	      });
	   }


} // class




