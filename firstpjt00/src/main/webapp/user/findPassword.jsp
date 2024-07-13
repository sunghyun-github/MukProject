<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>비밀번호 찾기</title>
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
        <form method="post" action="findPasswordProc.do" onsubmit="return validateForm()">
            <h2>비밀번호 찾기</h2>
            <hr>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="아이디" name="user_id" id="user_id" maxlength="20">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="이메일" name="user_email" id="user_email" maxlength="50">
            </div>
            <input type="submit" class="btn btn-primary form-control" value="비밀번호 찾기">
        </form>
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
    var userEmail = document.getElementById("user_email").value;
    
    if (userId.trim() === "") {
        alert("아이디를 입력하세요.");
        return false; // 폼 제출을 막습니다.
    }
    
    if (userEmail.trim() === "") {
        alert("이메일을 입력하세요.");
        return false; // 폼 제출을 막습니다.
    }
    
    return true; // 폼이 제출됩니다.
}
</script>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>