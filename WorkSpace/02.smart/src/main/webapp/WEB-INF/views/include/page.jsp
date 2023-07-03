<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
  <ul class="pagination mt-3 justify-content-center">
	
	<!-- 첫 블럭으로 -->
	
	<!-- 	le <=, lt <, ge >=, gt > -->
	<c:if test="${page.curBlock gt 1}">
    <li class="page-item">
    	<a class="page-link">
    		<i class="fa-solid fa-angles-left" onclick="page(1)"></i>
    	</a>
    </li>
    <!-- 이전 블럭으로 -->
    <li class="page-item">
    	<a class="page-link">
    		<i class="fa-solid fa-angle-left" onclick="page(${page.beginPage-1})"></i>
    	</a>
    </li>
	
	</c:if>
    
    <c:forEach var="no" begin="${page.beginPage }" end="${page.endPage }">
    <c:if test="${no eq page.curPage}">
    <li class="page-item"><a class="page-link active" href="#">${no }</a></li>
    </c:if>    
    <c:if test="${no ne page.curPage}">
    <li class="page-item"><a class="page-link" onclick="page(${no})">${no }</a></li>
    </c:if>    
    </c:forEach>
	<!-- 다음 블럭으로 -->
	<c:if test="${page.curBlock lt page.totalBlock }">
    <li class="page-item">
    	<a class="page-link">
    		<i class="fa-solid fa-angle-right" onclick="page(${page.endPage+1})"></i>
    	</a>
    </li>
    
	<!-- 마지막 블럭으로 -->
    <li class="page-item">
    	<a class="page-link">
    		<i class="fa-solid fa-angles-right" onclick="page(${page.totalPage+1})"></i>
    	</a>
    </li>
	</c:if>    
  </ul>
</nav>

<script>
function page(no){
	$('[name=curPage]').val(no);
	$('form').submit()
}
</script>