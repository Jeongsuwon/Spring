<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="row justify-content-center h-100 align-items-center">
		<div class="col-md-9 col-lg-7 col-xl-5">
			<div class="card shadow-lg border-0 rounded-lg mt-5">
				<h3 class="text-center">
						<a href="<c:url value='/'/>"><img src="<c:url value='/img/hanul.logo.png'/>"></a>
				</h3>
				<h4 class="text-center">비밀번호찾기</h4>
				<div class="card-body">
					<form method="post" action="resetPassword">
						<div class="form-floating mb-3">
							<input class="form-control" type="text" name="userid" required
								placeholder="아이디">
								 <label>아이디</label>
						</div>
						<div class="form-floating mb-3">
							<input class="form-control" name="email" required type="text"
								placeholder="이메일"> 
								<label>이메일</label>
						</div>
						<div
							class="d-flex gap-3 justify-content-center">
							<a class="btn btn-primary form-control py-3" onclick="$('form').submit()">확인</a>
							<a class="btn btn-outline-primary form-control py-3" href="login">취소</a> 
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>