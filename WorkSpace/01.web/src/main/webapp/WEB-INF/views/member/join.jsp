<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div><a href="<c:url value='/'/>">홈으로</a></div>
	<h3>회원가입</h3>
	<form method="post" action="joinRequest">
	<table border='1'>
	<tr>
	<th>성명</th>
	<td><input type='text' name='name'></td>
	</tr>
	<tr>
	<th>성별</th>
	<td>
	<input type='radio' name="gender" value="남">남 <!-- radio, checkbox는 value 값 필요 생략 시 on으로 표기 -->
	<input type='radio' name="gender" value="여" checked>여	
	</td>
	</tr>
	<tr>
	<th>이메일</th>
	<td><input type='text' name='email'></td>
	</tr>
	</table>
	<input type="submit" value="회원가입">
	</form>
</body>
</html>