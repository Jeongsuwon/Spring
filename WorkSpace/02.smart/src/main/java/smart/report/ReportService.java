package smart.report;

import java.util.List;

import smart.common.PageVO;

public interface ReportService {
	
	int report_regist(ReportVO vo);	//공지글 신규 저장
	List<ReportVO> report_list();	//공지글 목록조회
	PageVO report_list(PageVO page);	//해당 페이지의 공지글 목록조회
	ReportVO report_info(int id);	//공지글 정보조회
	int report_update(ReportVO vo);	//공지글 정보수정, 저장
	int report_delete(int id);		//공지글 삭제
	int report_read(int id); 		//공지글 정보 조회수 증가처리
}
