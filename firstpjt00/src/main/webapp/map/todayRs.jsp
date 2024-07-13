<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="mpjt.dto.RsDTO"%>
<%@page import="mpjt.dao.RsDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
RsDAO dao = new RsDAO();
List<RsDTO> rsList = dao.rsList();
RsDTO dto = new RsDTO();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>오늘의 메뉴</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
</head>
<body>
<%@ include file="../common/menu.jsp"%>
<main id="container">
    <section class="today">
        <div class="inner">
            <h2 >오늘의 점심 추천</h2>
            <hr>
            <div class="rs_area">
                <div class="todays_rs">
                    <%
                    if (rsList != null && !rsList.isEmpty()) {
                        Random rand = new Random();
                        int randomIndex = rand.nextInt(rsList.size());
                        RsDTO randomRestaurant = rsList.get(randomIndex);
                    %>
                    
                    <div class="rs_wrap">
                        <div class="rs_img">
                            <img src="<%= randomRestaurant.getRs_img() %>" alt="<%= randomRestaurant.getRs_name() %>">
                        </div>
                        <div class="rs_info">
                            <h4>
                                <%= randomRestaurant.getRs_name() %>
                                <span>#<%= randomRestaurant.getRs_type() %></span>
                                <div class="rating" data-rating="<%= randomRestaurant.getRs_rating() %>">
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                    <span class="star"><i class="xi-star-o"></i></span>
                                </div>
                            </h4>
                            <p class="clock"><%= randomRestaurant.getRs_hour() %></p>
                            <p class="menu"><%= randomRestaurant.getRs_menu() %>&nbsp;&nbsp;&#8361;<%= randomRestaurant.getRs_price() %></p>
                            <p class="call"><%= randomRestaurant.getRs_num() %></p>
                            <p class="pin"><%= randomRestaurant.getRs_addr() %></p>
                            <p class="comment"><%= randomRestaurant.getRs_comment() %></p>
                        </div>
                    </div>
                    <%
                    } else {
                    %>
                        <p>추천할 맛집 정보가 없습니다.</p>
                    <%
                    }
                    %>
                </div>
                <button onclick="location.reload();" class="btn btn-primary">다시 추천받기</button>
            </div>
        </div>
    </section>
</main>
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
