package smart.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import smart.board.FileVO;
import smart.member.MemberVO;

@Service
public class CommonUtility {
	
	//첨부파일 여러개를 업로드하는 처리
	public ArrayList<FileVO> attachedFiles(String category, MultipartFile[] files, HttpServletRequest request) {
		ArrayList<FileVO> list = null;
		for(MultipartFile attached: files) {
			if(attached.isEmpty()) continue;
			if(list == null) list = new ArrayList<FileVO>();
			FileVO fileVO = new FileVO();
			fileVO.setFilename(attached.getOriginalFilename());
			fileVO.setFilepath(fileUpload(category, attached, request));
			list.add(fileVO);
		}
		return list;
	}
	
	//첨부파일 삭제: 디스크에 저장된 물리적 파일 삭제
	public void deleteFile(String filepath, HttpServletRequest request) {
		if(filepath != null) {
			filepath = filepath.replace(appURL(request), "d://app/"+request.getContextPath());
			File file = new File(filepath);
			if(file.exists()) file.delete();
		}
	}
	
	//파일다운로드
	public void fileDownload(String filename, String filepath, HttpServletRequest request, HttpServletResponse response) throws Exception {
			//filepath: http://localhost:8080/smart/upload/profile/2023/06/22/abc.png
			//appURL : http://localhost:8080/smart
		filepath = filepath.replace( appURL(request), "d://app/" + request.getContextPath());
		
		//다운로드 할 파일객체를 생성
		File file = new File(filepath);
		String mime = request.getSession().getServletContext().getMimeType(filename);
		response.setContentType(mime);
		
		//파일 IO : 읽기/쓰기 - 단위 문자 : reader / writer, 단위 byte: input/output
		
		
		//파일을 첨부해서 쓰기작업하기
		//파일명에 한글이 있따면 인코딩처리
		filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
		response.setHeader("content-disposition", "attachment; filename=" + filename);
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		
	}
	
	
	//파일업로드
	public String fileUpload(String category, MultipartFile file, HttpServletRequest request) {
////		D:\sts\WorkSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\02.smart\resources
//		String path = request.getSession().getServletContext().getRealPath("resources");
		//D:\\app\smart
		String path = "D:\\app" + request.getContextPath();
		String upload = "/upload/" + category + new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
		path += upload;
		
		//파일을 저장해둘 폴더가 없으면 폴더 만들기
		File folder = new File(path);
		if( ! folder.exists()) folder.mkdirs();
		
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(path, filename));
		} catch (Exception e) {
			
		} 
		// http://localhost:8080/smart/upload/profile/2023/06/22/abc.png
		return appURL(request) + upload + "/" + filename;
	}
	
	private void emailServerConnect(HtmlEmail email) {
		email.setHostName("smtp.naver.com"); //메일서버지정
		email.setAuthentication("아이디", "비밀번호");// 아이디/비번으로 로그인
		email.setSSLOnConnect(true);
	}
	private String EMAIL_ADDRESS = "아이디"; //아이디
		
	//이메일 보내기: 회원가입 축하메시지 전송
	public void sendWelcome(MemberVO vo, String welcomeFile) {
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true);
		
		//이메일 서버지정
		emailServerConnect(email);
		
		try {
			email.setFrom(EMAIL_ADDRESS, "스마트 웹&앱 관리자");
			email.addTo(vo.getEmail(), vo.getName());
			email.setSubject("한울 스마트 웹&앱 과정 가입 축하");
			
			StringBuffer content = new StringBuffer();
			content.append("<body>");
			content.append("<h3><a target='_blank' href='https://www.canva.com/'>한울 스마트 웹&앱 과정</a><h3>");
			content.append("<div>우리 과정에 참여하게 된 것을 환영합니다.</div>");
			content.append("<div>프로젝트 잘 마무리하시고 취뽀하시기를 바랍니다.</div>");
			content.append("<div>첨부된 파일을 확인하신 후 등교하세요.</div>");
			content.append("</body>");
			email.setHtmlMsg(content.toString());
			
			EmailAttachment file = new EmailAttachment();
			file.setPath(welcomeFile); //파일선택
			email.attach(file); //선택한 파일 첨부
			email.send(); //메일 보내기 버튼 클릭
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//이메일 보내기: 임시비밀번호 전송
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
	
	// API 요청
	public String requestAPI(String apiURL) {
		String response = "";
		try {
		      URL url = new URL(apiURL);
		      HttpURLConnection con = (HttpURLConnection)url.openConnection();
		      con.setRequestMethod("GET");
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      System.out.print("responseCode="+responseCode);
		      if(responseCode==200) { // 정상 호출
		        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		      } else {  // 에러 발생
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
		      }
		      String inputLine;
		      StringBuffer res = new StringBuffer();
		      while ((inputLine = br.readLine()) != null) {
		        res.append(inputLine);
		      }
		      br.close();
		      response = res.toString();
		      if(responseCode==200) {
		        System.out.println(res.toString());
		      }
		    } catch (Exception e) {
		      System.out.println(e);
		    }
		
		return response;
	}
	
	//공공데이터 API 요청결과 문자열을 Json으로 변환하고, 필요한 데이터 수집하는 메소드
	public Map<String, Object> requestAPIResultInfo(Object apiURL) {
		try {
			JSONObject json = new JSONObject( requestAPI(apiURL.toString()));
			json = json.getJSONObject("response");
			//response.body 내부에 있는 정보들만 필요함
			//해당 정보들(items,numOfRows,pageNo,totalCount) 만 map으로 담기
			//이중 실제 사용할 정보는 items,totalCount 
			if(json.has("body")) {
				
				return json.getJSONObject("body").toMap();
			}else {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("items", null);
				map.put("totalCount", 0);
				map.put("pageNo", 1);
				return map;
			}
			
		}catch (Exception e) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("items", null);
			map.put("totalCount", 0);
			map.put("pageNo", 1);
			return map;
		}
		
			
	}	
	
	
	public String requestAPI(String apiURL, String property) {
		String response = "";
		try {
		      URL url = new URL(apiURL);
		      HttpURLConnection con = (HttpURLConnection)url.openConnection();
		      con.setRequestMethod("GET");
		      //Authorization: {토큰 타입] {접근 토큰]
		      con.setRequestProperty("Authorization", property);
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      System.out.print("responseCode="+responseCode);
		      if(responseCode==200) { // 정상 호출
		        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		      } else {  // 에러 발생
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
		      }
		      String inputLine;
		      StringBuffer res = new StringBuffer();
		      while ((inputLine = br.readLine()) != null) {
		        res.append(inputLine);
		      }
		      br.close();
		      response = res.toString();
		      if(responseCode==200) {
		        System.out.println(res.toString());
		      }
		    } catch (Exception e) {
		      System.out.println(e);
		    }
		
		return response; // "{response: { body:{}, header:{} } }
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
