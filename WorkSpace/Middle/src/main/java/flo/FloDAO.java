package flo;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;





@Repository
public class FloDAO {
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	public FloVO login(HashMap<String, String> params) {
	
		FloVO vo = sql.selectOne("flo.flo_Login", params);
		return vo;
	}
	
	
	public FloVO login_Check(String user_id) {
		return sql.selectOne("flo.check", user_id);
	}
}
