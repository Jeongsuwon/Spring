package kr.com.hanul;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import flo.FloDAO;
import flo.FloVO;

@RestController @RequestMapping("/flo")
public class FloController {
	
	
	@Autowired FloDAO dao;
	@RequestMapping(value="/flo_Login", produces="text/html;charset=utf-8")
	public String login(String user_id, String pw) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", user_id);
		params.put("pw", pw);
		FloVO vo = dao.login(params);
		return new Gson().toJson(vo);
	}
	
	@ResponseBody
	@RequestMapping("/useridCheck")
	public boolean useridCheck(String userid) {
		// 화면에서 입력한 아이디가 DB에 있는지 여부를 확인
		return false;
	}
	
}
