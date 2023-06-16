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
		<jsp:include page="/WEB-INF/views/include/modal_alert.jsp"/>
		
		<input type="hidden" id="userid" value="${loginInfo.userid}">
		<c:set var="now" value="<%=new java.util.Date().getTime()%>"/>
		<script src="<c:url value='/js/member.js?${now }'/>"></script>
		<script>
		//비밀번호 변경 성공시 확인버튼 클릭하면 홈으로
		$('#modal-alert .btn-ok.btn-success').click(function(){
			if($(this).hasClass('btn-success')){
			location = '<c:url value="/"/>';
			}
		})
		
		//새 비밀번호 변경저장처리
		function resetPassword(){
			$.ajax({
				url: 'updatePassword',
				data: {userid: $('#userid').val(), userpw: $('[name=userpw]').val()}
			}).done(function(response){
				if(response){
					modalAlert('success', "비밀번호변경", "비밀번호가 변경되었습니다");
				}else{
					modalAlert('warning', "비밀번호변경", "비밀번호가 변경실패했습니다.");					
				}
				new bootstrap.Modal($('#modal-alert')).show();
			})
		}
		
		$('#btn-save').on('click', function(){
			if(tagIsValid()){
				// 입력 현재 비밀번호가 DB에 있는 비밀번호와 같은지 확인
				
				$.ajax({
						url: 'confirmPassword',
						data: { userpw: $('[name=currentpw]').val(), userid: $('#userid').val()},
				}).done(function(response){
// 					console.log(response)
					response = 0;
					if(response == 1){
						alert('현재 비밀번호가 일치하지 않습니다')
						$('[name=currentpw]').val('').focus();
					}else{
						if($('[name=currentpw]').val()==$('[name=userpw]').val()){
							alert('현재 비밀번호와 동일합니다');
							$('[name=userpw]').focus();
				}else{
					resetPassword();
				}
					}
				}).fail(function(xhr){
					console.log(xhr.statusText + ':' + xhr.status + '\n' + xhr.responseText)
				})
				
				/*
					$.ajax({
						url: 'confirmPassword',
						data: { userpw: $('[name=currentpw]').val()},
						success: function(response){
						},error: function(){
							
						} 
						}
					})
					*/
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
					if( ! status.is){
						alert('비밀번호 변경 불가\n' + status.desc)
						$(this).focus();
						ok = false;
						return ok;
					}
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