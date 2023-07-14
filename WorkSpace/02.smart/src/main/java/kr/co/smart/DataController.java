package kr.co.smart;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import smart.common.CommonUtility;

@Controller @RequestMapping("/data")
public class DataController {
	private String key
	="kmc0ZV1P4EDjawY1Lf6lvCWeZXhgexI8O3A59ZNccISKWGOaO4IIUn%2BTAO8dYHAhjcPOkkGwsioB7rC1UgPOCQ%3D%3D";
	
	private String animalURL = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/";
	
	@Autowired private CommonUtility common;
	
	//보호소 조회 요청
	@RequestMapping("/animal/shelter")
	public String animal_shelter(String sido, String sigungu, Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("shelter?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		url.append("&org_cd=").append(sigungu);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		
		return "data/animal/shelter";
	}
	
	//유기동물 시군구조회 요청
	@RequestMapping("/animal/sigungu")
	public String animal_sigungu(Model model, String sido) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("sigungu?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		
		return "data/animal/sigungu";
	}
	
	//유기동물 시도조회 요청
	@RequestMapping("/animal/sido")
	public String animal_sido(Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("sido?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&numOfRows=30");
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/sido";
	}
	
	
	//품종 조회 요청
	@RequestMapping("/animal/kind")
	public String animal_kind(String upkind, Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("kind?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&up_kind_cd=").append(upkind);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/kind";
	}
	
	//유기동물 조회 요청
	//jsp에서 보낸 json 파라미터는 바로 데이터 객체에 담을 수 없음.
	@RequestMapping("/animal/list")
	public Object animal_list(@RequestBody HashMap<String, Object> map, Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("abandonmentPublic?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(map.get("curPage"));
		url.append("&numOfRows=").append(map.get("pageList"));
		url.append("&upr_cd=").append(map.get("sido"));
		url.append("&org_cd=").append(map.get("sigungu"));		
		url.append("&care_reg_no=").append(map.get("shelter"));
		url.append("&upkind=").append(map.get("upkind"));
		url.append("&kind=").append(map.get("kind"));
		model.addAttribute("list", common.requestAPIResultInfo(url.toString()));
		return "data/animal/animal_list";
	}
	
	/*
	 * public Object animal_list(int pageNo, int rows, Model model) { StringBuffer
	 * url = new StringBuffer(animalURL);
	 * url.append("abandonmentPublic?serviceKey=").append(key);
	 * url.append("&_type=json"); url.append("&pageNo=").append(pageNo);
	 * url.append("&numOfRows=").append(rows); model.addAttribute("list",
	 * common.requestAPIResultInfo(url.toString())); return
	 * "data/animal/animal_list"; }
	 */
	
	//약국목록 조회요청
//	@ResponseBody @RequestMapping(value="/pharmacy", produces="application/text; charset=utf-8")
//	public String pharmacy_list() {
//		StringBuffer url
//		= new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
//		url.append("?ServiceKey=").append(key);
//		url.append("&_type=json");
//		return common.requestAPI(url.toString());
//	}
	@ResponseBody @RequestMapping("/pharmacy")
	public Object pharmacy_list(int pageNo, int rows, String name) throws Exception {
		StringBuffer url
		= new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append( pageNo );
		if(!name.isEmpty()) url.append("&yadmNm=").append(URLEncoder.encode(name, "utf-8"));
		url.append("&numOfRows=").append( rows );
		HashMap<String, Object> map =
				new Gson().fromJson(common.requestAPI(url.toString()), new TypeToken<HashMap<String, Object>>(){}.getType());
		
		return map;
	}
	
	//공공데이터 목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "da");
		return "data/list";
	}
}
