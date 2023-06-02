<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">고객목록</h3>
<table class="tb-list">
<colgroup>
<col>
<col width="300px">
<col width="400px">
</colgroup>
<tr>
<th>고객명</th>
<th>전화번호</th>
<th>이메일</th>
</tr>

<%-- 
<%
for (int i=1; i<=10; i++){
	System.out.print(i);
}
String[] names = {"홍길동", "박문수", "심청"};
for(int idx=0; idx<names.length; idx++){
	System.out.print(names[idx]);
}

for(String name : names){
	System.out.print(name);
}
%>
--%>

<c:forEach items="${list}" var="vo" >
<tr>
<td>${vo.name }</td>
<td>${vo.phone }</td>
<td>${vo.email }</td>
</tr>
</c:forEach>
<c:if test="${empty list }">
<tr>
<td colspan='3'>고객목록이 없습니다.</td>
</tr>
</c:if>

</table>

</body>
</html>