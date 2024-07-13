
<%@page import="mpjt.dto.UserDTO"%>
<%@page import="mpjt.dao.RsDAO"%>
<%@page import="mpjt.dto.RsDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% // param
request.setCharacterEncoding("utf-8");
String sNum = request.getParameter("num"); 
int num = Integer.parseInt(sNum);
RsDTO dto = new RsDTO();
UserDTO user = new UserDTO();

dto.setRs_idx(num);
%>    
<% // db - select
RsDAO dao = new RsDAO();

//1. visitcount update
//dao.updateVisitcount(dto); 

//2. view content
dto = dao.selectView(dto);

%>    
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글 상세보기</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script>
   function del(num){
      const input = confirm("정말 삭제 할까요?");
      if(input){
         location.href = "<%=request.getContextPath()%>/board/DeleteProc?num="+num;
      }else{
         alert('삭제를 취소 했습니다.');
         return;
      }
   }
<%
// 현재 로그인한 사용자의 ID를 가져옵니다.
String currentUser = (String)session.getAttribute("user_id");
%>

// 다른 작성자인 경우 수정 및 삭제 링크 비활성화
if ("<%=currentUser%>" !== "<%=user.getUser_id()%>") {
    // 수정 링크 비활성화
    var updateLinks = document.querySelectorAll('a[href*="update.jsp"]');
    updateLinks.forEach(function(link) {
        link.style.display = 'none';
    });

    // 삭제 링크 비활성화
    var deleteLinks = document.querySelectorAll('a[href*="javascript:del"]');
    deleteLinks.forEach(function(link) {
        link.style.display = 'none';
    });
}
</script>

</head>
<body>
<%@ include file="../common/menu.jsp"%>
<main id="container">
<div class="inner rs_inner">
	<div class="submit_wrap">
		<a class="list_btn" href="rsList.jsp"><img class="list" src="../resources/images/list.png" alt="">맛집 목록</a>
	</div>
	<h2 class="blind">맛집 상세보기</h2>
	<div class="board_view_wrap">
	<table>
		<tr>
			<td class="col1" colspan="3"><%=dto.getRs_name()%></td>
			<td class="col2">조회수: <%=dto.getRs_views()%></td>
		</tr>
		<tr>
			<td class="col3" colspan="3"></td>
			<td class="col4"></td>
		</tr>
		<tr>
			<td class="col5" colspan="4">
			<div class="content">
				<div id="map" style="width:100%;height:350px;"></div>
				<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9c81bee860ad23a53ad9cd495fc9a65a&libraries=services"></script>
				<script>
					var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
					    mapOption = {
					        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
					        level: 1 // 지도의 확대 레벨
					    };  
					
					// 지도를 생성합니다    
					var map = new kakao.maps.Map(mapContainer, mapOption); 
					
					// 주소-좌표 변환 객체를 생성합니다
					var geocoder = new kakao.maps.services.Geocoder();
					
					// 주소로 좌표를 검색합니다
					geocoder.addressSearch('<%=dto.getRs_addr()%>', function(result, status) {
					
					    // 정상적으로 검색이 완료됐으면 
					     if (status === kakao.maps.services.Status.OK) {
					
					        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					
					        // 결과값으로 받은 위치를 마커로 표시합니다
					        var marker = new kakao.maps.Marker({
					            map: map,
					            position: coords
					        });
					
					        // 인포윈도우로 장소에 대한 설명을 표시합니다
					        var infowindow = new kakao.maps.InfoWindow({
					            content: '<div style="width:150px;text-align:center;padding:8px 0;color:#000;"><%=dto.getRs_name()%></div>'
					        });
					        infowindow.open(map, marker);
					
					        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					        map.setCenter(coords);
					    } 
					});    
					
				</script>
			
			</div>
				<%
					if (session.getAttribute("user_id") != null && session.getAttribute("user_id").equals(user.getUser_id())) {
				%>
					<div class="submit_wrap">
					<a class="submit_btn" href="update.jsp?num=<%=num%>">수정</a>
					<a class="submit_btn" href="javascript:del('<%=num%>');">삭제</a> 
					</div>
				<%} %>
			
			</td>
		</tr>
			<td>주소: <%=dto.getRs_addr() %></td>
		<tr>
			
		</tr>
	</table>
</div>	
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>