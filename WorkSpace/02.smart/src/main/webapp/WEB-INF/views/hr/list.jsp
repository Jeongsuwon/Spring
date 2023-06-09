<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">사원목록</h3>
	<form method="post" action="list">
	<div class="row my-3">
	<div class="col-auto d-flex align-items-center">
	<label class="me-3">부서명</label>
	<select name="department_id" class="form-select col" onchange="submit()">
		<option value="-1">전체</option>
		<c:forEach items="${departments }" var="d">
		<option value="${d.department_id }" ${d.department_id eq department_id ? 'selected' : '' } >${d.department_name }</option>		
		</c:forEach>
		
	</select>
	</div>
	</div>
	</form>
	
	<table class="tb-list">
	<colgroup>
	<col width="80px"> <col width="250px"> <col width="300px"> <col width="300px"> <col width="140px">
	</colgroup>
	<tr>
	<th>사번</th>
	<th>사원명</th>
	<th>부서</th>
	<th>업무</th>
	<th>입사일자</th>
	</tr>
	<!-- 사원정보가 없는 경우 -->
	<c:if test="${empty list }">
	<tr><td colspan='5'>사원정보가 없습니다.</td></tr>
	</c:if>
	<!-- 사원정보가 있는 경우 -->
	<c:forEach items="${list }" var="vo">
	<tr>
	<td>${vo. employee_id }</td>
	<td><a class="text-link" href="info?id=${vo.employee_id }">${vo. name}</a></td>
	<td>${vo. department_name }</td>
	<td>${vo. job_title }</td>
	<td>${vo. hire_date }</td>
	</tr>
	</c:forEach>
	</table>
</body>
</html>