<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">공지글 답글쓰기</h3>
	<form method="post" enctype="multipart/form-data" action="reply_register">
		<table class="tb-row">
			<colgroup>
				<col width="180px">
				<col>
			</colgroup>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title"
					class="check-empty form-control" title="제목"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" class="check-empty form-control"
						title="내용"></textarea></td>
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
								<span class="file-name"></span>
								<i role="button" class="fs-3 d-none file-delete text-danger fa-solid fa-file-excel"></i>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="writer" value="${loginInfo.userid }">
		<!-- 원글관련정보 -->
		<input type="hidden" name="rid" value="${vo.id }">
		<input type="hidden" name="root" value="${vo.root }">
		<input type="hidden" name="step" value="${vo.step }">
		<input type="hidden" name="indent" value="${vo.indent }">
		<!-- 페이지관련정보 -->
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
		$('#btn-save').on('click', function() {
			if (emptyCheck()) {
				singleFileUpload();
				$('form').submit()
			}
		})
	</script>

</body>
</html>