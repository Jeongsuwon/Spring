<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.input-group .form-select {
		flex:initial;
		 width:100px
		 }
		 
	 table {
	 	table-layout: fixed;
	 	 }
</style>
</head>
<body>
	<h3 class="my-4">방명록 목록</h3>
	<form method="post" action="list">
	<div class="row justify-content-between mb-3">
		<div class="col-auto">
			<div class="input-group">
				<select class="form-select" name="search">
					<option value="s1" ${page.search eq 's1' ? 'selected' : '' }>전체</option>
					<option value="s2" ${page.search eq 's2' ? 'selected' : '' }>제목</option>
					<option value="s3" ${page.search eq 's3' ? 'selected' : '' }>내용</option>
					<option value="s4" ${page.search eq 's4' ? 'selected' : '' }>작성자</option>
					<option value="s5" ${page.search eq 's5' ? 'selected' : '' }>제목+내용</option>
				</select>
				<input type="text" name="keyword" class="form-control" value="${page.keyword }">
				<button class="btn btn-primary px-3"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
		</div>

			<div class="col-auto">
				<div class="row justify-content-between d-flex">
				
					<!-- 한 페이지에 보여질 목록갯수 선택 -->
					<div class="col-auto ps-0">
						<select class="form-select" name="pageList">
							<c:forEach var="i" begin="1" end="5">
								<option value="${10*i }">${10*i }개씩</option>
							</c:forEach>
						</select>
					</div>
					
					<!-- 보기형태(리스트,그리드) 선택 -->
					<div class="col-auto ps-0">
						<select class="form-select" name="viewType">
								<option value="list">리스트</option>
								<option value="grid">그리드</option>
						</select>
					</div>
					<!-- 로그인된 경우에만 글 쓰기 가능 -->
					<c:if test="${! empty loginInfo }">
						<div class="col-auto">
							<a class="btn btn-primary" href="new">새글쓰기</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	<input type="hidden" name="curPage" value="1">
	<input type="hidden" name="id">
	</form>
	
	<c:if test="${page.viewType eq 'grid' }">
		<div class="row">
			<c:forEach items="${page.list }" var="vo">
            <div class="col-xl-3-20 col-md-6 mb-4">
                <div class="card">
                
                    <div class="card-body">
                    	<div class="text-truncate-v3">
                    	<a class="text-link" href="javascript:info(${vo.id })">${vo.title }</a>
                    	</div>
                    </div>
                    
                    <div class="card-footer">
                    	<div>${vo.name }</div>
                    <div class="d-flex align-items-center justify-content-between">
                    	<div>${vo.writedate }</div>
						<c:if test="${vo.filecnt gt 0}"> <i class="fa-solid fa-paperclip"></i></c:if>
                    </div>
                    </div>
                </div>
            </div>
			</c:forEach>
        </div>
	</c:if>
	
	<c:if test="${page.viewType eq 'list' }">
		<table class="tb-list">
		<colgroup>
			<col width="100px">
			<col>
			<col width="120px">
			<col width="120px">
			<col width="100px">
		</colgroup>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성날짜</th>
			<th>조회수</th>
		</tr>
		<c:if test="${empty page.list }">
			<tr>
				<td colspan="5">방명록 글이 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach items="${page.list }" var="vo">
			<tr>
				<td>${vo.no }</td>
				<td class="text-start text-truncate"><a class="text-link" href="javascript:info(${vo.id })"> ${vo.title }></a>
					<c:if
						test="${vo.filecnt gt 0}">
						<i class="fa-solid fa-paperclip"></i>
					</c:if>
				</td>
				<td>${vo.name }</td>
				<td>${vo.writedate }</td>
				<td>${vo.readcnt }</td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
	
	
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
	
	
	<script>
	//상세정보화면 요청
	function info(id){
		$('[name=id]').val(id)
		$('[name=curPage]').val(${page.curPage})
		$('form').attr('action', 'info').submit()
	}
	
	//조회 목록갯수 변경시
		$('[name=pageList], [name=viewType]').change(function(){
			//목록 갯수 변경시에는 총 페이지 수가 달라지므로 항상 1페이지에 위치해야 함.
			//보기형태 변경시는 현재 페이지가 유지되어야 함.
			if($(this).attr('name')=="viewType") $('[name=curPage]').val(${page.curPage})
			$('form').submit()
		})
	
		//현재 보기형태가 선택되어 있게
	$('[name=viewType]').val('${page.viewType}').prop('selected', true);
	
	//해당 목록갯수가 선택되어 있게
	$('[name=pageList]').val(${page.pageList}).prop('selected', true);
	</script>
	
</body>
</html>