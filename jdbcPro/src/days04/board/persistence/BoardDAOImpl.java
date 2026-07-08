package days04.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import days04.board.domain.BoardDTO;

public class BoardDAOImpl implements BoardDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private BoardDTO dto = null;
	private ResultSet rs = null;


	public BoardDAOImpl(Connection conn) {
		this.conn = conn;
	}


	// 
	public Connection getConn() {
		return conn;
	}

	// Setter를 사용한 의존성 주입(DI)
	public void setConn(Connection conn) {
		this.conn = conn;
	}





	// select 조회
	@Override
	public List<BoardDTO> select() throws SQLException {

		String sql = """
				SELECT seq, title, writer,email,writedate,readed
				FROM tbl_cstvsboard
				ORDER BY seq DESC
				""";

		List<BoardDTO> list = null;


		this.pstmt = this.conn.prepareStatement(sql);
		this.rs = this.pstmt.executeQuery();

		if (this.rs.next() ) {
			list = new ArrayList<BoardDTO>();
			do {
				this.dto = BoardDTO.builder()
						.seq(this.rs.getInt("seq"))
						.title(this.rs.getString("title"))
						.writer(this.rs.getString("writer"))
						.email(this.rs.getString("email"))
						.writedate(this.rs.getDate("writedate"))
						.readed(this.rs.getInt("readed"))
						.build();
				list.add(dto);

			} while (this.rs.next());//do

		}// if



		if( this.rs != null ) this.rs.close();
		if( this.pstmt != null ) this.pstmt.close();


		return list;
	}

	// insert
	@Override
	public int insert(BoardDTO dto) throws SQLException {
		String sql = """
				INSERT INTO tbl_cstvsboard( seq,writer,pwd,email,title,tag,content)
				VALUES(seq_tblcstvsboard.NEXTVAL,?,?,?,?,?,?)
				""";
		int rowCount = 0;

		this.pstmt = this.conn.prepareStatement(sql);

		this.pstmt.setString(1, dto.getWriter());
		this.pstmt.setString(2, dto.getPwd());
		this.pstmt.setString(3, dto.getEmail());
		this.pstmt.setString(4, dto.getTitle());
		this.pstmt.setInt(5, dto.getTag());
		this.pstmt.setString(6, dto.getContent());

		rowCount = this.pstmt.executeUpdate();
		if( this.pstmt != null ) this.pstmt.close();

		return rowCount;
	}

	// 읽기
	@Override
	public int increaseReaded(long seq) throws SQLException {
		String sql = """
				UPDATE tbl_cstvsboard
				SET readed = readed + 1
				WHERE seq = ? 
				""";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();
		if( this.pstmt != null ) this.pstmt.close();
		return rowCount;
	}


	// view
	@Override
	public BoardDTO view(long seq) throws SQLException {
		String sql = """
				SELECT seq, title, writer,email,writedate,readed,content
				FROM tbl_cstvsboard
				WHERE seq = ?
				""";

		BoardDTO dto = null;


		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		this.rs = this.pstmt.executeQuery();

		if (this.rs.next() ) {
			
				dto = BoardDTO.builder()
						.seq(this.rs.getInt("seq"))
						.title(this.rs.getString("title"))
						.writer(this.rs.getString("writer"))
						.email(this.rs.getString("email"))
						.writedate(this.rs.getDate("writedate"))
						.readed(this.rs.getInt("readed"))
						.content(this.rs.getString("content"))
						.build();
		}// if



		if( this.rs != null ) this.rs.close();
		if( this.pstmt != null ) this.pstmt.close();


		return dto;
	}


	
	// delete
	@Override
	public boolean delete(int seq) throws SQLException {

	    String sql = """
	        DELETE FROM tbl_cstvsboard
	        WHERE seq = ?
	        """;

	    this.pstmt = this.conn.prepareStatement(sql);
	    this.pstmt.setInt(1, seq);

	    int rowCount = this.pstmt.executeUpdate();

	    if (this.pstmt != null) this.pstmt.close();

	    return rowCount == 1;
	}
	
	
	
	
	
	//	--- 더좋은코딩....
	//	@Override
	//	public List<BoardDTO> select() throws SQLException {
	//
	//	    String sql = """
	//	            SELECT seq,
	//	                   title,
	//	                   writer,
	//	                   email,
	//	                   writedate,
	//	                   readed
	//	              FROM tbl_cstvsboard
	//	             ORDER BY seq DESC
	//	            """;
	//
	//	    List<BoardDTO> list = new ArrayList<>();
	//
	//	    try (
	//	        PreparedStatement pstmt = conn.prepareStatement(sql);
	//	        ResultSet rs = pstmt.executeQuery()
	//	    ) {
	//
	//	        while (rs.next()) {
	//
	//	            BoardDTO dto = BoardDTO.builder()
	//	                    .seq(rs.getInt("seq"))
	//	                    .title(rs.getString("title"))
	//	                    .writer(rs.getString("writer"))
	//	                    .email(rs.getString("email"))
	//	                    .writedate(rs.getDate("writedate"))
	//	                    .readed(rs.getInt("readed"))
	//	                    .build();
	//
	//	            list.add(dto);
	//	        }
	//	    }
	//
	//	    return list;
	//	}
	//	


}
