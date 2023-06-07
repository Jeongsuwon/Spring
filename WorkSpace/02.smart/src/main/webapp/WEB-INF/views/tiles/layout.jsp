<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="ko">
<c:choose>
	<c:when test="${category eq 'cu' }"><c:set var="title" value="고객관리"/> </c:when>
	<c:when test="${category eq 'hr' }"><c:set var="title" value="사원관리"/> </c:when>
	<c:when test="${category eq 'no' }"><c:set var="title" value="공지사항"/> </c:when>
	<c:when test="${category eq 'bo' }"><c:set var="title" value="방명록"/> </c:when>
	<c:when test="${category eq 'da' }"><c:set var="title" value="공공데이터"/> </c:when>
	<c:when test="${category eq 'vi' }"><c:set var="title" value="시각화"/> </c:when>
</c:choose>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>스마트웹&amp;앱 ${title}</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="img/hanul.ico" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css?<%=new java.util.Date() %>" rel="stylesheet" />
        <link href="css/common.css?<%=new java.util.Date() %>" rel="stylesheet" />
        
        <!-- cdnjs.com > fontawesome 검색 > style, javascript 선언문 복사해서 넣기 -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>
        <!-- //fontawesome -->
    </head>
    <body>
        <div class="d-flex" id="wrapper">
            <!-- Sidebar-->
            <div class="border-end bg-white" id="sidebar-wrapper">
                <div class="sidebar-heading border-bottom bg-light">
                <a href="<c:url value='/'/>">
                <img style="width:20%" src="img/hanul.logo.png" class="me-2">
                <span>스마트 웹&amp;앱</span>
                </a>
                </div>
                <div class="list-group list-group-flush">
                    <a class="${category eq 'cu' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="list.cu">고객관리</a>
                    <a class="${category eq 'hr' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="#!">사원관리</a>
                    <a class="${category eq 'no' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="#!">공지사항</a>
                    <a class="${category eq 'bo' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="#!">방명록</a>
                    <a class="${category eq 'da' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="#!">공공데이터</a>
                    <a class="${category eq 'vi' ? 'active':'' } list-group-item list-group-item-action list-group-item-light p-3" href="#!">시각화</a>
                </div>
            </div>
            <!-- Page content wrapper-->
            <div id="page-content-wrapper">
                <!-- Top navigation-->
                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                    <div class="container-fluid">
                        <button class="btn btn-primary" id="sidebarToggle">Toggle Menu</button>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
                                
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
                                    <div class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                        <a class="dropdown-item" href="#!">Action</a>
                                        <a class="dropdown-item" href="#!">Another action</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#!">Something else here</a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <!-- Page content-->
                <div class="container-fluid">
                    <tiles:insertAttribute name="container"/>
                </div>
                <footer class="border-top py-4 mt-4 text-center">
                	<div>Copyright &copy; My Website 2023</div>
                </footer>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
