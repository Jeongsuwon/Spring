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
<div><a href="<c:url value='/member'/>">회원가입화면</a></div>
	<h3>회원정보[${method }]</h3>
	<div>성명: ${name }</div>
	<div>성별: ${gender }</div>
	<div>이메일: ${email }</div>
</body>
</html>