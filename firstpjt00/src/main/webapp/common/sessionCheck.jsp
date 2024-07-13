<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// sessionCheck.jsp
String mUser_id = null;

if(session.getAttribute("user_id") == null){
	response.sendRedirect("../user/login.jsp");
}
%> 