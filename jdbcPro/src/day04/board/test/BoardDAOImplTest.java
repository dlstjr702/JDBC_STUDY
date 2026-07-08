package day04.board.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {

	private Connection conn = DBConn.getConnection();
	private BoardDAO dao = null;
	
	@BeforeEach
	void setUp() {
		System.out.println("Set Up");
		this.conn = DBConn.getConnection();
		this.dao = new BoardDAOImpl(conn);
	}
	
	
	@AfterEach
	void tearDown() {
		System.out.println("tearDown");
		DBConn.close();
	}
	
	
	
	@Test
	void testSelect() throws SQLException {
		List<BoardDTO> list = dao.select();
		
		
		// JUnit5 에 여러개의 검증(assertion)을 하나의 테스트 안에서
		// 모두 수행할 수 있도록 하는 메서드.
		assertAll(
				() -> assertNotNull(list),
				() -> assertFalse(list.isEmpty())
				);
		list.forEach(System.out::println);
		
		
	}
	
	
	/*
	@Test
	   void testSelect() {
	      
	      Connection conn = DBConn.getConnection();
	      BoardDAO dao = new BoardDAOImpl(conn);
	      try {
	         List<BoardDTO> list = dao.select();
	         if (list != null) {
	            list.forEach( System.out::println );
	         } else {
	                System.out.println("게시글이 존재 X");
	         }
	      } catch (SQLException e) { 
	         e.printStackTrace();
	      } finally {
	         DBConn.close();
	      }
	      System.out.println(" end ");
	   }
	*/
}
