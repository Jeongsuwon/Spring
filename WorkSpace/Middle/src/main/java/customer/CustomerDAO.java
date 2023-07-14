package customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO {
	@Autowired @Qualifier("hanul") SqlSession sql;
	
	public List<CustomerVO> getList(){
		List<CustomerVO>  list = sql.selectList("cu.list");
		return list;
	}
	public void delete(int id) {
		int result = sql.delete("cu.delete", id);
		System.out.println("성공여부 : " + result);
	}
	
	public void insert(CustomerVO vo){
		int result = sql.insert("cu.insert", vo);
		System.out.println("성공여부 : " + result);
		
	}
	
	public void update(int id) {
		int result = sql.update("cu.update", id);
		System.out.println("성공여부 : " + result);
	}
}
