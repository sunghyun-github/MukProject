<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<script>
    function confirmDelete() {
        var result = confirm("정말 탈퇴하시겠습니까?");
        if (result) {
            // 확인을 눌렀을 때
            return true; // 폼 제출
        } else {
            // 취소를 눌렀을 때
            return false; // 폼 제출 취소
        }
    }
</script>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<main id="container">
<div class="user_del">
    <h2>회원 탈퇴</h2>
    <hr>
    <p>삭제할 아이디의 비밀번호를 입력하세요.</p><br>
    <form action="deleteProc.do" method="post" onsubmit="return confirmDelete()">
        <div class="mb-3 delete_wrap">
            <label for="userPassword" class="form-label">비밀번호:</label>
            <input type="password" class="form-control" id="userPassword" name="user_password" required>
        </div>
        <div class="submit_wrap"><button type="submit" class="btn btn-primary submit_btn">확인</button></div>  
    </form>
</div>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
