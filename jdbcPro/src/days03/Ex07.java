package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author inseok  
 * @date 2026. 7. 7. 오후 4:07:35
 * @subject
 * @content
 */
public class Ex07 {

   public static void main(String[] args) {
      // 모든 부서 정보를 UP_SELECTDEPT 저장프로시저를 사용해서 조회.
      
      Connection conn = null;
      CallableStatement cstmt = null;
      ResultSet rs = null;
      ArrayList<DeptVO> list = null;
      DeptVO vo = null;

      String sql = "{ CALL  UP_SELECTDEPT( ? ) }";

      try {
         conn = DBConn.getConnection();
         cstmt = conn.prepareCall(sql);         
         // OUT  커서 선언
         cstmt.registerOutParameter(1, Types.REF_CURSOR);         
         cstmt.executeQuery();         
         rs = (ResultSet) cstmt.getObject(1);

         if (rs.next()) {
            list = new ArrayList<DeptVO>();
            do {
               vo = DeptVO.builder()
                     .deptno(rs.getInt("deptno"))
                     .dname(rs.getString("dname"))
                     .loc(rs.getString("loc"))
                     .build();
               list.add(vo);
            } while (rs.next());
         } // if
         
         dispDeptInfo(list);

      } catch (SQLException e) { 
         e.printStackTrace();
      } finally {
         try {
            cstmt.close();
            DBConn.close();
         } catch (SQLException e) { 
            e.printStackTrace();
         }
      }


   } // main

   private static void dispDeptInfo(ArrayList<DeptVO> list) {
      list.forEach(System.out::println);
   }

} // class

/*
CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN pdeptcursor  FOR
           SELECT * 
           FROM dept
           WHERE deptno > 0;
-- EXCEPTION    
END;

Procedure UP_SELECTDEPT이(가) 컴파일되었습니다.
 */