package kr.com.hanul;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import hr.EmployeesDAO;
import hr.EmployeesVO;

@RestController
public class HrController {
	
	@Autowired EmployeesDAO dao ;
	
	@RequestMapping(value="/list.hr", produces="text/html;charset=utf-8")
	public String list(EmployeesVO vo) {
		List<EmployeesVO> list = dao.list();
		Gson gson = new Gson();
		//Object(List, DTO등) -> String json으로 바꾸는 메소드 : toJson()
		return gson.toJson(list);
	}
}
