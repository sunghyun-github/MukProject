<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>비밀번호 찾기 결과</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
<style>

</style>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<main id="container">
<div class="col-lg-4" style="padding-top: 40px;">
    <h2 class="log">비밀번호 찾기 결과</h2>
    <hr>
    <div class="jumbotron">
	    <div class="find_id">
	        <p class="result_find_message">찾으시는 비밀번호는 [ <span><%= request.getAttribute("password") %></span> ]입니다. </p>
	    </div>
	    <div class="btn_retrun">
	        <a href="login.jsp" class="btn btn-primary">로그인 하기</a>
	    </div>
	</div>
</div>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>