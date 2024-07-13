<%@page import="mpjt.dto.RsDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 1. 값을 받고 찍어 본다. 꼭~~
request.setCharacterEncoding("utf-8"); // 한글 처리
String name = request.getParameter("rs_name");
String addr = request.getParameter("rs_addr");
String num = request.getParameter("rs_num");
String hour = request.getParameter("rs_hour");
String menu = request.getParameter("rs_menu");
String price = request.getParameter("rs_price");
String type = request.getParameter("rs_type");
String comment = request.getParameter("rs_comment");
String img = request.getParameter("rs_img");

// 2. 값이 비어 있는지 확인
if (name.isEmpty() || addr.isEmpty() || type.isEmpty()) {
    // 만약 어떤 값이든 비어 있다면, 사용자에게 알리고 가입 프로세스 중단
    out.println("<script>alert('입력되지 않은 값이 있습니다.'); history.back();</script>");
} else {
    // 값이 모두 존재할 때 DB 처리를 한다.
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null; // select

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3307/mpjt?serverTimezone=UTC";
    String user = "root";
    String pw = "rpass";

    // 중복 체크를 위한 변수
    boolean isDuplicate = false;

    try {
        // 1. driver loading
        Class.forName(driver);   
        // 2. connection
        conn = DriverManager.getConnection(url, user, pw);
        out.print("conn ok!!");
        
        // 3. 중복 체크
        String checkSql = "SELECT rs_name FROM restaurant WHERE rs_name=?";
        pstmt = conn.prepareStatement(checkSql);
        pstmt.setString(1, name);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            // 이미 존재하는 가게인 경우
            isDuplicate = true;
        }
        
        // 중복이 아닌 경우에만 사용자 추가
        if (!isDuplicate) {
            // 4. sql 창
            String insertSql = "insert into restaurant (rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_comment, rs_img) values(?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, name);
		    pstmt.setString(2, addr);
		    pstmt.setString(3, num);
		    pstmt.setString(4, hour);
		    pstmt.setString(5, menu);
		    pstmt.setString(6, price);
		    pstmt.setString(7, type);
		    pstmt.setString(8, comment);
		    pstmt.setString(9, img);
            // 5. execute
            pstmt.executeUpdate();   // insert, update, delete
        }
        
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    // 중복 체크 후 처리
    if (isDuplicate) {
        // 중복된 ID인 경우 메시지 출력 후 이전 페이지로 돌아감
        out.println("<script>alert('중복된 음식점입니다.'); history.back();</script>");
    } else {
        // 중복되지 않은 경우 로그인 페이지로 이동
        response.sendRedirect("rsList.jsp");
    }
}
%>