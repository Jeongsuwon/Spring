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
	<h3 class="my-4">방명록 수정</h3>
	<form method="post" enctype="multipart/form-data" action="update">
		<table class="tb-row">
			<colgroup>
				<col width="180px">
				<col>
			</colgroup>
			<tr>
				<th>제목</th>
				<td><input type="text" value="${fn: escapeXml(vo.title) }" name="title"
					class="check-empty form-control" title="제목"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" class="check-empty form-control"
						title="내용">${vo.content }</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<div>
						<label>
							<input type="file" name="file" class="form-control" id="file-multiple" multiple>
							<i role="button" class="fs-3 fa-solid fa-upload"></i>
							</label>
					</div>
					<!-- 마우스 드래그 드랍으로 파일첨부 처리되게 -->
					<div class="form-control mt-2 py-2 file-drag">
						<!-- 첨부파일이 없는 경우 -->
						<c:if test="${empty vo.fileList }">
							<div class="text-center py-3"></div>
						</c:if>
						<!-- 첨부파일이 있는 경우 -->
						<c:forEach items="${vo.fileList }" var="file" varStatus="s">
							<div class="file-item d-flex align-items-center gap-2">
								<button type="button" class="btn-close small" data-seq="${s.index }"></button>
								<span class="file-name">${file.filename }</span>
							</div>
						</c:forEach>		
						<div class="text-center py-3">첨부할 파일을 마우스로 끌어오세요</div>
						
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="writer" value="${loginInfo.userid }">
			<input type="hidden" name="curPage" value="${page.curPage}" >
			<input type="hidden" name="search" value="${page.search}" >
			<input type="hidden" name="keyword" value="${page.keyword}" >
			<input type="hidden" name="viewType" value="${page.viewType}" >
			<input type="hidden" name="pageList" value="${page.pageList}" >
			<input type="hidden" name="id" value="${vo.id }" >
			<!-- 삭제한 첨부파일 id 목록 -->
			<input type="hidden" name="removed">

	</form>
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-primary px-4" id="btn-save">저장</button>
		<button class="btn btn-danger px-4" id="btn-cancle">취소</button>
	</div>

	<script>
	var fileList = new FileList();
	
	<c:forEach items="${vo.fileList }" var="f">
		fileList.setFile(urlTofile("${f.filepath}", "${f.filename}"),${f.id})
	</c:forEach>
		console.log(fileList)
		
// 	물리적인 파일정보를 읽어와 파일정보를 담도록 한다
function urlTofile(url, filename){
			//함수1 호출
			
			var file;
			$.ajax({
				url: url,
				responseType: 'blob',
				async: false, //ajax: 기본 비동기(true), 동기처리시 false
			}).done(function(response){
				var blob = new Blob([response]);
				file = new File([blob], filename)
			})
			return file;
		}
	
	//백틱(``) 사용하기: 변수 값을 +로 연결하지 않고 표현식을 사용한다.
// 	var name = "홍길동" // 이름은 홍길동 입니다
// 	console.log("이름은 " + name + "입니다")
// 	console.log(`이름은  \${name} 입니다`)
	
	
	/*
		$('.file-drag').on('dragover dragleave drop', function(e){
			e.preventDefault();
			//드래그오버시 입력태그에 커서가 있을 때처럼 보여지게
			if(e.type=='dragover') $(this).addClass('drag-over')
			else 				   $(this).removeClass('drag-over')
		})	
	*/
	
		$('#btn-cancle').on('click',function(){
			$('form').attr('action', 'info').submit()	
		})
		
		$('#btn-save').on('click', function() {
			if (emptyCheck()) {
				multipleFileUpload();
				$('[name=removed]').val(fileList.info.removeId)
				$('form').submit()
			}
		})
	</script>

</body>
</html>