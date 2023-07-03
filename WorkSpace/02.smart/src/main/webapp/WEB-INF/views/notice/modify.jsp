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
	<h3 class="my-4">${vo.indent eq 0 ? '공지글' : '답글' }수정</h3>
	<form method="post" enctype="multipart/form-data" action="update">
		<table class="tb-row">
			<colgroup>
				<col width="180px">
				<col>
			</colgroup>
			<!-- escapeXml : xml 마크업 문자로 인식될 수 있는 문자는 제외  -->
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
					<div class="row">
						<div class="col-auto d-flex align-items-center gap-4">
							<label>
							<input type="file" name="file" class="form-control" id="file-single">
							<i role="button" class="fs-3 fa-solid fa-upload"></i>
							</label>
							<div class="d-flex gap-3 align-items-center" id="file-attach" >
								<span class="file-name">${vo.filename }</span>
								<i role="button" class="fs-3 ${empty vo.filename ? 'd-none' : ''  } file-delete text-danger fa-solid fa-file-excel"></i>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${vo.id }">
		<input type="hidden" name="filename">
		<input type="hidden" name="curPage" value="${page.curPage }">
		<input type="hidden" name="search" value="${page.search }">
		<input type="hidden" name="keyword" value="${page.keyword }">

	</form>
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-primary px-4" id="btn-save">저장</button>
		<button class="btn btn-danger px-4" type="button"
			onclick="history.go(-1)">취소</button>
	</div>

	<script>
	$(function(){
		singleFile = '';
	})
		$('#btn-save').on('click', function() {
			$('[name=filename]').val($('.file-name').text())
			if (emptyCheck()) {
				singleFileUpload();
				$('form').submit()
			}
		})
	</script>

</body>
</html>