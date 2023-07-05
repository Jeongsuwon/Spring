<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">방명록 안내</h3>
	<table class="tb-row">
	<colgroup>
	<col width="180px"><col><col width="160px"><col width="160px"><col width="100px"><col width="100px">
	</colgroup>
		<tr>
			<th>제목</th>
			<td colspan="5">${vo.title }</td>
		</tr>
		
		<tr>
			<th>작성자</th>
			<td>${vo.name }</td>
			<th>작성일자</th>
			<td>${vo.writedate }</td>
			<th>조회수</th>
			<td>${vo.readcnt }</td>
		</tr>

		<tr>
			<th>내용</th>
			<td colspan="5">${fn: replace(vo.content, crlf, "<br>")}</td>
		</tr>

		<tr>
			<th>첨부파일</th>
			<td colspan="5">
				<c:forEach items="${vo.fileList }" var="file">
					<div class="row">
						<div class="col-auto gap-2 my-1 d-flex align-items-center">
							<span class="file-name">${file.filename }</span>
							<i role="button" data-file="${file.id }" class="file-download fs-3 fa-solid fa-download"></i>
						</div>
					</div>
				</c:forEach>
				
			</td>
		</tr>
	</table>
	
	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<a class="btn btn-primary" id="btn-list">방명록 목록</a>
		<a class="btn btn-primary" id="btn-modify">방명록 수정</a>
		<a class="btn btn-primary" id="btn-delete">방명록 삭제</a>
	</div>
	
	
	<form method="post">
		<input type="hidden" name="file" >
		<input type="hidden" name="curPage" value="${page.curPage}" >
		<input type="hidden" name="search" value="${page.search}" >
		<input type="hidden" name="keyword" value="${page.keyword}" >
		<input type="hidden" name="viewType" value="${page.viewType}" >
		<input type="hidden" name="pageList" value="${page.pageList}" >
		<input type="hidden" name="id" value="${vo.id}" >
	</form>
	<jsp:include page="/WEB-INF/views/include/modal_image.jsp"/>
	<jsp:include page="/WEB-INF/views/include/modal_alert.jsp"></jsp:include>
	<script>
	
	$('#btn-list, #btn-modify, #btn-delete').click(function(){
		var id = $(this).attr('id');
		id = id.substr(id.indexOf('-')+1);
		
		if( id=='delete'){
			modalAlert('danger', '방명록 삭제', '이 방명록 글을 삭제하시겠습니까?')
			new bootstrap.Modal('#modal-alert').show()
		}else{
			
		$('form').attr('action', id).submit()
		}
		
	})
	
	//모달창으로 삭제여부 confirm시 예 버튼 클릭 시에만 서브밋
	$('#modal-alert .btn-danger').click(function(){
		$('form').attr('action', 'delete').submit()
	})
	//폰트어썸으로 만들어진 버튼의 경우 동적으로 다시 만들어지므로
	//이벤트는 태그자체에 직접 등록했을 때 발생되지 않는다. -> 이벤트를 문서에 등록한다
	$(document).on('click', '.file-download', function(){
		$('[name=file]').val( $(this).data('file'))
		$('form').attr('action', 'download').submit()
	})
	
	//첨부파일이 이미지인 경우 미리보기 되게 처리
	<c:forEach items="${vo.fileList }" var="f" varStatus="s">
		if(isImage("${f.filename}")){
			$('.file-name').eq(${s.index}).after("<span class='file-preview'><img src='${f.filepath}'></span>");
			
		}
	</c:forEach>
	
	
	</script>
	
</body>
</html>