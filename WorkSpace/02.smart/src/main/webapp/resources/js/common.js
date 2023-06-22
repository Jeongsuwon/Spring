/**
 * 공통 함수 선언 
 https://api.jqueryui.com/datepicker/
 */

function toPhone(tag) {
	var phone = tag.val().replace(/[^0-9]/g, '').replace(/[-]/g, ''); //숫자만 입력되게 처리 010-1234-4567 -> 01012344567
	if (phone.length > 1) { //2자리 이상 입력하면: 02, 062, 010

		// -가 들어가야 할 위치 계산
		// substr(시작, 길이) / substring(시작, 끝위치)
		var start = phone.substr(0, 2) == "02" ? 2 : 3, //시작 위치(국번) 
			middle = start + 4; // 두 번째 항목: 1234 - 무조건 4자리 

		// - 만들어서 넣기
		if (phone.length > middle) {
			phone = phone.substr(0, start) + "-" + phone.substr(start, 4) + "-" + phone.substr(middle, 4);
		} else if (phone.length > start) {
			phone = phone.substr(0, start) + "-" + phone.substr(start, 4) + "-";
		}
	}

	tag.val(phone);
}

function modalAlert(type, title, message) {
	$('#modal-alert .modal-title').html(title);
	$('#modal-alert .modal-body').html(message);

	//모달창 type에 따라 아이콘 모양, 색 지정을 위해 모든 클래스 삭제
	$('.modal-icon').removeClass('text-info text-warning text-danger text-primary text-success fa-circle-exclamation fa-circle-question')
	// 아니오/확인으로 사용되는 버튼의 색상 초기화
	$('.modal-footer .btn-ok').removeClass('btn-info btn-warning btn-danger btn-primary btn-success')


	if (type == 'danger') { //confirm에 해당
		//아니오, 예 버튼
		$('.modal-footer .btn-ok').addClass('btn-secondary').text('아니오')
		$('.modal-footer .btn-danger').removeClass('d-none')

		$('.modal-icon').addClass('fa-circle-question') //아이콘 물음표		
	} else {
		//확인 버튼, confirm일 때만 사용할 btn-danger는 필요 x
		$('.modal-footer .btn-ok').addClass('btn-' + type).text('확인')
		$('.modal-footer .btn-danger').addClass('d-none')

		$('.modal-icon').addClass('fa-circle-exclamation') //아이콘 느낌표	
	}
	$('.modal-icon').addClass('text-' + type);
};

//동적으로 만들어진 요소에 대해서는 document에 이벤트를 등록해야한다.
$(document).on('click', '.date + .date-delete', function(){
	$(this).css('display', 'none'); //삭제버튼 안 보이게
	$(this).prev('.date').val('');//날짜 태그의 값을 초기화
}).on('click', '#file-attach .file-delete', function(){
		$(this).addClass('d-none'); //삭제버튼 안 보이게
		console.log('1>', $('#file-single').val());
		var _preview = $('#file-attach .file-preview');
		$('input[type=file]').val(''); //첨부되어있던 이미지파일정보 없애기
		if(_preview.length>0) _preview.empty();//미리보기한 이미지 태그 없애기
		console.log('2>', $('#file-single').val());
		
	})

//파일이 이미지파일인지 확인
function isImage(filename){
	// abc.png, abc.jpg ...
	var ext = filename.substr(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ["png", "jpg", "bmp", "gif", "jpeg", "webp"];
	return imgs.indexOf(ext)==-1 ? false : true;
	
}

$(function() {
	//프로필 이미지 선택처리
	$('input#file-single').on('change', function(){
//		console.log($(this))
//		console.log(this.files)
		
		var _priview = $('#file-attach .file-preview');
		var _delete = $('#file-attach .file-delete');
		
		var attached = this.files[0];
		console.log(attached)
		
		if(attached){
			//이미지파일인지 확인
			if(isImage(attached.name)){
			    singleFile = attached; // 선택한 파일정보를 관리
				_delete.removeClass('d-none'); //삭제버튼 보이게
				// 미리보기 태그가 있을 때만(존재하지 않을 때는 길이 0)
				if(_priview.length > 0){
					_priview.html("<img>");
					
				var reader = new FileReader();
				reader.readAsDataURL(attached);
				reader.onload = function(e){
					_priview.children("img").attr("src", e.target.result);
//					_priview.children("img").attr("src", this.result);
				}
				}	
			}else{
				singleFile = ''; //이미지가 아닌 파일인 경우는 관리정보를 초기화
				//이전 선택되었던 이미지파일 처리
				_priview.empty();
				$(this).val('');//실제 file태그의 정보 초기화
				_delete.addClass('d-none');
				
			}
		}else{
			//파일선택 창에서 취소를 클릭한 경우: 어떤 처리도 하지 않는다.
			//파일정보는 관리된 singleFile 변수에 있다.
			
		}
		
	})
	

	
	$('.date').change(function(){
		$(this).next('.date-delete').css('display', 'inline')
	})
	
	$('[name=phone]').keyup(function() {
		toPhone($(this));
	})

	var today = new Date();
	var range = today.getFullYear() - 100 + ':' + today.getFullYear();
	if ($(".date").length > 0) {
		$.datepicker.setDefaults({
			dateFormat: "yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			yearRange: range,
			showMonthAfterYear: true,
			monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
			dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
			maxDate: today,
		})
		$(".date").datepicker();
		$(".date").attr('readonly', true)
	}
})