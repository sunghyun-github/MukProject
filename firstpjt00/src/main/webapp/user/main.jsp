<%@page import="mpjt.dao.RsDAO"%>
<%@page import="mpjt.dto.RsDTO"%>
<%@page import="java.util.Map"%>
<%@page import="mpjt.dto.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="mpjt.dao.BoardDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%
BoardDAO boardDAO = new BoardDAO();
List<BoardDTO> recentPosts = boardDAO.getRecentPosts();

RsDAO rsDAO = new RsDAO();
List<RsDTO> getTopRs = rsDAO.getTopRs();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>뭐먹을끼니?</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="../resources/js/ui-common.js"></script>
<script>

window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const message = urlParams.get('message');
    if (message) {
        alert(message);
    }
};

//클릭한 tr의 ID를 가져와 view.jsp로 이동하는 함수
function redirectToView(id) {
 window.location.href = '../mbboard/view.bo?num=' + id;
}

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
</head>
<body>
   <%@ include file="../common/menu.jsp"%>
   <main id="container">
   <section class="main_banner">
      <!-- Slider main container -->
      <!-- Slider main container -->
      <div class="swiper">
         <div class="btn_wrap">
            <div class="swiper-pagination"></div>
            <button class="toggle_btn" type="button">
               <span class="blind">일시정지</span>
            </button>
         </div>
         <div class="swiper-wrapper">
            <div class="swiper-slide">
               <img src="../resources/images/banner1.png" alt="Slide 1">
            </div>
            <div class="swiper-slide">
               <img src="../resources/images/banner2.png" alt="Slide 2">
            </div>
            <div class="swiper-slide">
               <img src="../resources/images/banner3.png" alt="Slide 3">
            </div>
         </div>

         <!-- If we need navigation buttons -->
         <div class="swiper-button-prev arrowbtn"></div>
         <div class="swiper-button-next arrowbtn"></div>

         <!-- Pagination -->

      </div>
	  </section>
	<section class="main_service">
	      <div class="inner">
	<h2 class="blind">뭐먹고싶니?</h2>
	      </div>
	      <div class="main_service_list">
	         <div class="main_service_group">
	            <div class="service_row1">
	               <div class="marqueebox">
	                  <div class="marquee_wrap">
	                     <!-- <marquee에 li 8개 * 3개(marquee)> -->
	                  <ul class="marquee">
	                     <li class="marquee_con marquee1"></li>
	                     <li class="marquee_con marquee2"></li>
	                     <li class="marquee_con marquee3"></li>
	                     <li class="marquee_con marquee4"></li>
	                     <li class="marquee_con marquee5"></li>
	                     <li class="marquee_con marquee6"></li>
	                     <li class="marquee_con marquee7"></li>
	                     <li class="marquee_con marquee8"></li>
	                  </ul>
	                  <ul class="marquee marquee_2">
	                     <li class="marquee_con marquee1"></li>
	                     <li class="marquee_con marquee2"></li>
	                     <li class="marquee_con marquee3"></li>
	                     <li class="marquee_con marquee4"></li>
	                     <li class="marquee_con marquee5"></li>
	                     <li class="marquee_con marquee6"></li>
	                     <li class="marquee_con marquee7"></li>
	                     <li class="marquee_con marquee8"></li>
	                  </ul>
	                  <ul class="marquee marquee_3">
	                     <li class="marquee_con marquee1"></li>
	                     <li class="marquee_con marquee2"></li>
	                     <li class="marquee_con marquee3"></li>
	                     <li class="marquee_con marquee4"></li>
	                     <li class="marquee_con marquee5"></li>
	                     <li class="marquee_con marquee6"></li>
	                     <li class="marquee_con marquee7"></li>
	                     <li class="marquee_con marquee8"></li>
	                  </ul>
	                 
	               </div>
	            </div>
	         </div>
	      </div>
		 </div>
	</section>
	 
	<section class="main_intro">
		  	
		  	<h2>오늘 <span>뭐먹을끼니?</span></h2>
		  	<p>점심 뭐먹지...<i class="xi-message-o"></i> 매일 반복되는 걱정 끝<i>!</i></p>
		  	<p>학원 주변 <span>맛집 정보</span>와 <span>오늘의 메뉴 추천</span>으로</p>
		  	<p>점심 메뉴 고민을 해결해드릴게요<i>!</i></p>
		  </section>
	     
<section class="top_views">
    	<div class="inner">
 			<div class="views_tit">
 				<h2>별점 높은 맛집&nbsp;&nbsp;<i class="xi-star"></i></h2>
 				<div class="pic_wrap">
		<% for(RsDTO dto : getTopRs) { %>
			<a href="../map/rsList_pic.jsp" class="pic">
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
											 <%}%>"># <%=dto.getRs_type()%></span>
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
 		</div>
    </section> 
   
	<section class="recent_bbs">
		<div class="inner">
		<div class="recent_tit">
		<h2>최근 게시물&nbsp;&nbsp;<i class="xi-spinner-1 xi-spin"></i></h2>
		<a href="../mbboard/bbs.bo">+ 더보기</a>
		</div>
		<div class="board_list_wrap">
	          <table>
	             <thead>
	                <tr>
	                   <th class="col2">작성자</th>
	                   <th class="col3">제목</th>
	                   <th class="col4"><i class="xi-eye"></i> 조회수</th>
	                   <th class="col5"><i class="xi-heart"></i> 좋아요</th>
	                   <th class="col6">작성일</th>
	                </tr>
	             </thead>
	             <tbody>
	              <% 
	              for (BoardDTO post : recentPosts) { %> 
	                  <tr onClick="redirectToView('<%= post.getFr_idx() %>')" >
	                      <td><%= post.getUser_id() %></td>
	                      <td><%= post.getFr_title() %></a></td>
	                      <td><%= post.getFr_visitnum() %></td>
	                      <td><%= post.getFr_like() %></td>
	                      <td><%= post.getFr_regd() %></td>
	                  </tr>
	              <% } %>
	          </tbody>
	             </table>
	          </div>
		 <ul>
	      
	  </ul>
	  </div>
	  </section>
   </main>
   <%@ include file="../common/footer.jsp"%>
</body>
</html>