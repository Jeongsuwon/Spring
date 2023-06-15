<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<table class="tb-row">
		<colgroup>
		<col width="180px">
		<col>
		</colgroup>
		<tr>
		<th>현재 비밀번호</th>
		<td>
		<div class="row">
			<div class="col-auto">
				<input type="password" class="form-control" name="currentpw">
			</div>
		</div>		
		</td>
		</tr>
		<tr>
		<th>새 비밀번호</th>
		<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input type="password" class="form-control check-item" name="userpw">
			</div>
			<div class="col-auto desc"></div>
			<div class="mt-2">비밀번호는 영문 대/소문자, 숫자 조합 5자~10자</div>
		</div>		
		</td>
		</tr>
		<tr>
		<th>새 비밀번호 확인</th>
		<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input type="password" class="form-control check-item" name="userpw_ck">
			</div>
			<div class="col-auto desc"></div>
		</div>		
		</td>
		</tr>
		</table>
		
		<div class="btn-toolbar justify-content-center my-3">
			<button class="btn btn-primary px-4" id="btn-save">변경</button>
		</div>
		<script src="<c:url value='/js/member.js'/>"></script>
		<script>
// 		$('#btn-save').click(function(){
			
// 		})
		
		$('#btn-save').on('click', function(){
			if(tagIsValid()){
					
			}
		})
		
		//태그에 값을 유효하게 입력했는지
		function tagIsValid(){
			var ok = true;
			if($('[name=currentpw]').val()==""){
				alert("현재 비밀번호를 입력하세요!")
				$('[name=currentpw]').focus()
				ok = false
			}else{
				$('.check-item').each(function(){
					//비밀번호/확인 입력상태 체크 처리
					var status = member.tagStatus($(this))
					console.log('status>', status)
					console.log('status>', status.is)
					console.log('status>', status.desc)
				})
			}
			return ok;
}

$('.check-item').keyup(function(){
	member.showStatus($(this))
	
})
		
		</script>
</body>
</html>