<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="mpjt.dto.RsDTO"%>
<%@page import="mpjt.dao.RsDAO"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="mpjt.dao.UserDAO"%>
<%@page import="java.util.List"%>

<%
	request.setCharacterEncoding("utf-8");
	String category = request.getParameter("category");
	
	RsDAO dao = new RsDAO();
	List<RsDTO> rsList;
	
	if (category != null && !category.equals("select")) {
	    rsList = dao.rsListByCategory(category);
	} else {
	    rsList = dao.rsList();
	}
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>맛집찾기</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script src="<%=request.getContextPath()%>/resources/js/ui-common.js?v=<?php echo time(); ?>"></script>
<script>
	// 클릭한 tr의 ID를 가져와 view.jsp로 이동하는 함수
	function redirectToView(id) {
	    window.location.href = 'rsView.jsp?num=' + id;
	}
</script>
</head>
<body>
<%@ include file="../common/menu.jsp"%>
<main id="container">
<div class="inner rs_inner">
	<div class="rs_tit">
	<h2>맛집찾기</h2>
	<% 
      // UserDAO 클래스의 getUserRole 메서드를 호출하여 사용자의 역할을 확인
//       String userRole = new UserDAO().getUserRole((String)session.getAttribute("user_id"));
//       if ("관리자".equals(userRole)) { // 사용자 역할이 관리자인 경우에만 회원 리스트 버튼 표시
    %>
	<a href="addRs.jsp">+ 맛집추가</a>
	</div>
	<hr>
	<div class="category_wrap">
		<div class="rs_category">
			<a href="rsList.jsp" class="btn btn-primary">지도/목록으로 보기</a>
			<a href="rsList_pic.jsp" class="btn btn-primary active">사진으로 보기</a>
		</div>
		<div class="rs_filter">
			<form class="search_food" action="rsList_pic.jsp" method="get">
				<select name="category" class="select" onchange="this.form.submit()">
					<option value="select">음식종류 선택 &nbsp;🍚</option>
					<option value="한식">한식</option>
					<option value="일식">일식</option>
					<option value="중식">중식</option>
					<option value="아시아">아시아</option>
				</select>
			</form>
		</div>
    </div>
	
	<div class="pic_wrap">
		<% for(RsDTO dto : rsList) { %>
			<a href="rsView.jsp?num=<%=dto.getRs_idx()%>" class="pic">
				<img src="<%=dto.getRs_img()%>" alt="">
				<div class="pic_hover">
					<table>
						<tr>
							<td class="name"><%=dto.getRs_name()%></td>
							<td>
								<span class="<% if (dto.getRs_type().equals("중식")){%>
												ch
											 <%} else if(dto.getRs_type().equals("한식")) {%>
											 	kr
											 <%} else if(dto.getRs_type().equals("일식")) {%>
											 	jp
											 <%} else if(dto.getRs_type().equals("세계음식")) {%>
											 	world
											 <%}%>"># <%=dto.getRs_type()%>
								</span>
							</td>
							<td>
                                <div class="rating" data-rating="<%=dto.getRs_rating()%>">
                                    <!-- 별점을 표시할 span 요소들 -->
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    
                                </div>
                            </td>
						</tr>
						<tr>
							<td>대표메뉴</td>
							<td><%=dto.getRs_menu() %></td>
							<td><%=dto.getRs_price()%></td></tr>
						<tr>
							<td colspan="3">" <%=dto.getRs_comment() %> "</td>
						</tr>
					</table>
					
				</div>
			</a>
		<%} %>
		
	</div>
</div>
</main>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9c81bee860ad23a53ad9cd495fc9a65a"></script>

<script>
document.addEventListener("DOMContentLoaded", function() {
    // 별점을 표시할 모든 요소를 가져옴
    var ratingElements = document.querySelectorAll('.rating');

    // 각 요소에 대해 별점을 설정
    ratingElements.forEach(function(ratingElement) {
        var ratingValue = parseInt(ratingElement.getAttribute('data-rating'));
        var stars = ratingElement.querySelectorAll('.star');

        // 별점값에 따라 색을 설정
        for (var i = 0; i < ratingValue; i++) {
            stars[i].innerHTML = '<i class="xi-star"></i>';
        }
    });
});

</script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>