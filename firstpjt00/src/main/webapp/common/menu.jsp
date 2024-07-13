<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String user_id = null;
	if (session.getAttribute("user_id") != null) {
		user_id = (String) session.getAttribute("user_id");
	}
	
	String userRole = new mpjt.dao.UserDAO().getUserRole(user_id);
	
%>
	<div id="wrap">
		<header id="header">
			<div class="inner">
				<h1 class="logo">
					<a href="<%=request.getContextPath()%>/user/main.jsp"> <span class="blind">뭐먹을끼니?</span>
					</a>
				</h1>
				
				<nav class="gnb_wrap">
					<ul class="gnb">
						<li><a href="../map/todayRs.jsp">오늘의 메뉴</a></li>
						<li><a href="../map/rsList.jsp">맛집찾기</a></li>
						<li><a href="../mbboard/bbs.bo">자유게시판</a></li>
						
					</ul>
					<div class="login_area">
						<ul class="login_wrap">
							<%
							if (user_id == null) {
							%>
							
							<li><a href="../user/login.do">로그인</a></li>
							<li><a href="../user/join.do">회원가입</a>
							</li>
							<%
							} else {
							%>
								<li class="user_id"><b><%=user_id %></b>님, 환영합니다.</li>
								<% 
						        if ("관리자".equals(userRole)) { // 사용자 역할이 관리자인 경우에만 회원 리스트 버튼 표시
						        %>
									<li><a href="<%=request.getContextPath()%>/user/list.jsp">회원관리</a></li>
								<%
			                     }
			                    %>
							 <li><a href="../user/mypage.jsp">마이페이지</a></li>
                  			 <li><a href="../user/logout.do">로그아웃</a></li>
                  <%
                     }
                     %>
               </ul>
            </div>
         </nav>

			</div>
		</header>
	</div>