package kr.com.hanul;

import org.springframework.stereotype.Component;

@Component(value="bean") 
public class TestBean {
	public TestBean() {
		System.out.println("@Autowired가 되었다. 나는 null 아니다.");
	}
}
