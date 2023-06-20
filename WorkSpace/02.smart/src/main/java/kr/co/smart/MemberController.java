package kr.co.smart;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParser;

import smart.common.CommonUtility;
import smart.member.MemberDAO;
import smart.member.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberDAO service;
	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	// id: T6a4DJXRkNKz3SYDUkmj
	// sercret: _IndgXI1AX

	private String NAVER_ID = "T6a4DJXRkNKz3SYDUkmj";
	private String NAVER_SECRET = "_IndgXI1AX";
	private String KAKAO_ID = "44ae311334afadc867a392786dd67cb0";
	
	
	// 카카오로그인 처리 요청
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin(HttpServletRequest request) {
		//인가코드 : https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(KAKAO_ID);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/member/kakaoCallback");
		return "redirect:" + url.toString();
	}
	
	// 네이버로그인 처리 요청
	@RequestMapping("/naverLogin")
	public String naverLogin(HttpSession session, HttpServletRequest request) {
		// 네이버 로그인 연동 url 생성하기
		// https://nid.naver.com/oauth2.0/authorize?
		// response_type=code&client_id=CLIENT_ID&state=STATE_STRING&redirect_uri=CALLBACK_URL
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);

		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(NAVER_ID);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/member/naverCallback");
		return "redirect:" + url.toString();

	}

	//카카오 콜백처리
	@RequestMapping("/kakaoCallback")
	public String kakaoCallback(String code, HttpSession session) {
		if (code == null)
		return "redirect:/";
		/*
		curl -v -X POST "https://kauth.kakao.com/oauth/token" \
		 -H "Content-Type: application/x-www-form-urlencoded" \
		 -d "grant_type=authorization_code" \
		 -d "client_id=${REST_API_KEY}" \
		 --data-urlencode "redirect_uri=${REDIRECT_URI}" \
		 -d "code=${AUTHORIZE_CODE}"
		*/
		
		/*
		HTTP/1.1 200 OK
		Content-Type: application/json;charset=UTF-8
		{
		    "token_type":"bearer",
		    "access_token":"${ACCESS_TOKEN}",
		    "expires_in":43199,
		    "refresh_token":"${REFRESH_TOKEN}",
		    "refresh_token_expires_in":5184000,
		    "scope":"account_email profile" -> 필수x
		}
		*/
		
		// 카카오 인가코드https://kauth.kakao.com/oauth/authorize
		// String token = "";
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(KAKAO_ID);
		url.append("&code=").append(code);
		
		
		String response = common.requestAPI(url.toString());
		JSONObject json = new JSONObject(response);
		
		String token = json.getString("access_token");
		String type = json.getString("token_type");
		
		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		response = common.requestAPI(url.toString(), type + " " + token);
//		response = common.requestAPI(url.toString(), "Bearer" + token); 
		json = new JSONObject(response);
		
		
		MemberVO vo = new MemberVO();
		vo.setSocial("K");
		vo.setUserid(json.get("id").toString());
		
		JSONObject kakao_account = json.getJSONObject("kakao_account");
		vo.setName(hasKey(kakao_account, "name", "생략"));
		vo.setEmail(hasKey(kakao_account, "email"));
		vo.setGender(hasKey(kakao_account, "gender").equals("male") ? "남" : "여");
		vo.setPhone(hasKey(kakao_account, "phone_number"));
		
		JSONObject profile = kakao_account.getJSONObject("profile");
		vo.setProfile(hasKey(profile,"profile_image_url"));
		
		if( ! hasKey(profile, "nickname").isEmpty()) { //nickname 값이 있는 경우
		     vo.setName(hasKey(profile, "nickname", vo.getName()));
		}
		
		if(service.member_info(vo.getUserid()) == null) {
			service.member_join(vo);
		}else {
			service.member_update(vo);
		}
		
		session.setAttribute("loginInfo", vo);
		return "redirect:/";
		
		
		
	}

	
	
	// 네이버 콜백처리
	@RequestMapping("/naverCallback")
	public String naverCallback(String code, String state, HttpSession session) {
		String storedState = (String) session.getAttribute("state");
		if (code == null || !state.equals(state))
			return "redirect:/";

		// 접근 토큰 발급 요청
		// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		// &client_id=jyvqXeaVOVmV
		// &client_secret=527300A0_COq1_XV33cf
		// &code=EIc5bFrl4RibFls1
		// &state=9kgsGTfH4j7IyAkg

		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(NAVER_ID);
		url.append("&client_secret=").append(NAVER_SECRET);
		url.append("&code=").append(code);
		url.append("&state=").append(state);

		String response = common.requestAPI(url.toString());
		// 문자열 --> JSON

		JSONObject json = new JSONObject(response);
		String token = json.getString("access_token");
		String type = json.getString("token_type");

		// 접근 토큰을 이용하여 프로필 API 호출하기
		url = new StringBuffer("https://openapi.naver.com/v1/nid/me");
		response = common.requestAPI(url.toString(), type + " " + token);
		json = new JSONObject(response); 
		if(json.getString("resultcode").equals("00")) {
			json = json.getJSONObject("response");
			MemberVO vo = new MemberVO();
			vo.setSocial("N"); //N:네이버, K:카카오
			vo.setUserid( json.getString("id"));
			//별칭이 있으면 별칭을, 없으면 이름을 name 필드에 담자
			vo.setName(hasKey(json, "nickname"));
			if(vo.getName().isEmpty()) {
				vo.setName(hasKey(json, "name", "아무개"));
			}
			vo.setEmail(hasKey(json, "email"));
			vo.setProfile(hasKey(json, "profile_image"));
			vo.setGender(hasKey(json, "gender", "M").equals("M") ? "남" : "여"); // M/F --> 남/여
			vo.setPhone(hasKey(json, "mobile"));
			
			// DB에 네이버로그인 정보 저장하기 - 존재여부를 확인하여 신규/변경 저장
			if(service.member_info(vo.getUserid())==null) {
				service.member_join(vo);
			}else {
				service.member_update(vo);
			}
			session.setAttribute("loginInfo", vo);
		}
		
		/*
		  { 
		   "resultcode": "00", 
		   "message": "success",
		   "response": { 
		   "email": "openapi@naver.com",
		   "nickname": "OpenAPI",
		   "profile_image":"https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
		   "age":"40-49",
		   "gender": "F", 
		   "id": "32742776", 
		   "name": "오픈 API", 
		   "birthday":"10-01"
		    }
		  }
		 */

		return "redirect:/";

	}
	
	private String hasKey(JSONObject json, String key) {
		return json.has(key) ? json.getString(key) : "";
	}
	
	// 기본 값을 지정해야하는 경우
	private String hasKey(JSONObject json, String key, String value) {
		return json.has(key) ? json.getString(key) : value;
	}

	// 로그아웃 처리 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		MemberVO login = (MemberVO) session.getAttribute("loginInfo");
		session.removeAttribute("loginInfo");
		/*
		curl -v -X GET "https://kauth.kakao.com/oauth/logout?
		client_id=${YOUR_REST_API_KEY}
		&logout_redirect_uri=${YOUR_LOGOUT_REDIRECT_URI}"
		*/
		String social = login.getSocial();
		if(social!=null && social.equals("K")) {
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/logout");
		url.append("?client_id=").append(KAKAO_ID);
		url.append("&logout_redirect_uri=").append(common.appURL(request));
		return "redirect:" + url.toString();
		}else
		return "redirect:/";
	}

	// 새 비밀번호 변경저장 처리 요청
	@ResponseBody
	@RequestMapping("/updatePassword")
	public boolean update(MemberVO vo) {
		// 화면에서 입력한 새 비밀번호가 DB에 변경저장
		vo.setUserpw(pwEncoder.encode(vo.getUserpw()));
		return service.member_resetPassword(vo) == 1 ? true : false;
	}

	// 현재 비밀번호 확인 요청
	// @ResponseBody : 화면을 통한 응답이 아닌 현재 메소드 자체가 응답
	@ResponseBody
	@RequestMapping("/confirmPassword")
	public int confirm(String userpw, String userid) {
		// 화면에서 입력한 현재 비밀번호가 DB에 있는지 확인
		MemberVO vo = service.member_info(userid);
		return pwEncoder.matches(userpw, vo.getUserpw()) ? 0 : 1;
	}

	// 비밀번호 변경처리 요청
	@RequestMapping("/changePassword")
	public String change(HttpSession session) {
		MemberVO vo = (MemberVO) session.getAttribute("loginInfo");
		if (vo == null) {
			return "redirect:login";
		} else {
			return "member/change";
		}
	}

	// 비밀번호 찾기 처리요청
	@ResponseBody
	@RequestMapping(value = "/resetPassword", produces = "text/html; charset=utf-8")
	public String reset(MemberVO vo) {
		// 화면에서 입력한 아이디/이메일이 일치하는 회원에게 임시 비번을 발급.
		String name = service.member_user_id_email(vo);
		StringBuffer msg = new StringBuffer("<script>");
		if (name == null) {
			msg.append("alert=('아이디나 이메일이 맞지 않습니다. \\n확인하세요!');");
			msg.append("location='findPassword'");
		} else {
			vo.setName(name);
			// 임시 비번을 생성한 후 회원정보 DB의 회원정보로 저장 임시비번을 이메일로 보내준다.
			String pw = UUID.randomUUID().toString();
			pw = pw.substring(pw.lastIndexOf("-") + 1);
			vo.setUserpw(pwEncoder.encode(pw)); // 암호화된 임시비번

			if (service.member_resetPassword(vo) == 1 && common.sendPassword(vo, pw)) {
				msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요');");
				msg.append("location='login'"); // 임시비번으로 로그인하도록 로그인화면 연결
			} else {
				msg.append("alert('임시 비밀번호가 발급 실패');");
				msg.append("history.go(-1)");
			}
		}
		msg.append("</script>");
		return msg.toString();
	}

	// 비밀번호 찾기 화면요청
	@RequestMapping("/findPassword")
	public String find() {
		return "default/member/find";
	}

	// 로그인 처리요청
	@RequestMapping(value = "/smartLogin")
	public String login(String userid, String userpw, HttpSession session, RedirectAttributes redirect) {
		// 화면에서 입력한 아이디, 비번이 일치하는 회원정보가 DB에 있는지 확인
		// 입력한 아이디에 해당하는 회원정보 조회
		MemberVO vo = service.member_info(userid);
		boolean match = false;
		if (vo != null) { // 아이디가 일치하는 회원정보가 있고
			match = pwEncoder.matches(userpw, vo.getUserpw()); // 비번일치여부 확인
		}
		if (match) {
			session.setAttribute("loginInfo", vo);
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("fail", true);
			return "redirect:login"; // 로그인화면 다시 요청
		}
	}

	/*
	 * @ResponseBody @RequestMapping(value="/smartLogin",
	 * produces="text/html; charset=utf-8") public String login(String userid,
	 * String userpw, HttpSession session, HttpServletRequest request) { //화면에서 입력한
	 * 아이디, 비번이 일치하는 회원정보가 DB에 있는지 확인 //입력한 아이디에 해당하는 회원정보 조회 MemberVO vo =
	 * service.member_info(userid); boolean match = false; if( vo != null) { //아이디가
	 * 일치하는 회원정보가 있고 match = pwEncoder.matches(userpw, vo.getUserpw()); //비번일치여부 확인
	 * }
	 * 
	 * StringBuffer msg = new StringBuffer("<script>"); if(match) { //로그인되는 경우
	 * session.setAttribute("loginInfo", vo); // 세션에 로그인한 회원정보 담기
	 * msg.append("location='").append(common.appURL(request)).append("'"); }else {
	 * //로그인되지 않는 경우 msg.append("alert('아이디나 비밀번호가 일치하지 않습니다!'); history.go(-1); ");
	 * } msg.append("</script>");
	 * 
	 * return msg.toString(); }
	 */
	@Autowired
	private CommonUtility common;

	// 로그인 화면요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
}
