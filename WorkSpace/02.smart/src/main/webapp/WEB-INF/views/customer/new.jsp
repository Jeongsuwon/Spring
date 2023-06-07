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
	<h3 class="my-4">신규고객등록</h3>
	<form method="post" action="register.cu" autocomplete="off">
		<table class="tb-row">
			<colgroup>
				<col width="180px">
			</colgroup>
			<tr>
				<th>고객명</th>
				<td><div class="row">
						<div class="col-auto">
							<input type="text" name="name" class="form-control" required>
						</div>
					</div></td>
			</tr>
			<tr>
				<th>성별</th>
					<td>
				<div class="form-check form-check-inline">
  					<input class="form-check-input" type="radio" name="gender" id="gender" value="남">
  						<label class="form-check-label" for="gender"> <!-- label의 for과 input의 id가 동일해야함 -->
    						남
  						</label>
					</div>
				<div class="form-check form-check-inline">
				<label>
  					<input class="form-check-input" type="radio" name="gender" value="여" checked>여
				</label>
				</div>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<div class="row">
						<div class="col-auto">
							<input type="text" name="email" class="form-control">
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td>
					<div class="row">
						<div class="col-auto">
							<input type="text" name="phone" class="form-control">
						</div>
					</div>
				</td>
			</tr>

		</table>
		<div class="btn-toolbar gap-2 my-3 justify-content-center">
			<button class="btn btn-outline-success">저장</button>
			<button type="button" class="btn btn-outline-danger" onclick="location='list.cu'">취소</button>
		</div>
	</form>
</body>
</html>