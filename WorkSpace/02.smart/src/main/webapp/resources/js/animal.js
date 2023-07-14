/**
 * 유기동물 관련 처리
 */
 
 //사진 크게 보기
 $(document)
 .on('click', '.popfile img', function(){
	$('#modal-image .modal-body').html($(this).clone());
	$('#modal-image .modal-body img').removeAttr('style');
	new bootstrap.Modal($('#modal-image')).show();
})
.on('change', '#upkind', function(){
	//축종 변경시 해당 품종 조회하기
	animal_kind();
	animal_list(1);
	
})

//축종에 대한 품종 조회
function animal_kind(){
	$('#kind').remove();
	$.ajax({
		url: 'animal/kind',
		data: {upkind: $('#upkind').val()},
	}).done(function(response){
		$('#upkind').after(response)
	})
}


 
 //축종태그만들기
 function animal_type(){
	
	var type =`
		<select class="form-select" id="upkind">
			<option value=''>축종선택</option>
			<option value='417000'>개</option>
			<option value='422400'>고양이</option>
			<option value='429900'>기타</option>
		</select>
	`;
	$('.animal-top').append(type);
	//$('.animal-top').prepend(type);
}
	
 //시도 조회
	function animal_sido(){
		$.ajax({
			url: 'animal/sido'
		}).done(function(response){
			$('.animal-top').append(response);
			animal_type();
		})
	}
 
 
 //유기동물목록 조회
function animal_list(pageNo){
	$('.pharmacy-top').addClass('d-none');
	$('#data-list').empty();
	$('.pagination').closest('nav').remove(); //페이지목록이 이미 있으면 삭제
	$('.loading').show();
	
	if($('#sido').length==0) animal_sido(); //시도 조회
	
	page.curPage = pageNo;
	var animal = page; //요청하는 페이지 번호, 페이지당 보여질 목록 수
	animal.sido = $('#sido').length == 0 ? '' : $('#sido').val();
	animal.sigungu = $('#sigungu').length == 0 ? '' : $('#sigungu').val();
	animal.shelter = $('#shelter').length == 0 ? '' : $('#shelter').val();
	animal.upkind = $('#upkind').length == 0 ? '' : $('#upkind').val();
	animal.kind = $('#kind').length == 0 ? '' : $('#kind').val();
 
		
	$.ajax({
		//url: '<c:url value="/data/animal/list"/>',
		url: 'animal/list',
		data: JSON.stringify(animal),
		type: 'post',
		contentType: 'application/json',
		//data:{pageNo: pageNo, rows: page.pageList},
		async: false,
	}).done(function(response){
		$('#data-list').html(response);
	})
	setTimeout(function(){$('.loading').hide()}, 500);
}