/**
 * 회원정보 관련 처리
 */
 
var member = {
	// 태그별로 상태확인
	tagStatus: function(tag){
		if(tag.is("[name=userpw]")) return this.userpwStatus(tag.val());
		else if(tag.is("[name=userpw_ck]")) return this.userpwCheckStatus(tag.val());	
	},
	
	common : {
		empty: { is: false, desc:'입력하세요'},
		space: { is: false, desc:'공백없이 입력하세요'},
		min: { is: false, desc:'5자이상 입력하세요'},
		max: { is: false, desc:'10자이내로 입력하세요'}
	},
	
	space: /\s/g, //공백 확인
	userpw:
	{
		invalid: { is: false, desc: '영문 대/소문자, 숫자만 입력하세요'	},
		lack: { is:false, desc:'영문 대/소문자, 숫자를 모두 포함해야 합니다.'},
		valid: { is:true, desc: '사용가능합니다'},
		equal: { is:true, desc: '비밀번호와 일치합니다'},
		notEqual: { is:false, desc: '비밀번호와 일치하지않습니다'}
	},
	
	showStatus: function(target){
		var status = this.tagStatus(target)
		target.closest('.input-check').find('.desc').text(status.desc)
						.removeClass('text-success text-danger')
						.addClass(status.is ? 'text-success' : 'text-danger')
	},
	
	userpwStatus: function(pw){
		var reg = /[^A-Za-z0-9]/g, upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if(pw=="") return this.common.empty;
		else if(pw.match(this.space)) return this.common.space
		else if(reg.test(pw)) return this.userpw.invalid;
		else if(pw.length < 5) return this.common.min;
		else if(pw.length > 10) return this.common.max;						
		else if(!upper.test(pw) || !lower.test(pw) || ! digit.test(pw)) return this.userpw.lack;	
		else return this.userpw.valid;
	},
	userpwCheckStatus: function(pwCheck){
		if(pwCheck=="") return this.common.empty;
		else if(pwCheck ==$('[name=userpw]').val()) return this.userpw.equal;
		else return this.userpw.notEqual;
	},
}