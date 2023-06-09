package smart.hr;

import java.util.List;

public interface HrService {
	//CRUD
	int employee_insert(EmployeeVO vo); //신규사원등록
	List<EmployeeVO> employee_list(); //사원목록조회
	List<EmployeeVO> employee_list(int department_id); //특정부서에 속한 사원목록 조회
	List<DepartmentVO> employee_department_list();//사원이 있는 부서목록 조회
	List<DepartmentVO> department_list();//회사의 부서목록 조회
	List<JobVO> job_list();//회사의 업무목록 조회
	EmployeeVO employee_info(int employee_id);//사원정보조회
	int employee_update(EmployeeVO vo);//사원정보변경저장
	int employee_delete(int employee_id);//사원정보삭제
	
}
