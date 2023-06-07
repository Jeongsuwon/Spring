package customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
	// 컨트롤러 : @Controller (프레젠테이션 레이어, 웹 요청과 응답을 처리함)
	// 로직 처리 : @Service (서비스 레이어, 내부에서 자바 로직을 처리함)
	// 외부I/O 처리 : @Repository (퍼시스턴스 레이어, DB나 파일같은 외부 I/O 작업을 처리함)
	// 자동 주입 : @Autowired
public class CustomerDAO implements CustomerService {
	
	@Autowired private SqlSession sql;
	
	
	// 생성된 객체(bean으로 등록된 객체들)의 주소를 스프링 container에 관리가 됨
	// IoC(Inversion of Control): 개발자가 필요할 때마다 new로 객체를 생성 -> 스프링프레임워크가 객체를 생성
	// DI(Dependency Injection): 객체의 주소를 담아주는(주입) 처리
	
	// 필드에 데이터 담는 방법 2가지
	//(1) 생성자
//	public CustomerDAO(SqlSession sql) {
//		this.sql = sql;
//	}
	
	@Override
	public void customer_insert(CustomerVO vo) {
		 sql.insert("customer.insert", vo);
		
	}

	@Override
	public List<CustomerVO> customer_list() {
		// TODO Auto-generated method stub
		return sql.selectList("customer.list");
	}
	@Override
	public List<CustomerVO> customer_list(String name) {
		// TODO Auto-generated method stub
		return sql.selectList("customer.list", name);
	}

	@Override
	public CustomerVO customer_info(int id) {
		// TODO Auto-generated method stub
		return sql.selectOne("customer.info", id);
	}

	@Override
	public void customer_update(CustomerVO vo) {
		sql.update("customer.update", vo);
	}

	@Override
	public void customer_delete(int id) {
		sql.delete("customer.delete", id);
	}

}
