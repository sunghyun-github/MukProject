package mpjt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mpjt.dao.MyLikesDAO;
import mpjt.dto.LikedPostDTO;

@WebServlet("/MyLikesServlet")
public class MyLikesServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
private final MyLikesDAO myLikesDAO = new MyLikesDAO();
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8"); // 한글처리
    	
    	String userId = (String) request.getSession().getAttribute("user_id");
         List<LikedPostDTO> likedPosts = myLikesDAO.getLikedPosts(userId);

         String json = new Gson().toJson(likedPosts);

         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");
         response.getWriter().write(json);
         
    }
}
