/**
 * 공통 함수 선언 
 https://api.jqueryui.com/datepicker/
 */

//10MB를 넘는 파일을 걸러내는 처리
function filteredFile(files){
	var exist = false;
	//files는 객체, 값만 따로 뽑아본다
	console.log('1>', files)
	files = Object.values(files).filter(function(file){
		if(file.size > 1024*1024*10) exist = true;
		return file.size <= 1024*1024*10;
	})
	console.log('2>', files)
	
	if(exist){
		alert('10Mb 이상 첨부 불가')
	}
	
	return files;//걸러낸 10Mb 이하 파일들
	
}

//파일관리 객체 생성자 함수
function FileList(){
	this.files = [];
	this.info = { upload:[], fileId:[], removeId:[]}; //업로드 여부, 업로드된 파일의 id, 삭제할 파일 id
	// 이미 업로드된 파일: 처리할 게 없음
	// 새로 추가첨부한 파일: upload 해야 함
	// 이미 업로드되어 있던 파일을 삭제: DB + 물리적 파일 삭제 
	// new Object == {}, new Array == []
	this.setFile = function(file, id){
	//선택한 파일마다 alert가 뜬다
//		if(file.size > 1024*1024*10){
//			alert("10MB 이상의 파일은 첨부할 수 없습니다")
//			return;
//		}
		//두 번째 파라미터 id가 O일 때 업로드 안함(false)
		//드래그 드랍 또는 파일 태그로 선택하는 경우 id가 X일 때 업로드 해야 함(true)
		this.info.upload.push(typeof id == "undefined" ? true : false); // true: 업로드해야 함, false: 업로드 안함
		//두 번째 파라미터 id가 O : fileId에 담기
		if(typeof id != "undefined") this.info.fileId.push(id);
		this.files.push(file);
	}
	this.getFile = function(){
		return this.files;
	}
	this.removeFile = function(i){
		this.files.splice(i, 1);
		//업로드 여부 삭제
		this.info.upload.splice(i, 1);
		
		//이미 업로드 되어있는 파일삭제한 경우 삭제할 파일 id에 담기
		if( typeof this.info.fileId[i] != "undefined"){
			this.info.removeId.push( this.info.fileId[i]); //삭제대상 파일 id 추가
			this.info.fileId.splice(i, 1); //파일 id 목록에서도 삭제 
		}
	}
	//파일을 보여줄 함수
	this.showFile = function(){
		var tag = '';
		if(this.files.length > 0){
			for(var i=0; i<this.files.length; i++){
				tag += 
				`<div class="file-item d-flex gap-2 align-items-center my-1">
							<button type="button" class="btn-close small" data-seq="${i}"/>
							<span class="file-name">${this.files[i].name}</span>
						</div>`;
			}
		}else{
			tag = '<div class="text-center py-3">첨부할 파일을 마우스로 끌어오세요</div>';
		}
		$('.file-drag').html(tag);
		console.log('최종fileList', fileList)
	}
}


var singleFile = ''; //파일 선택시 선택한 첨부파일 정보를 담아둘 변수

//파일첨부 정보 file태그에 담기
	function singleFileUpload(){
		if(singleFile != ""){
			var transfer = new DataTransfer();
			transfer.items.add( singleFile );
			//화면 태그 속성: attr -> 기본에 해당, 나중에 속성추가지정: prop
			$('input[type=file]').prop('files', transfer.files);
			console.log($('input[type=file]').val())
		}
	}


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
		var _preview = $('#file-attach .file-name'); //파일명 태그
		if(_preview.length>0) _name.empty();//파일명 태그 없애기
		
	}).on('click', '.file-preview img', function(){
		//미리보기 이미지 클릭시 크게 보이게
		if($('#modal-image').length == 1){
			$('.modal-body').html( $(this).clone() );
			new bootstrap.Modal($('#modal-image')).show()
		}
	})
	.on('click', '.file-item .btn-close', function(){
		//첨부된 파일 삭제 클릭시
		fileList.removeFile($(this).data('seq'));
		fileList.showFile();
	})
	
//파일이 이미지파일인지 확인
function isImage(filename){
	// abc.png, abc.jpg ...
	var ext = filename.substr(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ["png", "jpg", "bmp", "gif", "jpeg", "webp"];
	return imgs.indexOf(ext)==-1 ? false : true;
	
}

//첨부 파일 크기 제한 함수
function rejectedFile(fileInfo, tag){
	// 1024byte = 1kb, 1Mb = 1024 * 1024 byte
	if(fileInfo.size > 1024*1024*10){
		alert("10Mb를 넘는 파일은 첨부할 수 없습니다!");
		tag.val('');
		return true;
	}else{
		return false;
	}
}

$(function() {
	
	//다중 파일선택 처리
	$('input#file-multiple').on('change', function(){
		var files = this.files;
		files = filteredFile(files);
		for(var i=0; i<files.length; i++){
			fileList.setFile(files[i]);
		}
		fileList.showFile()
		console.log(fileList)
	})
	
	//프로필 이미지 선택처리
	$('input#file-single').on('change', function(){
//		console.log($(this))
//		console.log(this.files)
		
		var _priview = $('#file-attach .file-preview'); //이미지 미리보기
		var _delete = $('#file-attach .file-delete'); //파일 삭제요소
		var _name = $('#file-attach .file-name');//파일명 요소
		
		var attached = this.files[0];
		console.log(attached)
		
		if(attached){
			// 파일사이즈 제한
			if(rejectedFile(attached, $(this))) return;
			// 파일명 보여지게
			if(_name.length>0){
				_name.text( attached.name);
			}
			
			//이미지파일인지 확인
			if(isImage(attached.name)){
			    singleFile = attached; // 선택한 파일정보를 관리
				_delete.removeClass('d-none'); //삭제버튼 보이게
				// 미리보기 태그가 있을 때만(존재하지 않을 때는 길이 0)
				if(_priview.length > 0){
					_priview.html("<img>");
					
				}else{
					//첨부파일이 이미지인데, 미리보기 요소가 없으면 동적으로 만들어 보이게 처리
					//삭제버튼 앞에 넣기
					_delete.before("<span class='file-preview'><img></span>");
					_priview = $('#file-attach .file-preview');
				}	
				var reader = new FileReader();
				reader.readAsDataURL(attached);
				reader.onload = function(e){
					_priview.children("img").attr("src", e.target.result);
//					_priview.children("img").attr("src", this.result);
				}
			}else{
				//이전 선택되었던 이미지파일 처리
				_priview.empty();
				
				if($(this).hasClass("image-only")){
				singleFile = ''; //이미지가 아닌 파일인 경우는 관리정보를 초기화					
				$(this).val('');//실제 file태그의 정보 초기화
				_delete.addClass('d-none');
				}
				
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
		
	$('body').on('dragover dragleave drop', function(e){
		e.preventDefault();
				})	
	
	//드래그 드랍으로 파일첨부 처리
	$('.file-drag').on({
		'dragover dragleave drop': function(e){
			e.preventDefault();
			//드래그오버시 입력태그에 커서가 있을 때처럼 보여지게
			if(e.type=='dragover') $(this).addClass('drag-over')
			else 				   $(this).removeClass('drag-over')
		},
		'drop': function(e){
			console.log(e.originalEvent.dataTransfer.files)
			var files = e.originalEvent.dataTransfer.files;
			files = filteredFile(files);
			
			for(var i=0; i<files.length; i++){
				//폴더는 담지 않는다
				if(files[i].type ==""){
					alert("폴더는 첨부할 수 없습니다")
				}else{
				fileList.setFile(files[i]);				
				}
			}
			fileList.showFile();
		}
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

//서브밋 전 다중파일첨부 정보 file태그에 담기
function multipleFileUpload(){
	var transfer = new DataTransfer();
	var files = fileList.getFile();
	if( files.length>0){
		for(var i=0; i<files.length; i++){
			//업로드해야 하는 파일들만(true) 추가
			if(fileList.info.upload[i])transfer.items.add(files[i])
		}
	}
	$('input#file-multiple').prop('files', transfer.files)
}


//입력항목 입력여부 확인
function emptyCheck(){
	var ok = true;
	$('.check-empty').each(function(){
		if($(this).val()==""){
			alert($(this).attr('title') + "을 입력하세요");
			$(this).focus();
			ok =  false;
			return ok; 
		}
	})
	return ok;
}
