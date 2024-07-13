<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>맛집 삭제 결과</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <%@ include file="../common/menu.jsp"%>
    <div class="container">
        <h1>맛집 삭제 결과</h1>
        <hr>
        <div class="alert alert-dismissible <%=(request.getAttribute("result").equals("success")) ? "alert-success" : "alert-danger"%>">
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            <% if (request.getAttribute("result").equals("success")) { %>
                <strong>성공!</strong> 맛집이 성공적으로 삭제되었습니다.
            <% } else { %>
                <strong>오류!</strong> 맛집 삭제 중 오류가 발생했습니다.
            <% } %>
        </div>
        <a href="rsList.jsp" class="btn btn-primary">맛집 목록으로 돌아가기</a>
    </div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>