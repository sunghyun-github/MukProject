<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<title>맛집추가</title>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<main id="container">
	<div class="add_rs">
		<h2>맛집추가</h2>
		<hr>
		<div class="jumbotron" style="padding-top: 20px;">
			<form method="post" action="addProc.jsp" class="rs_add">
				<div class="form-group">
					<label for="rs_name" class="form-label">가게명:</label> 
					<input type="text" class="form-control" placeholder="가게명" name="rs_name" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_addr" class="form-label">주소:</label> 
					<input type="text" class="form-control" placeholder="도로명주소" name="rs_addr" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_num" class="form-label">전화번호:</label> 
					<input type="text" class="form-control" placeholder="ex) 000-000-0000" name="rs_num" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_hour" class="form-label">영업시간:</label> 
					<input type="text" class="form-control" placeholder="ex) 00:00-24:00" name="rs_hour" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_menu" class="form-label">대표메뉴:</label> 
					<input type="text" class="form-control" placeholder="메뉴명" name="rs_menu" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_price" class="form-label">가격:</label> 
					<input type="text" class="form-control" placeholder="ex) 00,000" name="rs_price" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_price" class="form-label">종류:</label> 
					<input type="text" class="form-control" placeholder="한식, 일식, 중식, 세계음식" name="rs_type" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_comment" class="form-label">한줄평:</label> 
					<input type="text" class="form-control"  name="rs_comment" maxlength="45">
				</div>
				<div class="form-group">
					<label for="rs_img" class="form-label">이미지 주소:</label> 
					<input type="text" class="form-control" placeholder="ex) http://" name="rs_img" maxlength="255">
				</div>
				
				<input type="submit" class="btn btn-primary form-control" value="맛집추가">
			</form>
		</div>
	</div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>