<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<div><a href="<c:url value='/first'/>">오늘날짜</a></div>
<div><a href="<c:url value='/second'/>">현재시각</a></div>
<div><a href="<c:url value='/now'/>">지금</a></div>
<div><a href="<c:url value='/member'/>">회원가입</a></div>
<div><a href="<c:url value='/login'/>">로그인</a></div>
</body>
</html>
