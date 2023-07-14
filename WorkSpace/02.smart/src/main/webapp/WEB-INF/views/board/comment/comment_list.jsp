<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<c:if test="${empty list}">
	<!-- 댓글이 없는경우 -->
		<div class="col-md-10 content text-center">
			<div class="fs-5">등록된 댓글이 없습니다.</div>
			<div>첫 번째 댓글을 남겨주세요.</div>
		</div>
		</c:if>
		<c:forEach items="${list }" var="vo">
		<!-- 댓글 작성자의 프로필이미지 -->
		<c:choose>
			<c:when test="${empty vo.profile }">
				<c:set var="profile" value="<i class='font-profile fa-solid fa-user'></i>"/>
			</c:when>
			<c:otherwise>
				<c:set var="profile" value="<img class='profile' src='${vo.profile}'>" />
			</c:otherwise>
		</c:choose>
		
		<!-- 댓글이 있는경우 -->
		<div class="col-md-10 content border-bottom py-3" data-id="${vo.id }">
		
			<div class="d-flex align-items-center mb-2 justify-content-between">
				<div class="d-flex align-items-center mb-2">
					<span class="text-secondary me-2">${profile }</span>
					<span>${vo.name } [${vo.writedate } ]</span>
				</div>
				<c:if test="${loginInfo.userid eq vo.writer or loginInfo.admin eq 'Y'}">
				<div>
					<span class="title me-4 d-none">댓글수정 [<span class="writing"> 0 / 200 </span>]</span>
					<a class="btn btn-outline-info btn-sm btn-modify-save">수정</a>
					<a class="btn btn-outline-danger btn-sm btn-delete-cancel">삭제</a>
				</div>
				</c:if>
			</div>
			<div class="comment">${fn: replace(fn: replace(vo.content, lf, '<br>'), crlf, '<br>') }</div>
			<div class="d-none hidden">
			
			</div>
		</div>
		</c:forEach>
		
	
<script>
	/* 수정/저장버튼 클릭시 */
	$('.btn-modify-save').on("click", function(){
		var _content = $(this).closest('.content'); /* closest -> 찾는 요소를 발견할 때까지 자기 자신부터 부모 루트까지 순회 */
		if($(this).text()=='수정'){
		modifyStatus(_content);			
		}else{
			//변경입력한 댓글을 DB에 저장하도록 처리
			//Json데이터를 서버로 보낼 때 : post, 보내는 내용에 대해 json으로 지정
			$.ajax({
				type: 'post',
				contentType: 'application/json',
				url:'<c:url value="/board/comment/update"/>',
				data: JSON.stringify({id: _content.data('id'), content: _content.find('textarea').val()})
			}).done(function(response){
				console.log(response)
				alert(response.message)
				_content.find('.hidden').text(response.content);
				stayStatus(_content);
			});
		}
			
		
	})
	//변경모드 상태
	function modifyStatus(_content){
		//버튼은 저장/취소
		_content.find('.btn-modify-save').text('저장').removeClass('btn-outline-info').addClass('btn-primary');
		_content.find('.btn-delete-cancel').text('취소').removeClass('btn-outline-danger').addClass('btn-secondary');
		
		//.comment 안에 있던 댓글내용은 textarea에 보여지게
		var _comment = _content.find('.comment'); //댓글 내용이 있는 태그
		var comment = _comment.html().replace(/<br>/g, '\n'); //댓글 내용
		_comment.html(`<textarea class="form-control w-100">\${comment}</textarea>`);
		// _content.find('.title').text(comment.length).removeClass('d-none'); 입력글자수 보이게
		_content.find('.title').removeClass('d-none');
		_content.find('.writing').text(comment.length);
		
		_content.find('.hidden').html(`\${comment}`); //원래 댓글내용 그대로 담기
	}
	
	// 삭제/취소 버튼
	$('.btn-delete-cancel').click(function(){
		 var _content = $(this).closest('.content');
		 if($(this).text()=='취소'){
				 stayStatus(_content);
			 
		 }else{
			 //삭제클릭시
			 if(confirm('댓글을 삭제하시겠습니까?')){
/* 				 $.ajax({
					 url: '<c:url value="/board/comment/delete"/>',
					 data: {id: _content.data('id')}
				 }).done(function(){
					 commentList();
				 }) */
				 //댓글 삭제 후 해당 댓글태그만 삭제하는 경우
				 $.ajax({
					 url: '<c:url value="/board/comment/delete"/>',
					 data: {id: _content.data('id')}
				 }).done(function(response){
					 if(response){
						 _content.remove();
					 }
				 })
			 }
		 }
	})
	
	//가만있는 모드 상태
	function stayStatus(_content){
		//버튼은 수정/삭제
		_content.find('.btn-modify-save').text('수정').addClass('btn-outline-info').removeClass('btn-primary');
		_content.find('.btn-delete-cancel').text('삭제').addClass('btn-outline-danger').removeClass('btn-secondary');
		
		var _comment = _content.find('.comment'); //댓글 내용이 있는 태그
		var textarea = _content.find('.hidden').html().replace(/\n/g, '<br>');
		
		_comment.html(textarea);
		// _content.find('.title').text(comment.length).removeClass('d-none'); 입력글자수 보이게
		_content.find('.title').addClass('d-none'); //글자수 부분 안 보이게
		_content.find('.hidden').empty();
	}
	
	
	</script>
	