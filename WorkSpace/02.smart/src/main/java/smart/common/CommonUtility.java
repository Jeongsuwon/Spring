package smart.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smart.member.MemberVO;

@Service
public class CommonUtility {
	
	private void emailServerConnect(HtmlEmail email) {
		email.setHostName("smtp.naver.com"); //메일서버지정
		email.setAuthentication("sw_9312", "gmlakddl13!");// 아이디/비번으로 로그인
		email.setSSLOnConnect(true);
	}
	private String EMAIL_ADDRESS = "sw_9312@naver.com"; //아이디
	
	//이메일 보내기
	public boolean sendPassword(MemberVO vo, String pw) {
		boolean send = true;
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true); //이메일전송 과정 Console에서 로그로 확인
		
		emailServerConnect(email);
		try {
			email.setFrom(EMAIL_ADDRESS, "관리자");
			email.addTo(vo.getEmail(), vo.getName());//받는사람 지정
			email.setSubject("스마트 웹&앱 로그인 임시 비번");
			StringBuffer content = new StringBuffer();
			content.append("<h3>[").append(vo.getName()).append("]님 임시 비번 발급되었습니다.</h3>");
			content.append("<div>아이디: ").append(vo.getUserid()).append("</div>");
			content.append("<div>임시 비밀번호: <strong>").append(pw).append("</strong></div>");
			content.append("<div>발급된 임시 비밀번호로 로그인한 후 비밀번호를 변경하세요</div>");
			email.setHtmlMsg(content.toString()); //내용쓰기
			
			email.send();//보내기 버튼 클릭(전송)
			
			
		} catch (Exception e) {
			send = false;
		}//관리자가 보내는 이
		return send;
	}
	
	// context root url 지정
	public String appURL( HttpServletRequest request) {
		//http://localhost:8080/smart
		StringBuffer url = new StringBuffer("http://");
		//localhost = 127.0.0.1 = 본인 ip
		url.append(request.getServerName()).append(":"); // http://localhost:
		url.append(request.getServerPort()); //8080
		url.append(request.getContextPath()); //smart
		return url.toString();
		
		
	}
	
	
	
}
