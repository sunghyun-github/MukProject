<%@page import="mpjt.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp" %>
<%@page import="mpjt.dto.UserDTO"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ include file="../common/menu.jsp"%>
<%
String userId = (String) session.getAttribute("user_id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
</head>
<body>
    <main id="container">
    	<section class="user_update">
        <div class="inner">
            <h2>회원 정보 수정</h2>
            <hr>
            <form action="updateProc.do" method="post">
                <div class="mb-3">
                    <label for="userID" class="form-label">ID:</label>
                    <input type="text" class="form-control" id="userID" value="<%=session.getAttribute("user_id") %>" disabled>
                </div>
                <% if ("관리자".equals(userRole)) { %>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="user_role" id="user" value="일반 사용자" required>
                    <label class="form-check-label" for="user">일반 사용자</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="user_role" id="admin" value="관리자" required>
                    <label class="form-check-label" for="admin">관리자</label>
                </div>
                <% } %>
                <div class="mb-3">
                    <label for="currentPassword" class="form-label">현재 비밀번호:</label>
                    <input type="password" class="form-control" id="currentPassword" name="current_password" required>
                </div>

                <div class="mb-3">
                    <label for="newPassword" class="form-label">새 비밀번호:</label>
                    <input type="password" class="form-control" id="newPassword" name="new_password" required>
                </div>
                <div class="mb-3">
                    <label for="userName" class="form-label">새 이름:</label>
                    <input type="text" class="form-control" id="userName" name="user_name" required>
                </div>
                <div class="mb-3">
                    <label for="userGender" class="form-label">성별:</label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="user_gen" id="male" value="남자" required>
                        <label class="form-check-label" for="male">남자</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="user_gen" id="female" value="여자" required>
                        <label class="form-check-label" for="female">여자</label>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">수정</button>
            </form>
           </div>
         </section>
        </div>
    </main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>