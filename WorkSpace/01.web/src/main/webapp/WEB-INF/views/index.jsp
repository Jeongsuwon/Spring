<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="<c:url value='/'/>">홈으로</a>
	<h3>View</h3>
	<h3>Model 객체 사용</h3>
	오늘은 ${today } 입니다.
	<h3>ModelAndView 객체 사용</h3>
	현재시각은 ${now } 입니다.
</body>
</html>