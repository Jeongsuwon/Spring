package smart.report;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import smart.common.PageVO;

public class ReportDAO implements ReportService {
	
//	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public int report_regist(ReportVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReportVO> report_list() {
//		return sql.selectList("report.list");
		return null;
	}

	@Override
	public PageVO report_list(PageVO page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportVO report_info(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int report_update(ReportVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int report_delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int report_read(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
