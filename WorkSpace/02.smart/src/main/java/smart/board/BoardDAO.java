package smart.board;



import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import smart.common.PageVO;


@Repository
public class BoardDAO implements BoardService {
	
	
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	@Override
	public int board_register(BoardVO vo) {
		//방명록 정보 저장 + 첨부파일정보 저장
		int insert = sql.insert("board.register", vo);
		
		if(insert == 1 && vo.getFileList() != null) {
			sql.insert("board.fileRegister", vo);
		}
		
		return insert;
	}

	@Override
	public PageVO board_list(PageVO page) {
		//건수 조회
		page.setTotalList(sql.selectOne("board.totalList", page));
		//해당 페이지의 목록(기본 10건)
		page.setList(sql.selectList("board.list", page));
		return page;
	}

	@Override
	public BoardVO board_info(int id) {
		//방명록 정보 + 첨부파일 정보
		BoardVO vo = sql.selectOne("board.info", id);
		vo.setFileList(sql.selectList("board.fileList", id));
		return vo;
	}

	@Override
	public int board_update(BoardVO vo) {
		int update = sql.update("board.update", vo);
		if( update == 1 && vo.getFileList() != null) {
			sql.insert("board.fileRegister", vo);
		}
		return update;
	}

	@Override
	public int board_read(int id) {
		return sql.update("board.read", id);
	}

	@Override
	public int board_delete(int id) {
		return sql.delete("board.delete", id);
	}

	@Override
	public FileVO board_file_info(int id) {
		return sql.selectOne("board.fileInfo", id);
	}

	@Override
	public List<FileVO> board_file_remove(String removed) {
		return sql.selectList("board.fileRemoved", removed);
	}

	@Override
	public int board_file_delete(int id) {
		return sql.delete("board.fileDelete", id);
	}

	@Override
	public int board_comment_register(BoardCommentVO vo) {
		return sql.insert("board.commentRegister", vo);
	}

	@Override
	public int board_comment_update(BoardCommentVO vo) {
		return sql.update("board.commentUpdate", vo);
	}

	@Override
	public int board_comment_delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardCommentVO> board_comment_list(int board_id) {
		return sql.selectList("board.commentList", board_id);
	}

}
