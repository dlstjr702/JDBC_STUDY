package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.EmpDeptSalgradeVO;

import com.util.DBConn;

public class Ex01 {

    public static void main(String[] args) {
//    	emp 테이블에서 사원정보를 검색 조회
//    	 *    1) 검색조건(searchCondition): 사원명(n), 잡(j), 입사일자(h), sal 등급(g), 사원명+잡(nj)
//    	 *    2) 검색어(searchKeyword)
//    	 *    3) 출력형식
//    	 *      -----------------------------------------------
//    	      사번   이름           직업          입사일           급여    부서
//    	      -----------------------------------------------
//    	      7369 SMITH      CLERK      1980-12-17    800  20
//    	      7876 ADAMS      CLERK      1987-05-23   1100  20
//    	      7900 JAMES      CLERK      1981-12-03    950  30
//    	      -----------------------------------------------
//    	 end 
    	

        Connection conn = DBConn.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        ArrayList<EmpDeptSalgradeVO> list = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("> 검색조건(n,j,h,g,nj) , 검색어 : ");
        String searchCondition = scanner.next();
        String searchKeyword = scanner.next();

        String sql = """
                SELECT
                       e.empno,
                       e.ename,
                       e.job,
                       e.hiredate,
                       sal + NVL(comm, 0) sal,
                       e.deptno
                FROM emp e JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal
                """;

        // 검색조건
        if (searchCondition.equalsIgnoreCase("n")) {
            sql += String.format(
                    " WHERE REGEXP_LIKE(e.ename,'%s','i')",searchKeyword);
        } else if (searchCondition.equalsIgnoreCase("j")) {
            sql += String.format(
                    " WHERE REGEXP_LIKE(e.job,'%s','i')",searchKeyword);
        } else if (searchCondition.equalsIgnoreCase("h")) {
            sql += String.format(
                    " WHERE TO_CHAR(e.hiredate,'YYYY-MM-DD') LIKE '%%%s%%'", searchKeyword);
        } else if (searchCondition.equalsIgnoreCase("g")) {
            sql += String.format(
                    " WHERE s.grade=%s",searchKeyword);
        } else if (searchCondition.equalsIgnoreCase("nj")) {
            sql += String.format(" WHERE REGEXP_LIKE(e.ename,'%1$s','i')" + " OR REGEXP_LIKE(e.job,'%1$s','i')",searchKeyword);
        }

        sql += " ORDER BY e.empno";


        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                String job = rs.getString("job");
                LocalDateTime hiredate = rs.getDate("hiredate").toLocalDate().atStartOfDay();
                Double sal = rs.getDouble("sal");
                int deptno = rs.getInt("deptno");

                EmpDeptSalgradeVO vo = EmpDeptSalgradeVO.builder()
                                .empno(empno)
                                .ename(ename)
                                .job(job)
                                .hiredate(hiredate)
                                .sal(sal)
                                .deptno(deptno)
                                .build();

                list.add(vo);
            }

            dispEmpInfo(list);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            scanner.close();
        }

        System.out.println("end");
    }

    private static void dispEmpInfo(ArrayList<EmpDeptSalgradeVO> list) {
    	System.out.println("--------------------------------------------------------------");
        System.out.printf("%-6s %-10s %-10s %-12s %8s %5s%n",
                "사번", "이름", "직업", "입사일", "급여", "부서");
        System.out.println("--------------------------------------------------------------");

        for (EmpDeptSalgradeVO vo : list) {
            System.out.printf("%-6d %-10s %-10s %-12s %8f \t %5d%n",
                    vo.getEmpno(),
                    vo.getEname(),
                    vo.getJob(),
                    vo.getHiredate().toLocalDate(),
                    vo.getSal(),
                    vo.getDeptno());
        }

        System.out.println("--------------------------------------------------------------");
    }
}