package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import smart.common.PageVO;
import smart.report.ReportDAO;

@Controller @RequestMapping("/report")
public class ReportController {
	
//	@Autowired private ReportDAO service;
	
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, PageVO page) {
		
		session.setAttribute("category", "re");
//		model.addAttribute("page", service.report_list(page));
		return "report/list";
	}

}
