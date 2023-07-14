package hr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeesDAO {
	@Autowired @Qualifier("hr") SqlSession sql;
	
	public List<EmployeesVO> list(){
		
		List<EmployeesVO> list = sql.selectList("hr.list");
		return list;
	}
}
