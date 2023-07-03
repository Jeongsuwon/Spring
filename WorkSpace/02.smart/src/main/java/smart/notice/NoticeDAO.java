package smart.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import smart.common.PageVO;


@Repository
public class NoticeDAO implements NoticeService {
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	@Override
	public int notice_regist(NoticeVO vo) {
		return sql.insert("notice.insert", vo);
	}

	@Override
	public List<NoticeVO> notice_list() {
		return sql.selectList("notice.list");
	}

	@Override
	public NoticeVO notice_info(int id) {
		return sql.selectOne("notice.info", id);
	}

	@Override
	public int notice_update(NoticeVO vo) {
		return sql.update("notice.update", vo);
	}

	@Override
	public int notice_delete(int id) {
		return sql.delete("notice.delete", id);
	}

	@Override
	public int notice_read(int id) {
		return sql.update("notice.read", id);
	}

	@Override
	public PageVO notice_list(PageVO page) {
		//총 글의 건수 조회
		page.setTotalList(sql.selectOne("notice.totalList", page));
		page.setList(sql.selectList("notice.list", page));
		return page;
	}

	@Override
	public int notice_reply_regist(NoticeVO vo) {
		return sql.insert("notice.replyRegister", vo);
	}

}
