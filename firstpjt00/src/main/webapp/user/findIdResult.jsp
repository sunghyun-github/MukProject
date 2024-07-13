<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>아이디 찾기 결과</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />

</head>
<body>
<%@ include file="../common/menu.jsp" %>
<main id="container">
<div class="col-lg-4" style="padding-top: 40px;">
    <h2 class="log">아이디 찾기 결과 </h2>
    <hr>
    <div class="find_id">
    <% String foundUserId = (String) request.getAttribute("userId"); %>
    <% if(foundUserId != null) { %>
        <p class="result_find_message">찾으시는 아이디는 [ <span><%= foundUserId %></span> ]입니다.</p>
    <% } else { %>
        <p class="result_find_message">입력한 정보로는 아이디를 찾을 수 없습니다.</p>
    <% } %>
    </div>
    <div class="btn_retrun">
        <a href="login.jsp" class="btn btn-primary">로그인 하기</a>
        <a href="findPassword.jsp" class="btn btn-secondary">비밀번호 찾기</a>
    </div>
</div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>