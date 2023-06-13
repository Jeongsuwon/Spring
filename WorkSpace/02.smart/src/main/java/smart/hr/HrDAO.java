package smart.hr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class HrDAO implements HrService {
	@Autowired @Qualifier("hr") private SqlSession sql;
	
	@Override
	public int employee_insert(EmployeeVO vo) {
		return sql.insert("hr.insert", vo);	
	}

	@Override
	public List<EmployeeVO> employee_list() {
		return sql.selectList("hr.list");
	}

	@Override
	public EmployeeVO employee_info(int employee_id) {
		return sql.selectOne("hr.info", employee_id);
	}

	@Override
	public int employee_update(EmployeeVO vo) {
		return sql.update("hr.update", vo);
	}

	@Override
	public int employee_delete(int employee_id) {
		return sql.delete("hr.delete", employee_id);
	}

	@Override
	public List<DepartmentVO> employee_department_list() {
		return sql.selectList("hr.employeeDepartmentList");
	}

	@Override
	public List<EmployeeVO> employee_list(int department_id) {
		
		return sql.selectList("hr.list", department_id);
	}

	@Override
	public List<DepartmentVO> department_list() {
		return sql.selectList("hr.departmentList");
	}

	@Override
	public List<JobVO> job_list() {
		return sql.selectList("hr.jobList");
	}

}
