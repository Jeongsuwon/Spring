package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import smart.hr.HrDAO;

@Controller @RequestMapping("/hr")
public class HrController {
	
	@Autowired private HrDAO service;
	//사원목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "hr");
		//비지니스로직: DB에서 사원정보목록을 조회한 후
		//			목록화면에 출력할 수 있도록 Model 객체에 담는다.
		
		//회사의 사원이 소속된 부서목록 조회
		
		model.addAttribute("list", service.employee_list());
		//프리젠테이션로직: 응답화면연결 - 목록화면
		return "hr/list";
	}
}
