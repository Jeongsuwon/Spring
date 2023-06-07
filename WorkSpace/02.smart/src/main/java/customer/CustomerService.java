package customer;

import java.util.List;

public interface CustomerService {
        //DAO   	
		//DTO/VO
		// CRUD처리
		void customer_insert(CustomerVO vo);// 신규 고객정보 저장
		List<CustomerVO> customer_list();// 고객목록 조회 -> 고객 전체에 대해서
		List<CustomerVO> customer_list(String name);
		CustomerVO customer_info(int id);// 고객 상세정보 조회 -> 고객 한 명에 대해서
		void customer_update(CustomerVO vo); // 고객정보 수정 저장
		void customer_delete(int id);// 고객정보 삭제
		
}
