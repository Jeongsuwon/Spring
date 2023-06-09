package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import smart.hr.EmployeeVO;
import smart.hr.HrDAO;

@Controller @RequestMapping("/hr")
public class HrController {
	
	//
	
	@Autowired private HrDAO service;
	
	//사원정보 수정저장처리 요청
	@RequestMapping("/update")
	public String update(EmployeeVO vo) {
		//비지니스로직: 화면에서 변경입력한 정보로 DB 사원정보를 변경저장한 후
		service.employee_update(vo);
		//프리젠테이션로직: 응답화면 - 사원정보화면
		return "redirect:info?id=" + vo.getEmployee_id();
	}
	
	//사원정보 수정화면 요청
	@RequestMapping("modify")
	public String modify(int id, Model model) {
		//DB에서 해당 사원정보를 조회해와 수정화면에 출력할 수 있도록 Model객체에 담는다
		model.addAttribute("vo",service.employee_info(id));
		
		//회사의 부서/업무 목록 조회
		model.addAttribute("departments", service.department_list());
		model.addAttribute("jobs", service.job_list());
		return "hr/modify";
	}
	
	//사원정보화면 요청
	@RequestMapping("/info")
	public String info(int id, Model model) {
		//비지니스로직: DB에서 해당 사원정보를 조회해와
		//			화면에 출력할 수 있도록 Model객체에 담는다
		model.addAttribute("vo" ,service.employee_info(id));
		//프리젠테이션로직: 응답화면 - 정보화면
		return "hr/info";
	}
	
	//사원목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, @RequestParam(defaultValue = "-1") int department_id) {
		session.setAttribute("category", "hr");
		//비지니스로직: DB에서 사원정보목록을 조회한 후
		//			목록화면에 출력할 수 있도록 Model 객체에 담는다.
		
		//회사의 사원이 소속된 부서목록 조회
		model.addAttribute("department_id", department_id);
		model.addAttribute("departments", service.employee_department_list());
		
		//사원목록 조회 
		model.addAttribute("list",  department_id==-1 ? service.employee_list():service.employee_list(department_id));

		//프리젠테이션로직: 응답화면연결 - 목록화면
		return "hr/list";
	}
}
