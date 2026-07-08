package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

public class Ex02 {

	public static void main(String[] args) {
		
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
        BoardService service = new BoardService(dao);
        BoardController controller = new BoardController(service);
        controller.boardStart();
		
		

		/*
		 * 1) 목록보기
		 * 2) 새글쓰기
		 * 3) 상세보기 : 목록보기에서 보고자하는 게시글의 제목을 클릭
		 * 				해당 글번호의 게시글 정보를 조회해서 출력
		 * 
		 * 
		 * 
		 * [MVC 패턴]
		 * 1. [C]ontroller
		 *  	주문서 / 요리 DTO (Data Transfer Object) / VO
		 * 
		 *    Service   선택				-- 로직담당 [M]odel
		 *     주문서
		 *    DAO(Data Access Object)   -- 로직담당 [M]odel
		 *     주문서
		 *    
		 *    [V]iew 출력담당
		 * 		요리전달
		 * 
		 * 2. MVC패턴에 패키지
		 * 		??.??.controller.컨트롤러
		 *         .service.서비스
		 *         .persistence.DAO
		 *         영속, 지속성 : 스프링 부트 + JPA
		 *         .domain.DTO 또는 VO
		 *         
		 *   days04.board.controller 패키지 추가   
		 *   			ㄴBoardController.java   
		 *   days04.board.service 패키지 추가      
		 *   days04.board.persistence 패키지 추가
		 *   days04.board.domain 패키지 추가
		 * 
		 * */


	}

}