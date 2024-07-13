<%@page import="mpjt.dao.BoardDAO"%>
<%@page import="mpjt.dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>
<% // param
request.setCharacterEncoding("utf-8");
String sNum = request.getParameter("num"); 
int num = Integer.parseInt(sNum);
BoardDTO dto = new BoardDTO();

dto.setFr_idx(num);

%>    
<% // db - select
BoardDAO dao = new BoardDAO();

// view content
dto = dao.selectView(dto);

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/update.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
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
<%@ include file="../common/menu.jsp" %>
<main id="container">
<section class="board_write">
<div class="inner">

<div class="board_write_wrap">
    <form name="writeForm" action="<%=request.getContextPath() %>/board/UpdateProc.bo?num=<%= dto.getFr_idx() %>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="num" value="<%=num%>">
        <table class="board_table">
        	<tr>
                <td class="write_label">제목</td>
                <td>
                    <input type="text" name="title" value="<%=dto.getFr_title() %>" class="input_field">
                </td>
            </tr>
            <tr>
				<td class="write_label">내용</td>
				<td><textarea name="content" class="textarea_field"><%=dto.getFr_cont() %></textarea></td>
			</tr>
            <tr>
               <td class="write_label">파일</td>
               <td><input type="file" name="attachedFile" class="input_file" value="<%=dto.getFr_ofile()%>"></td>
            </tr>
            <tr>
                <td colspan="2" class="action-buttons">
                    <a href="bbs.bo" class="btn btn-secondary">목록보기</a>
                    <a href="view.bo?num=<%=num%>" class="btn btn-secondary">게시글로 이동</a>     
                <% if(session.getAttribute("user_id") != null && session.getAttribute("user_id").equals(dto.getUser_id())) { %>
                    <a href="javascript:validateForm();" class="btn btn-primary">수정하기</a> 
                <% } %>
                </td>
            </tr>        
        </table>
    </form>
</div>
</div>
</section>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>