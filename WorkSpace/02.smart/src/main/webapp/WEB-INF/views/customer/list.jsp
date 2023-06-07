<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
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
	<h3 class="my-4">고객목록</h3>
	
	<div class="row my-3 justify-content-between">
		<div class="col-auto">
		<form method="post" action="list.cu">
			<div class="input-group">
				<label class="col-form-label me-3">고객명</label> 
				<input type="text" name="name" value="${name }" class="form-control">
				<button class="btn btn-primary px-3"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
		</form>
		</div>
		<div class="col-auto">
			<button class="btn btn-primary" onclick="location='new.cu'">고객등록</button>
		</div>
	</div>

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

		<c:forEach items="${list}" var="vo">
			<tr>
				<td><a class="text-link" href="info.cu?id=${vo.id }">${vo.name }</a></td>
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