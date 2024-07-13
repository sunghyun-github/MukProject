<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<main id="container">
<div class="col-lg-4">
    <div class="jumbotron" style="padding-top: 20px;">
        <form method="post" action="loginProc.do" onsubmit="return validateForm()">
            <h2 class="log">로그인</h2>
            <hr>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="아이디" name="user_id" id="user_id" maxlength="20"
                       value="<%= request.getAttribute("userId") != null ? request.getAttribute("userId") : "" %>">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="비밀번호" name="user_password" id="user_password" maxlength="20">
            </div>
            <input type="submit" class="btn btn-primary form-control" value="로그인">
        </form>
        <div class="find_wrap">
            <a href="findId.jsp">아이디 찾기</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="findPassword.jsp">비밀번호 찾기</a>
        </div>
    </div>
</div>

<%
    // errorMsg가 존재하면 alert로 띄움
    String errorMsg = (String) request.getAttribute("errorMsg");
    if (errorMsg != null) {
%>
    <script>
        alert("<%= errorMsg %>");
    </script>
<%
    }
%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<script>
function validateForm() {
    var userId = document.getElementById("user_id").value;
    var userPassword = document.getElementById("user_password").value;
    
    if (userId.trim() === "") {
        alert("아이디를 입력하세요.");
        return false; // 폼 제출을 막습니다.
    }
    
    if (userPassword.trim() === "") {
        alert("비밀번호를 입력하세요.");
        return false; // 폼 제출을 막습니다.
    }
    
    return true; // 폼이 제출됩니다.
}
</script>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
