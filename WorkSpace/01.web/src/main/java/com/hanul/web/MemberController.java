package com.hanul.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

	//로그인 처리요청
	@RequestMapping("loginResult")
	public String login(@RequestParam String id, String pw) {
		//로그인 성공: home화면
		//로그인 실패: 다시 로그인하도록 로그인 화면으로 연결
		//hong / 1234인 경우만 로그인 성공으로 간주
		if(id.equals("hong")&&pw.equals("1234")) {
//			return "home"; //forward방식 -> uri 유지
			return "redirect:/"; //redirect 방식 -> uri 변경
		}else {
			// return "member/login"; //forward방식
			return "redirect:login"; //redirect 방식
		}
	}
	
	//로그인 화면요청
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
}
