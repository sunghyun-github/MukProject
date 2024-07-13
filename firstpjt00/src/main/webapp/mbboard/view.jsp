<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="mpjt.dto.CommentDTO"%>
<%@page import="java.util.List"%>
<%@page import="mpjt.dao.CommentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mpjt.dao.BoardDAO"%>
<%@page import="mpjt.dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
// param
BoardDTO dto = (BoardDTO)request.getAttribute("dto");

// 현재 로그인한 사용자의 ID를 가져옵니다.
String currentUser = (String)session.getAttribute("user_id");

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글 상세보기</title>
<link rel="icon" href="../resources/images/favicon.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script>
document.addEventListener("DOMContentLoaded", function() {
   
   checkLikeStatus();
   
    // 현재 로그인한 사용자의 ID와 작성자 ID를 비교하여 수정 및 삭제 링크 비활성화
    var currentUser = "<%=currentUser%>";
    var postAuthor = "<%=dto.getUser_id()%>";
    if (currentUser !== postAuthor) {
        document.querySelectorAll('a[href*="update.bo"]').forEach(function(link) {
            link.style.display = 'none';
        });
        document.querySelectorAll('a[href*="javascript:del"]').forEach(function(link) {
            link.style.display = 'none';
        });
    }
});


// 삭제
function del(num) {
    const input = confirm("정말 삭제 할까요?");
    if (input) {
        location.href = "<%=request.getContextPath()%>/board/deleteProc.bo?num=" + num;
    } else {
        alert('삭제를 취소 했습니다.');
    }
}
   
//게시글 좋아요
function like() {
    const idx = <%=dto.getFr_idx()%>;
    const userId = "<%= currentUser%>";
    
    if (userId.trim() === 'null') {
        alert('로그인 후 좋아요를 누를 수 있습니다.');
        return;
    }

    fetch('<%=request.getContextPath()%>/likeBoard.bo?idx=' + idx + '&user_id=' + userId)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById('likeCount').innerText = '좋아요: ' + data.likes;
                // 버튼 상태 업데이트
                if (data.userLiked) {
                    document.getElementById('likeButton').classList.add('liked');
                } else {
                    document.getElementById('likeButton').classList.remove('liked');
                }
            }
        })
        .catch(error => console.error('Error liking post:', error));
}


//게시글 좋아요 확인
function checkLikeStatus() {
    const idx = <%=dto.getFr_idx()%>;
    const userId = "<%= currentUser%>";

    fetch('<%=request.getContextPath()%>/board/checkLikeStatus.bo?idx=' + idx + '&user_id=' + userId)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                if (data.userLiked) {
                    document.getElementById('likeButton').classList.add('liked');
                } else {
                    document.getElementById('likeButton').classList.remove('liked');
                }
            }
        })
        .catch(error => console.error('Error liking post:', error));
}


// 댓글 입력
function commentInsert() {
    const idx = <%=dto.getFr_idx()%>;
    const user_id = "<%=currentUser%>";
    const cont = document.getElementById('content').value;

    // 입력된 내용이 없을 때 알림 표시
    if (cont.trim() === "") {
        alert("내용을 입력해주세요!");
        return;
    }

    fetch('<%=request.getContextPath()%>/board/commentInsert.co?idx=' + idx + '&user_id=' + user_id + '&cont=' + cont)
        .then(response => response.json())
        .then(data => {
            getComment(); // 댓글이 추가된 후에 댓글을 다시 불러오기
            document.getElementById('content').value = ''; // 입력란 초기화
        })
        .catch(error => console.error('Error adding comment:', error));
}

document.addEventListener("DOMContentLoaded", function() {
    getComment(); // 페이지 로드 시에 댓글을 불러오는 함수 호출
});


//댓글 불러오기
function getComment() {
 const idx = <%=dto.getFr_idx()%>;
 fetch('<%=request.getContextPath()%>/board/commentList.co?idx=' + idx)
     .then(response => response.json())
     .then(data => {
    makeHtml(data);
    data.forEach(comment => {
        const commentIdx = comment.frc_idx; // 댓글 객체에서 frc_idx 속성을 얻어옴
        checkCommentLike(commentIdx); // 댓글의 좋아요 상태를 확인하는 함수 호출
    });
})
.catch(error => console.error('Error fetching comments:', error));
}


//댓글의 내용들 추가
function makeHtml(data) {
 let html = '';
 const currentUser = "<%= currentUser %>";

 for (const u of data) {
    html += '<ul>';
     html += '<li class="co_userid">' + '<span>' + u['user_id'] + '</span>' + u['frc_regd'] + '</li>';
	 // 좋아요 토글 버튼 추가
     html += '<li class="co_like_btn"> <button class="likeButton_co" id="commentToggleLikeButton-' + u['frc_idx'] + '" onclick="toggleCommentLike(' + u['frc_idx'] + ')"></button></li>';
     html += '<li id="commentLike-' + u['frc_idx'] + '" class="co_like">' + '좋아요: ' + u['frc_like'] + '</li>'; // "좋아요: " 추가
     html += '<li class="co_cont">' + u['frc_cont'] + '</li>';
     html += '<li class="co_btn_wrap">';
     // 작성자와 현재 로그인한 사용자가 일치하면 삭제 버튼 생성
     if (u['user_id'] === currentUser) {
         html += '<button id="deleteButton-' + u['frc_idx'] + '" onclick="deleteComment(' + u['frc_idx'] + ')">댓글 삭제</button>';
     }
     html += '</li>';
     html += '</ul>';
 }
 $('#comments').html(html);
}


// 댓글 삭제
function deleteComment(frc_idx) {
   if (confirm('정말로 댓글을 삭제하시겠습니까?')) {
       $.ajax({   
           url: '<%=request.getContextPath()%>/board/commentDelete.co?frc_idx=' + frc_idx,
           type: 'POST',
           success: function(result) {
               alert('댓글이 삭제되었습니다.');
               getComment();
           },
           error: function(err) {
               alert('댓글 삭제에 실패했습니다.');
               console.error(err);
           }
       });
   }
}

//댓글 좋아요 토글버튼
function toggleCommentLike(commentIdx) {
    const userId = "<%= currentUser%>";
    const likeButton = document.getElementById("commentToggleLikeButton-" + commentIdx);

    if (userId.trim() === 'null') {
        alert('로그인 후 좋아요를 누를 수 있습니다.');
        return;
    }
    
    fetch('<%=request.getContextPath()%>/commentLikeToggle.co?frc_idx=' + commentIdx + '&user_id=' + userId)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const likeCountElement = document.getElementById("commentLike-" + commentIdx);
                likeCountElement.innerText = '좋아요: ' + data.likes; // 좋아요 수 업데이트 시 "좋아요: " 추가

                // 좋아요 상태를 서버에서 받아옴
                if (data.userLiked) {
                    document.getElementById("commentToggleLikeButton-" + commentIdx).classList.add('liked_co');
                } else {
                    document.getElementById("commentToggleLikeButton-" + commentIdx).classList.remove('liked_co');
                }
            } else {
                alert(data.message); // 오류 메시지 출력
            }
        })
        .catch(error => console.error('Error toggling comment like:', error)); // 에러 콘솔에 기록
}

//초기 로드 시 각 댓글의 좋아요 상태를 확인하여 업데이트
function checkCommentLike(commentIdx) {
    const userId = "<%= currentUser%>";
    const likeButton = document.getElementById("commentToggleLikeButton-" + commentIdx);

    fetch('<%=request.getContextPath()%>/checkLikeStatus.co?frc_idx=' + commentIdx + '&user_id=' + userId)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const likeCountElement = document.getElementById("commentLike-" + commentIdx);
                likeCountElement.innerText = '좋아요: ' + data.likes; // 좋아요 수 업데이트 시 "좋아요: " 추가

                // 좋아요 상태를 서버에서 받아옴
                if (data.userLiked) {
                    document.getElementById("commentToggleLikeButton-" + commentIdx).classList.add('liked_co');
                } else {
                    document.getElementById("commentToggleLikeButton-" + commentIdx).classList.remove('liked_co');
                }
            } else {
                alert(data.message); // 오류 메시지 출력
            }
        })
        .catch(error => console.error('Error toggling comment like:', error)); // 에러 콘솔에 기록
}

</script>

<style>
   
</style>

</head>
<body>
 <%@ include file="../common/menu.jsp"%>
 <main id="container">
    <div class="board_view">
    <div class="inner">
       <div class="submit_wrap">
          <a class="list_btn" href="bbs.bo"><img class="list" src="../resources/images/list.png" alt="">글 목록</a>
       </div>
       <h2 class="blind">글 상세보기</h2>
       <div class="board_view_wrap">
          <table>
             <tr>
                <td class="col1" colspan="3"><%=dto.getFr_title()%></td>
                <td class="col2"><span class="viewCount">조회수: <%=dto.getFr_visitnum()%></span><span id="likeCount">좋아요: <%=dto.getFr_like()%></span></td>
             </tr>
             <tr>
                <td class="col3" colspan="3">작성자 : <%=dto.getUser_id()%></td>
                <td class="col4">작성 일자 : <%=dto.getFr_regd()%></td>
             </tr>
             <tr class="content">
                  <td class="col5" colspan="4">
                     <%
                        String originalFileName = dto.getFr_ofile();
                        String savedFileName = dto.getFr_sfile();
                        if (savedFileName != null && !savedFileName.isEmpty()) {
                            String fileExt = savedFileName.substring(savedFileName.lastIndexOf(".") + 1).toLowerCase();
                            if (fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png") || fileExt.equals("gif")) { %>
			                     <div class="image_container">
			                        <img src="<%= request.getContextPath() %>/Uploads/<%= savedFileName %>" alt="<%= originalFileName %>">
			                     </div> 
			             <% } else { %> 
		                        <a href="<%= request.getContextPath() %>/download.jsp?oName=<%= java.net.URLEncoder.encode(originalFileName, "UTF-8") %>&sName=<%= java.net.URLEncoder.encode(savedFileName, "UTF-8") %>">[다운로드]</a>
		                 <% }
                        } else {
                            out.print("");
                        } %>
                     <p class="fr_cont"><%=dto.getFr_cont()%></p> 
                     <div class="like_wrap">
                     	<button id="likeButton" class="likeButton" onclick="like()"></button>
                     	<p>좋아요</p>
                     </div>
                     <%
                     if (currentUser != null && currentUser.equals(dto.getUser_id())) {
                     %>
                      <div class="submit_wrap">
                        <a class="submit_btn" href="update.jsp?num=<%=dto.getFr_idx()%>">수정</a>
                        <a class="submit_btn" href="javascript:del('<%=dto.getFr_idx()%>');">삭제</a>
                     </div> <% } %>
                  	
                  </td>
               </tr>
            </table>
          <% if(currentUser != null) { %>
             <form action="<%=request.getContextPath()%>/commentInsert.co" method="post" class="co_form">
                <input placeholder="댓글을 입력해주세요." type="text" name="content" id="content" class="co_input"> <input class="co_btn" type="button" value="확인" onclick="commentInsert()"><br>
             </form>
          <% } else { %>
             <form id="commentForm"  class="co_form">
               <input placeholder="로그인 후 댓글을 입력해주세요." type="text" name="content" id="content"  class="co_input" class="co_input"> <input class="co_btn" type="button" value="확인" onclick="commentInsert()"><br>
            </form>
            <script>
              function commentInsert() {
                  var commentContent = document.getElementById("content").value;
                  if(commentContent.trim() === "") {
                      alert("댓글을 입력하세요!");
                      return;
                  }
                  alert("로그인 후에 댓글을 작성할 수 있습니다!");
              }
            </script>
          <% } %>
         <p>댓글 </p>
         <hr>
            <div id="comments"></div>
       </div>
    </div>
    </div>
 </main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>