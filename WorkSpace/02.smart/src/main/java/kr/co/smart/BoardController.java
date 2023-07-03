package kr.co.smart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smart.board.BoardDAO;
import smart.board.BoardVO;
import smart.common.CommonUtility;
import smart.common.PageVO;
import smart.member.MemberDAO;
import smart.member.MemberVO;


@Controller @RequestMapping("/board")
public class BoardController {
	@Autowired private BoardDAO service;
	@Autowired private CommonUtility common;

	
	//방명록 신규저장처리 요청
	@RequestMapping("/register")
	public String register(BoardVO vo, MultipartFile file[], HttpServletRequest req) {
		//화면에서 입력한 정보를 DB에 신규저장한 후 응답화면연결 - 목록
		//첨부된 파일목록을 BoardVO에 담기
		vo.setFileList(common.attachedFiles("board", file, req));
		
		service.board_register(vo);
		return "redirect:list";
	}
	
	//방명록 신규입력화면 요청
	@RequestMapping("/new")
	public String board() {
		return "board/new";
	}
	
	
	@Autowired private MemberDAO member;
	@Autowired private BCryptPasswordEncoder pw;
	//방명록 목록화면 요청
	@RequestMapping("/list")	
	public String list(HttpSession session, PageVO page, Model model) {
		// 임시 로그인 처리(테스트 후 삭제 or 주석)
					String userid = "kim";
					String userpw = "aA123";
					MemberVO login =  member.member_info(userid);
					if(pw.matches(userpw, login.getUserpw())) {
						session.setAttribute("loginInfo", login);
					}
				//-------------------------------------
						
				
				model.addAttribute("page", service.board_list(page));	
		
		
					session.setAttribute("category", "bo");
		return "board/list";
	}
	
}
