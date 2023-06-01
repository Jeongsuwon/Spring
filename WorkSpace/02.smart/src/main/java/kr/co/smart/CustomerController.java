package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CustomerServiceImpl;

@Controller
public class CustomerController {
	@Autowired private CustomerServiceImpl service;
	//고객목록화면 요청
	@RequestMapping("/list.cu")
	public String list(HttpSession session) {
		
		session.setAttribute("category", "cu");
		//비지니스로직
		service.customer_list();
		//응답화면연결
		return "customer/list";
	}
}
