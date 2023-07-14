<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<select class="form-select w-px300" id="shelter">
	<option value=''>보호소 선택</option>
	<c:forEach items="${list.items.item }" var="vo">
	<option value='${vo.careRegNo }'>${vo.careNm }</option>
	</c:forEach>
</select>

<script>
	$("#shelter").change(function(){
		animal_list(1);
	})
</script>