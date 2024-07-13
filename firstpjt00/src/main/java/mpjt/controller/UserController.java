package mpjt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mpjt.common.JDBCConnect;
import mpjt.dao.UserDAO;
import mpjt.dto.UserDTO;
import mpjt.service.user.UserService;

@WebServlet("*.do")
public class UserController extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }

   protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8"); // 한글처리

      String uri = request.getRequestURI();
      String action = uri.substring(uri.lastIndexOf("/"));

      if(action.equals("/delete.do")) {
         // delete.jsp로 이동
         String path = request.getContextPath() + "/user/delete.jsp";
         response.sendRedirect(path);
      } else if(action.equals("/update.do")) {
         // update.jsp로 이동
         String path = request.getContextPath() + "/user/update.jsp";
         response.sendRedirect(path);
      } else if(action.equals("/list.do")) {
         // list.jsp로 이동
         String path = request.getContextPath() + "/user/list.jsp";
         response.sendRedirect(path);
      } else if(action.equals("/main.do")) {
         // 메인 페이지로 이동
         String path = request.getContextPath()+ "/user/main.jsp"; // 올바른 경로로 수정
         response.sendRedirect(path);
      }else if(action.equals("/join.do")) {
         // 조인 페이지로 이동
         String path = request.getContextPath() + "/user/join.jsp"; // 올바른 경로로 수정
         response.sendRedirect(path);
      }
      else if(action.equals("/login.do")) {
         // 조인 페이지로 이동
         String path = request.getContextPath() + "/user/login.jsp"; // 올바른 경로로 수정
         response.sendRedirect(path);
      }
      else if(action.equals("/logout.do")) {
         // 로그아웃 처리
         HttpSession session = request.getSession();
         session.invalidate();
         String path = request.getContextPath() + "/user/main.jsp";
         response.sendRedirect(path);
      } else if(action.equals("/List.do")) {
         // 유저 목록 가져오기
         UserDAO dao = new UserDAO();
         List<UserDTO> userList =  dao.getUsers();
         request.setAttribute("List",userList);
         // List.jsp로 포워딩
         String path =  request.getContextPath() + "./List.jsp";
         RequestDispatcher dispatcher = request.getRequestDispatcher(path);
         dispatcher.forward(request, response);

      }else if(action.equals("/joinProc.do")) {
           // 회원가입 처리
           String id = request.getParameter("user_id");
           String password = request.getParameter("user_password");
           String name = request.getParameter("user_name");
           String gender = request.getParameter("user_gen");
           String role = request.getParameter("user_role");
           String email = request.getParameter("user_email"); 

           // 입력값이 공백인지 확인
           if (role.isEmpty() || id.isEmpty() ||
               password.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty()) {
               request.setAttribute("message", "입력되지 않은 값이 있습니다. 값을 확인해주세요 !");
               // 입력페이지로 다시 이동
               request.getRequestDispatcher("join.jsp").forward(request, response);
           } else {
               try {
                   UserDAO userModel = new UserDAO();
                   boolean isSuccess = userModel.registerUser(id, password, name, gender, email, role); // registerUser 메소드에 이메일 파라미터 추가

                   if (isSuccess) {
                       // 회원가입이 성공한 경우
                       request.setAttribute("successRedirect", "login.jsp");
                       request.getRequestDispatcher("join.jsp").forward(request, response);
                   } else {
                       // 중복된 아이디인 경우
                       request.setAttribute("message", "중복된 아이디입니다.");
                       request.getRequestDispatcher("join.jsp").forward(request, response);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   // 예외 발생 시
                   request.setAttribute("message", "회원가입 중 오류가 발생했습니다.");
                   request.getRequestDispatcher("join.jsp").forward(request, response);
               }
           }
       } else if (action.equals("/deleteProc.do")) {
    	    request.setCharacterEncoding("UTF-8");

    	    HttpSession session = request.getSession();
    	    String userId = (String) session.getAttribute("user_id");
    	    String password = request.getParameter("user_password");

    	    UserDAO userDAO = new UserDAO();
    	    boolean isDeleted = userDAO.deleteUser(userId, password);

    	    if (isDeleted) {
    	        session.invalidate();
    	        String message = URLEncoder.encode("회원탈퇴에 성공했습니다. 그동안 이용해주셔서 감사합니다.", "UTF-8");
    	        response.sendRedirect("main.jsp?message=" + message);
    	    } else {
    	        response.sendRedirect("delete.jsp?error=true");
    	    }
    	} else if(action.equals("/loginProc.do")) { 
           // 1. 값을 받고 찍어 본다. 꼭~~
           request.setCharacterEncoding("utf-8"); // 한글 처리
           String id = request.getParameter("user_id");
           String password = request.getParameter("user_password");

           int idx = 0;
           String name = "";
           String role = "";
           boolean isLogin = false;
           String errorMsg = null;

           // 2. DB 처리를 한다.
           UserDAO dao = new UserDAO();
           UserDTO dto = new UserDTO();
           dto.setUser_id(id);
           dto = dao.getUser(dto);

           if(dto != null) {
              // 패스워드가 같을 시 실행됨
               if(password.equals(dto.getUser_password())) {
                   isLogin = true;
                   role = dao.getUserRole(id); // 사용자 역할 가져오기
               } else { // 패스워드가 다를때 errorMsg에 비밀번호가 틀립니다! 값 넣어줌
                   errorMsg = "비밀번호가 틀립니다!";
               }
           } else { // 위를 아예 실행하지 못할때 errorMsg에 아이디가 존재하지 않습니다! 값 넣어줌
               errorMsg = "아이디가 존재하지 않습니다!";
           }

           String jspFile = "";
           if (isLogin){ // 꼭 써야하는거 
               HttpSession session =  request.getSession();
               session.setAttribute("user_id", id);
               jspFile = "main.do";
           } else { // 비밀번호만 틀렸을 경우이기 때문에 ID값을 보존시켜줌
               if (errorMsg.equals("비밀번호가 틀립니다!")) {
                   request.setAttribute("userId", id); // 입력한 아이디 값을 request 객체에 저장
               }
               request.setAttribute("errorMsg", errorMsg); // 오류 메시지를 request 객체에 저장
               jspFile = "login.jsp";
           }

           // 3. move
           if (isLogin) {
               String path = request.getContextPath() + "/user/" + jspFile;
               response.sendRedirect(path);
           } else {
               request.getRequestDispatcher("/user/" + jspFile).forward(request, response);
           }
       } else if (action.equals("/updateProc.do")) {
           response.setContentType("text/html; charset=UTF-8");
           request.setCharacterEncoding("UTF-8");
           PrintWriter out = response.getWriter();

           String id = (String) request.getSession().getAttribute("user_id");
           String currentPassword = request.getParameter("current_password"); // 사용자가 입력한 현재 비밀번호
           String newPassword = request.getParameter("new_password"); // 사용자가 입력한 새 비밀번호
           String role = request.getParameter("user_role");
           String name = request.getParameter("user_name");
           String gender = request.getParameter("user_gen");
           String email = request.getParameter("user_email"); // 사용자가 입력한 새 이메일

           UserDAO dao = new UserDAO();
           UserDTO user = new UserDTO();
           user.setUser_id(id);
           user = dao.getUser(user); // 기존의 getUser 메서드 사용

           if (user == null || !user.getUser_password().equals(currentPassword)) {
               out.println("<script>alert('현재 비밀번호가 일치하지 않습니다.'); history.back();</script>");
           } else {
               if (newPassword != null && !newPassword.isEmpty()) {
                   if (user.getUser_password().equals(newPassword)){
                       out.println("<script>alert('기존의 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.'); history.back();</script>");
                   } else {
                       if (!"관리자".equals(user.getUser_role())) {
                           role = user.getUser_role();
                       }

                       Connection conn = null;
                       PreparedStatement pstmt = null;

                       try {
                           conn = JDBCConnect.getConnection();
                           String sql = "UPDATE user SET user_role=?, user_password=?, user_name=?, user_gen=?, user_email=? WHERE user_id = ?";
                           pstmt = conn.prepareStatement(sql);
                           pstmt.setString(1, role);
                           pstmt.setString(2, newPassword);
                           pstmt.setString(3, name);
                           pstmt.setString(4, gender);
                           pstmt.setString(5, email);
                           pstmt.setString(6, id);
                           pstmt.executeUpdate();

                           out.println("<script>alert('회원정보 수정 완료!'); window.location.href='main.jsp';</script>");
                       } catch (Exception e) {
                           e.printStackTrace();
                       } finally {
                           JDBCConnect.close(pstmt, conn);
                       }
                   }
               } else {
                   out.println("<script>alert('새 비밀번호를 입력하세요.'); history.back();</script>");
               }
           }
       }
       else if (action.equals("/updateRole.do")) {
           request.setCharacterEncoding("utf-8");

           // 사용자가 선택한 새로운 역할과 사용자 ID를 받아옴
           String userId = request.getParameter("user_id");
           String newRole = request.getParameter("new_role");

           // DAO 객체 생성
           UserDAO dao = new UserDAO();

           // 사용자의 역할을 업데이트하는 메소드 호출
           boolean isSuccess = dao.updateUserRole(userId, newRole);

           // 역할 업데이트 성공 여부에 따라 다른 페이지로 이동
           if (isSuccess) {
               // 역할 업데이트가 성공한 경우
               response.sendRedirect(request.getContextPath() + "/list.do"); // 업데이트 후 회원 목록 페이지로 이동
           } else {
              // 역할 업데이트가 실패한 경우
              request.setAttribute("errorMessage", "변경에 실패했습니다.");
              request.getRequestDispatcher("/list.jsp").forward(request, response);

           }
       } else if (action.equals("/findIdProc.do")) {
           // 아이디 찾기 처리
           String name = request.getParameter("user_name");
           String gender = request.getParameter("user_gen");
           String email = request.getParameter("user_email");

           System.out.println(name);
           System.out.println(gender);
           System.out.println(email);
           if (name == null || name.isEmpty() || gender == null || gender.isEmpty() || email == null || email.isEmpty()) {
               request.setAttribute("errorMessage", "이름, 성별, 이메일을 모두 입력해주세요.");
               request.getRequestDispatcher("findId.jsp").forward(request, response);
               return;
           }

           UserDAO dao = new UserDAO();
           try {
               String userId = dao.findUserIdByEmailAndGender(name, gender, email);
               
               if (userId != null) {
                   request.setAttribute("userId", userId);
                   request.getRequestDispatcher("findIdResult.jsp").forward(request, response);
               } else {
            	   System.out.println(userId);
                   request.setAttribute("errorMessage", "입력한 정보로는 아이디를 찾을 수 없습니다.");
                   request.getRequestDispatcher("findId.jsp").forward(request, response);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       } else if (action.equals("/findPasswordProc.do")) {
           // 아이디 찾기 처리
           String userId = request.getParameter("user_id");
           String email = request.getParameter("user_email");

           System.out.println(userId);
           System.out.println(email);
           if (userId == null || userId.isEmpty() || email == null || email.isEmpty()) {
               request.setAttribute("errorMessage", "아이디, 이메일을 모두 입력해주세요.");
               request.getRequestDispatcher("findPassword.jsp").forward(request, response);
               return;
           }

           UserDAO dao = new UserDAO();
           try {
               String password = dao.findUserPasswordByEmailAndUserid(userId, email);
               System.out.println(password);
               if (password != null) {
                   request.setAttribute("password", password);
                   request.getRequestDispatcher("findPasswordResult.jsp").forward(request, response);
               } else {
                   request.setAttribute("errorMessage", "입력한 정보로는 아이디를 찾을 수 없습니다.");
                   request.getRequestDispatcher("findPassword.jsp").forward(request, response);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       } else if (action.equals("/idcheckProc.do")) {
           // ID 체크 처리
           String id = request.getParameter("user_id");
           UserDAO dao = new UserDAO();
           UserDTO dto = dao.getUserById(id);

           int rs = 0;
           if (dto != null) {
               rs = 1;
           }

           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           PrintWriter out = response.getWriter();
           out.print("{\"rs\":\"" + rs + "\"}");
           out.flush();
           out.close();
       } else if (action.equals("/emailcheckProc.do")) {
           // 이메일 체크 처리
           String userEmail = request.getParameter("user_email");
           if (userEmail != null) {
               userEmail = java.net.URLDecoder.decode(userEmail, "UTF-8");
           }

           UserDAO dao = new UserDAO();
           UserDTO user = dao.getUserByEmail(userEmail);

           int rs = 0;
           if (user != null) {
               rs = 1;
           }

           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           PrintWriter out = response.getWriter();
           out.print("{\"rs\":" + rs + "}");
           out.flush();
           out.close();
       }

      
   }
}