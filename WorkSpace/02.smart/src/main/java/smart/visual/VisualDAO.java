package smart.visual;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class VisualDAO implements VisualService {
	 @Autowired @Qualifier("hr") private SqlSession sql;
	
	@Override
	public List<HashMap<String, Object>> department() {
		return sql.selectList("visual.department");
	}

	@Override
	public List<HashMap<String, Object>> hirement_year() {
		return sql.selectList("visual.hirementYear");
	}

	@Override
	public List<HashMap<String, Object>> hirement_month() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.hirementMonth");
	}

	@Override
	public List<HashMap<String, Object>> hirement_top3_year() {
		return sql.selectList("visual.hirementTop3Year");
	}

	@Override
	public List<HashMap<String, Object>> hirement_top3_month() {
		return sql.selectList("visual.hirementTop3Month");
	}

}
