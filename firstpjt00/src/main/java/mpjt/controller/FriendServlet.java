package mpjt.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import mpjt.dao.FriendDAO;
import mpjt.dto.FriendDTO;

@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8"); // 한글처리
    	
		String uri = request.getRequestURI();
    	String action = request.getParameter("action");
        FriendDAO dao = new FriendDAO();

        if ("addFriend".equals(action)) {
            String userId = request.getParameter("userId");
            String friendId = request.getParameter("friendId");
            
            boolean friendExists = dao.friendExists(friendId);
            boolean isAlreadyFriend = dao.isAlreadyFriend(userId, friendId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            if (!friendExists) {
                // 존재하지 않는 아이디인 경우
                response.getWriter().write("{\"status\": \"notExist\"}");
            } else if (isAlreadyFriend) {
                // 이미 친구인 경우
                response.getWriter().write("{\"status\": \"alreadyFriend\"}");
            } else {
                // 친구 추가 성공 여부
                FriendDTO fdto = new FriendDTO();
                fdto.setUser_id(userId);
                fdto.setFriend_id(friendId);

                boolean success = dao.addFriend(fdto);
                response.getWriter().write("{\"status\": \"" + (success ? "success" : "failure") + "\"}");
            }
            
        } else if ("viewFriends".equals(action)) {
        	String userId = request.getParameter("userId");
            List<FriendDTO> friends = dao.getFriends(userId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(friends));
        } else if ("removeFriend".equals(action)) {
            String userId = request.getParameter("userId");
            String friendId = request.getParameter("friendId");

            boolean success = dao.removeFriend(userId, friendId);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": \"" + (success ? "success" : "failure") + "\"}");
        } else {
            response.getWriter().println("Invalid action.");
        } 
    }
}
