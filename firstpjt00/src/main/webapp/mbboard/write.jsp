<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="../common/sessionCheck.jsp"%>  
<%@ include file="../common/menu.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글쓰기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
function validateForm() {
	const form = document.writeForm;
	console.dir(form); // input
	if(form.title.value === ""){
		alert('title 필수값 입니다.');
		form.title.focus();
		return;
	}
	if(form.content.value === ""){
		alert('content 필수값 입니다.');
		form.content.focus();
		return;
	}
	form.submit();
}
</script>

</head>
<body>
<main id="container" >
	<section class="board_write">
	<div class="inner">
		<div class="board_write_wrap">
			<form name="writeForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/WriteProc.bo">
				<table class="board_table">
					<tr>
						<td class="write_label">제목</td>
						<td><input type="text" name="title" class="input_field"></td>
					</tr>
					<tr>
						<td class="write_label">내용</td>
						<td><textarea name="content" class="textarea_field"></textarea></td>
					</tr>
					<tr>
						<td class="write_label">업로드 파일</td>
						<td><input type="file" name="attachedFile" class="input_file"></td>
					</tr>
				</table>
				<input type="button" value="등록하기" class="writebtn" onclick="validateForm();">
			</form>
		</div>
		</div>
	</section>
</main>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>