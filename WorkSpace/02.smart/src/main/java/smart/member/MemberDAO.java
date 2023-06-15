package smart.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO implements MemberService {
		@Qualifier("hanul") @Autowired private SqlSession sql;
	@Override
	public int member_join(MemberVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberVO member_info(String userid) {
		return sql.selectOne("member.info", userid);
	}

	@Override
	public List<MemberVO> member_list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int member_update(MemberVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int member_delete(String userid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String member_user_id_email(MemberVO vo) {
		return sql.selectOne("member.useridEmail", vo);
	}

	@Override
	public int member_resetPassword(MemberVO vo) {
		return sql.update("member.resetPassword", vo);
	}

}
