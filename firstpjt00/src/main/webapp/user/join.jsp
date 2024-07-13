<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />

<script>
function idCheck(){
    const id = document.querySelector('#userIdInput').value.trim(); // trim()을 사용하여 공백을 제거
    if (id === '') {
        // 공백일 때 메시지를 추가
        alert('아이디를 입력해주세요.');
        return;
    }
    
    const param = {user_id: id}; 
    
    $.ajax({
        contentType: "application/json",
        type:'GET',
        url:'<%=request.getContextPath()%>/user/idcheckProc.do',
        dataType:'json',
        data:param,
        success: function (data) {
            console.log(data);
            if(data['rs'] === '0'){
                $('#isIdTrue').show(); // 사용 가능한 ID 입니다.
                $('#isIdFalse').hide(); // ID가 존재합니다.
            } else {
                $('#isIdFalse').show(); // ID가 존재합니다.
                $('#isIdTrue').hide(); // 사용 가능한 ID 입니다.
            }
        },
        error: function (request, status, error) {
            console.log(request, status, error);
        }
    });
}

function emailCheck() {
    const email = document.querySelector('#user_email').value.trim();
    if (email === '') {
        alert('이메일을 입력해주세요.');
        return;
    }

    const encodedEmail = encodeURIComponent(email); // URL 인코딩

    $.ajax({
        contentType: "application/json",
        type: 'GET',
        url: '<%=request.getContextPath()%>/user/emailcheckProc.do',
        dataType: 'json',
        data: { user_email: encodedEmail }, // 인코딩된 이메일 주소 전달
        success: function (data) {
            console.log(data);
            if (data.rs === 1) { // 중복된 이메일이면
                $('#isEmailFalse').show(); // 이미 사용 중인 이메일 메시지를 표시합니다.
                $('#isEmailTrue').hide();
            } else {
                $('#isEmailTrue').show(); // 이메일 사용 가능 메시지를 표시합니다.
                $('#isEmailFalse').hide();
            }
        },
        error: function (request, status, error) {
            console.log(request, status, error);
        }
    });
}

function passwordCheck() {
    const password = document.querySelector('input[name="user_password"]').value;
    const confirmPassword = document.querySelector('input[name="user_password_confirm"]').value;

    if (password === confirmPassword) {
        $('#isPasswordTrue').show();
        $('#isPasswordFalse').hide();
    } else {
        $('#isPasswordFalse').show();
        $('#isPasswordTrue').hide();
    }
}

function submitForm() {
    const emailValid = $('#isEmailTrue').is(':visible'); // 이메일 사용 가능 메시지가 표시되었는지 확인
    const passwordValid = $('#isPasswordTrue').is(':visible'); // 비밀번호 일치 메시지가 표시되었는지 확인

    if (!emailValid) {
        alert('이메일 중복을 확인해주세요.');
        return false; // 회원가입을 중단하고 페이지를 유지
    }

    if (!passwordValid) {
        alert('비밀번호가 일치하지 않습니다.');
        return false; // 회원가입을 중단하고 페이지를 유지
    }

    return true; // 회원가입 진행
}

// 최초에 hide 실행
$(function(){
    $('#isIdTrue, #isEmailTrue, #isPasswordTrue').hide(); 
    $('#isIdFalse, #isEmailFalse, #isPasswordFalse').hide(); 

    // 비밀번호 확인 필드에 입력이 일어날 때마다 체크
    $('input[name="user_password"], input[name="user_password_confirm"]').on('keyup', passwordCheck);
});

</script>

</head>
<body>
<%@ include file="../common/menu.jsp"%>
	<main id="container">
		<div class="col-lg-4 join_inner">
			<div class="jumbotron">
				<%-- 메시지 출력 부분 추가 --%>
				<%
                String message = (String) request.getAttribute("message");
                if (message != null && !message.isEmpty()) {
            %>
				<div class="alert alert-warning" role="alert">
					<%= message %>
				</div>
				<% } %>
				<form method="post" action="joinProc.do"
					onsubmit="return submitForm();">
					<div class="inner">
						<div class="jumbotron" style="padding-top: 20px;">
							<%-- 메시지 출력 부분 추가 --%>
							<%
                String messageString = (String) request.getAttribute("message");
                if (message != null && !message.isEmpty()) {
            %>
							<div class="alert alert-warning" role="alert">
								<%= message %>
							</div>
							<% } %>
							<h2 class="log">회원가입</h2>
							<hr>
							<div class="form-group blind">
								<label class="btn btn-primary active">
									<input type="radio" name="user_role" autocomplete="off" value="일반 사용자" checked>
								</label>
							</div>
							<div class="form-group_id">
								<input type="text" class="form-control" placeholder="아이디" name="user_id" maxlength="20" id="userIdInput"> <input type="button" class="btn btn-primary" value="중복확인" onclick="idCheck()">
							</div>
							<span id="isIdTrue" style="color: blue">사용 가능한 ID 입니다.</span>
								<span id="isIdFalse" style="color: red">ID가 이미 사용 중입니다.</span><br>
							<div class="form-group_email">
								<input type="text" class="form-control" placeholder="이메일" name="user_email" id="user_email" maxlength="50">
								<input type="button" class="btn btn-primary" value="중복확인" onclick="emailCheck()">
							</div>
							<span id="isEmailTrue" style="color: blue">사용 가능한 이메일입니다.</span>
							<span id="isEmailFalse" style="color: red">이미 사용 중인
								이메일입니다.</span>
							<div class="form-group_pass">
								<input type="password" class="form-control" placeholder="비밀번호" name="user_password" maxlength="20">
							</div>
							<div class="form-group_pass_confirm">
								<input type="password" class="form-control" placeholder="비밀번호 확인" name="user_password_confirm" maxlength="20">
							</div>
                            <span id="isPasswordTrue" style="color: blue">비밀번호가 같습니다.</span>
                            <span id="isPasswordFalse" style="color: red">비밀번호가 서로 다릅니다.</span><br>
							<div class="form-group_name">
								<input type="text" class="form-control" placeholder="이름" name="user_name" maxlength="20">
							</div>
							<div class="form-group" style="text-align: center;">
								<label class="btn btn-primary active">
									<input class="form-check-input" type="radio" name="user_gen" autocomplete="off" value="남자" checked>남자
								</label>
								<label class="btn btn-primary active">
									<input class="form-check-input" type="radio" name="user_gen" autocomplete="off" value="여자">여자
								</label>
							</div>
							<input type="submit" class="btn btn-primary form-control" value="회원가입">
						</div>
					</div>
				</form>

			</div>
		</div>
		<div class="col-lg-4"></div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

	<%-- 회원가입 완료 후 로그인 페이지로 이동하는 스크립트 --%>
	<%
    String successRedirect = (String) request.getAttribute("successRedirect");
    if (successRedirect != null && !successRedirect.isEmpty()) {
%>
	<script>
        alert('회원가입이 완료되었습니다.');
        window.location.href = '<%= successRedirect %>';
    </script>
	<% } %>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
