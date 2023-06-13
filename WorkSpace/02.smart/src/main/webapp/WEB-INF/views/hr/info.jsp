<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">사원정보</h3>
<table class="tb-row">
<colgroup>
<col width="180px">
</colgroup>
<tr><th>사번</th><td>${vo.employee_id }</td></tr>
<tr><th>사원명</th><td>${vo.name }</td></tr>
<tr><th>이메일</th><td>${vo.email }</td></tr>
<tr><th>전화번호</th><td>${vo.phone_number }</td></tr>
<tr><th>급여</th><td><fmt:formatNumber value="${vo.salary }" /> </td></tr>
<tr><th>입사일자</th><td>${vo.hire_date }</td></tr>
<tr><th>부서</th><td>${vo.department_name }</td></tr>
<tr><th>업무</th><td>${vo.job_title}</td></tr>
</table>


<div class="btn-toolbar my-3 gap-2 justify-content-center">
	<button class="btn btn-primary" onclick="location='list'">사원목록</button>
	<button class="btn btn-primary" onclick="location='modify?id=${vo.employee_id}'">정보수정</button>
	<button class="btn btn-primary" 
	onclick="if(confirm('사번[${vo.employee_id}] 사원정보를 삭제하시겠습니까?'))location='delete?id=${vo.employee_id}'">정보삭제(confirm)</button>
	<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-alert" >
  		정보삭제(modal버튼에 연결)</button> 
	<button class="btn btn-primary" onclick="showModal('success')">
  		정보삭제(modal success)</button> 
	<button class="btn btn-primary" onclick="showModal('danger')">
  		정보삭제(modal info)</button> 
</div>
<jsp:include page="/WEB-INF/views/include/modal_alert.jsp"></jsp:include>

<script>
function showModal(type){
	modalAlert(type, "사원정보삭제", "사번[${vo.name}] 사원정보를 삭제하시겠습니까?");
	new bootstrap.Modal($("#modal-alert")).show();
}

$('#modal-alert .btn-danger').click(function(){
	location='delete?id=${vo.employee_id}';
})

$(function(){
	modalAlert('danger', "사원정보삭제", "사번[${vo.name}] 사원정보를 삭제하시겠습니까?");
})
</script>
</body>
</html>