package days01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.doit.domain.DeptVO;
import com.util.DBConn;

/**
 * @author kEnik  
 * @date 2026. 7. 3. 오전 10:23:02
 * @subject  부서 정보를 조회해서 ArrayList 저장 후 출력
 * @content  한 부서정보를 저장할 객체(클래스) 선언:   [V]alue [O]bject
 *                                           org.doit.domain.DeptVO
 */


public class Ex04 {

   public static void main(String[] args) {
      
      Connection conn = null;
      Statement  stmt = null; // SQL 실행하는 객체
      ResultSet  rs   = null; // 실행 결과를 저장하는 객체
      ArrayList<DeptVO> list = null;
      DeptVO vo = null;
      
      // 1 + 2
      conn = DBConn.getConnection();

      // 3. sql작성->stmt sql실행-> rs -> ArrayList 저장 -> 출력
//      String sql = "SELECT * "
//              + " FROM dept "
//              + " ORDER BY deptno ASC";
      // Java 15 이상: 텍스트블럭 
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
         
         if ( rs.next()  ) {  // 첫 번째 레코드는 존재한다.
            list = new ArrayList<DeptVO>();
            do {
               deptno = rs.getInt("deptno");
               dname = rs.getString("dname");
               loc = rs.getString("loc");
//               vo = new DeptVO(deptno, dname, loc);
               vo = DeptVO.builder()
                        .deptno(deptno)
                        .dname(dname)
                        .loc(loc)
                        .build();
               
//               System.out.println( vo );
               list.add(vo);
            } while (rs.next() );
         } // if
          
         
         dispDeptInfo(list);
         
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
   } // main

   private static void dispDeptInfo(ArrayList<DeptVO> list) {
      // 1. 반복자 처리      
//      Iterator<DeptVO> ir = list.iterator();
//      while (ir.hasNext()) {
//         DeptVO vo = ir.next();
//         System.out.println( vo );
//      }
      
      // 2. 람다식
//      list.forEach(new Consumer<DeptVO>() { 
//         @Override
//         public void accept(DeptVO vo) {
//             System.out.println(vo);
//         }
//      });
      
//      list.forEach( vo -> System.out.println(vo) );
      
      // 메서드 참조
      list.forEach( System.out::println );
      
   }

} // class

