<%@page import="mpjt.common.PageDTO"%>
<%@page import="mpjt.dto.CommentDTO"%>
<%@page import="mpjt.dao.CommentDAO"%>
<%@page import="mpjt.dto.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="mpjt.dao.BoardDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="../common/menu.jsp"%>
<%
List<BoardDTO> boardLists = (List<BoardDTO>) request.getAttribute("boardLists");
PageDTO p = (PageDTO) request.getAttribute("paging");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>자유게시판</title>
<link rel="icon" href="../resources/images/favicon.png" ype="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<?php echo time(); ?>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<?php echo time(); ?>" />
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script>

//클릭한 tr의 ID를 가져와 view.jsp로 이동하는 함수
function redirectToView(id) {
 window.location.href = 'view.bo?num=' + id;
}
</script>

<script>
function searchList(pageNum = 1) { // 초기 페이지
   var searchField = document.querySelector('#searchField').value;
   var searchWord = document.querySelector('#searchWord').value;
   fetch('<%=request.getContextPath()%>/board/searchList.bo?searchField=' + searchField + '&searchWord=' + searchWord + '&page=' + pageNum)
       .then(response => response.json())
       .then(data => {
           console.log(data);
           makeHtml(data.posts); // 게시글
           makePage(data.paging); // 페이징
           displayTotalCount(data.totalCount); // 전체 글의 수 표시
       })
       .catch(error => console.error('Error fetching comments:', error));
}

function displayTotalCount(totalCount) {
    const totalCountElement = document.getElementById('totalCount');
    if (totalCountElement) {
        totalCountElement.textContent = '전체 게시글 수: ' + totalCount;
    } else {
        console.error('Element with ID "totalCount" not found');
    }
}

function makeHtml(posts) {
    let html = ''; 
    for (let u of posts) {
        html += '<tr onclick="redirectToView(' + u['fr_idx'] + ')">';
        html += '<td>' + u['fr_idx'] + '</td>';
        html += '<td>' + u['user_id'] + '</td>';
        // 텍스트가 1줄을 넘어갈때 ...으로 처리
        html += '<td style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 200px;">' + u['fr_title'] + '</td>';
        html += '<td>' + u['fr_visitnum'] + '</td>';
        html += '<td>' + u['fr_like'] + '</td>';
        html += '<td>' + u['fr_regd'] + '</td>';
        html += '<td><a href="download.jsp?oName=' + u['fr_ofile'] + '&sName=' + u['fr_sfile'] + '"><img src="../resources/images/download.svg"></a></td>';
        html += '</tr>';         
    }
    console.log(html);
//     $('#searchList').html(html);
    document.querySelector('#searchList tbody').innerHTML = html;
}

function makePage(paging) {
    let html = '<div class="admin_pagination">'; 
    if (paging.prev) {
        html += '<a href="javascript:void(0)" onclick="searchList(' + (paging.startPage - 1) + ')">[Prev]</a>';
    }
    for (let i = paging.startPage; i <= paging.endPage; i++) {
        if (i == paging.pageNum) {
            html += '<a href="javascript:void(0)" class="active">' + i + '</a>';
        } else {
            html += '<a href="javascript:void(0)" onclick="searchList(' + i + ')">' + i + '</a>';
        }
    }
    if (paging.next) {
        html += '<a href="javascript:void(0)" onclick="searchList(' + (paging.endPage + 1) + ')">[Next]</a>';
    }
    html += '</div>';
    console.log(html);
    document.getElementById('pageList').innerHTML = html;
}

document.addEventListener('DOMContentLoaded', function () {
    searchList();
});

</script>
</head>
<body>
   
   <main id="container">
      <div class="inner bbs_inner">
         <h2>자유게시판</h2>
         <hr>
         <div class="search_area">
            <div id ="totalCount">
<%--                <p><b>전체 게시글 수 :</b>&nbsp;<%=p.getTotal()%></p> --%>
            </div>
            <div class="search_wrap">
                  <select id="searchField">
                     <option value="fr_title">제목</option>
                     <option value="fr_cont">내용</option>
                  </select> <input type="text" id="searchWord" onkeyup="searchList()">
                  <input class="search_btn" type="submit" value="Search">
            </div>
         </div>
<script>

</script>
         
         <div class="board_list_wrap">
            <table id="searchList">
               <thead>
                  <tr>
                     <th class="col1">번호</th>
                     <th class="col2">작성자</th>
                     <th class="col3">제목</th>
                     <th class="col4"><i class="xi-eye"></i> 조회수</th>
                     <th class="col5"><i class="xi-heart"></i> 좋아요</th>
                     <th class="col6">작성일</th>
                     <th class="col8">다운로드</th>
                  </tr>

               </thead>
               <tbody>
               
               </tbody>
               </table>
            </div>
            <div class="submit_wrap"><a href="write.bo" class="btn btn-primary pull-right submit_btn">글쓰기 </a></div>
            <div id ="pageList"></div>       
         </div>
   </main>
   <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>