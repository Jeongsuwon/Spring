/**
 * 공통 함수 선언 
 https://api.jqueryui.com/datepicker/
 */
 
 function modalAlert(type, title, message){
	$('#modal-alert .modal-title').html(title);
	$('#modal-alert .modal-body').html(message);
	
	//모달창 type에 따라 아이콘 모양, 색 지정을 위해 모든 클래스 삭제
	$('.modal-icon').removeClass('text-info text-warning text-danger text-primary text-success fa-circle-exclamation fa-circle-question')
	// 아니오/확인으로 사용되는 버튼의 색상 초기화
	$('.modal-footer .btn-ok').removeClass('btn-info btn-warning btn-danger btn-primary btn-success')
	
	
	if(type=='danger'){ //confirm에 해당
		//아니오, 예 버튼
		$('.modal-footer .btn-ok').addClass('btn-secondary').text('아니오')
		$('.modal-footer .btn-danger').removeClass('d-none')
		
		$('.modal-icon').addClass('fa-circle-question') //아이콘 물음표		
	}else{
		//확인 버튼, confirm일 때만 사용할 btn-danger는 필요 x
		$('.modal-footer .btn-ok').addClass('btn-' + type).text('확인')
		$('.modal-footer .btn-danger').addClass('d-none')
		
		$('.modal-icon').addClass('fa-circle-exclamation') //아이콘 느낌표	
	}
	$('.modal-icon').addClass('text-' + type);
};
 
 $(function(){
	var today = new Date();
	var range = today.getFullYear()-100 + ':' + today.getFullYear();
	$.datepicker.setDefaults({
		dateFormat: "yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		yearRange: range,
		showMonthAfterYear: true,
		monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		dayNamesMin:["일", "월", "화", "수", "목", "금", "토"],
		maxDate: today, 
	})
	$( ".date" ).datepicker();
	$( ".date" ).attr('readonly', true)
})