<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">사원정보분석 시각화</h3>
	
	<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link">부서원 수</a>
  </li>
  <li class="nav-item">
    <a class="nav-link">채용인원 수</a>
  </li>
  <li class="nav-item">
    <a class="nav-link">테스트1</a>
  </li>
  <li class="nav-item">
    <a class="nav-link">테스트2</a>
  </li>
</ul>

<div id='tab-content' class="m-md-2 m-lg-3" style='height:520px'>
	<canvas id="chart" class="h-100 m-auto"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
$('ul.nav-tabs li').on({
	'click': function(){
		$('ul.nav-tabs li a').removeClass('active');
		$(this).children('a').addClass('active');
		
		var idx = $(this).index();
		if(idx==0) 			department(); //부서원 수 조회
		else if(idx==1) 	hirement(); //채용인원 수 조회
	},
	
	'mouseover': function(){
		$(this).addClass('shadow');
	},
	
	'mouseleave': function(){
		$(this).removeClass('shadow');
	},
})

//부서원수 조회
function department(){
	sampleChart();
}

function sampleChart(){
	 new Chart($('#chart'), {
		    type: 'bar',
		    data: {
		      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
		      datasets: [{
		        label: '# of Votes',
		        data: [12, 19, 3, 5, 2, 3],
		        borderWidth: 1
		      }]
		    },
		    options: {
		      scales: {
		        y: {
		          beginAtZero: true
		        }
		      }
		    }
		  });
	
}
//채용인원 수 조회
function hirement(){
	
}

$(function(){
	$('ul.nav-tabs li').eq(0).trigger('click');
})
</script>
</body>
</html>