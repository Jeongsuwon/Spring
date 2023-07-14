package kr.com.hanul;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import customer.CustomerDAO;
import customer.CustomerVO;


@RestController
public class CustomerController {
	
	
	//json / xml
	//json <- String으로 되어있는데 key와 value가 존재하고 list같은 자료구조도 [] 등으로 표현이 가능한 데이터 타입.
	//요소 하나 (Object, DTO) -> 기호: {}, List-> [],
	//[{vo} .... {vo.lastindex}]
	
	@Autowired CustomerDAO dao ;
	
	@RequestMapping(value="/list.cu", produces="text/html;charset=utf-8")
	public String list(CustomerVO vo, String param) {
		System.out.println("여기까지 누가 왔음" + param);
		List<CustomerVO> list = dao.getList();
		Gson gson = new Gson();
		//Object(List, DTO등) -> String json으로 바꾸는 메소드 : toJson()
		return gson.toJson(list);
		
	}
	
	@RequestMapping(value="/insert.cu", produces="text/html;charset=utf-8")
	public String insert(CustomerVO vo) {
		dao.insert(vo);
		return "aa";
		
	}
	
	
	@RequestMapping(value="/obj.cu", produces="text/html;charset=utf-8")
	public String obj() {
		CustomerVO vo = new CustomerVO();
		vo.setEmail("email");
		vo.setName("이름");
		return new Gson().toJson(vo);
	}
	
	@RequestMapping(value="/delete.cu", produces="text/html;charset=utf-8")
	public String delete(int id) {
		dao.delete(id);
		return "aa";
	}
	
	@RequestMapping(value="/update.cu", produces="text/html;charset=utf-8")
	public String update(int id) {
		dao.update(id);
		return "aa";
	}
	

}
