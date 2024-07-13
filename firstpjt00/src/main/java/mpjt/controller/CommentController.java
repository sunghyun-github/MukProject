package mpjt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mpjt.dao.CommentDAO;
import mpjt.dto.CommentDTO;

@WebServlet("*.co")
public class CommentController extends HttpServlet {
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


        if (action.equals("/commentList.co")) {
            int fr_idx = Integer.parseInt(request.getParameter("idx"));
            CommentDTO cdto = new CommentDTO();
            cdto.setFr_idx(fr_idx);
            
            // 세션에서 user_id 가져오기
            String userId = (String) request.getSession().getAttribute("user_id");
            
            CommentDAO cdao = new CommentDAO();
            List<CommentDTO> commentList = cdao.getComment(cdto, userId); // userId 전달
            
            Gson gson = new Gson();
            String json = gson.toJson(commentList);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
        } else if (action.equals("/commentInsert.co")) {
            String sFr_idx = request.getParameter("idx");
            String user_id = request.getParameter("user_id");
            String cont = request.getParameter("cont");
            int Fr_idx = Integer.parseInt(sFr_idx);

            CommentDTO cto = new CommentDTO();
            cto.setFr_idx(Fr_idx);
            cto.setUser_id(user_id);
            cto.setFrc_cont(cont);

            CommentDAO cdao = new CommentDAO();
            List<CommentDTO> commentLists = cdao.insertWrite(cto);

            Gson gson = new Gson();
            String json = gson.toJson(commentLists);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
        } else if (action.equals("/commentDelete.co")) {
            String sFrc_idx = request.getParameter("frc_idx");
            int Frc_idx = Integer.parseInt(sFrc_idx);

            CommentDTO cdto = new CommentDTO();
            cdto.setFrc_idx(Frc_idx);

            CommentDAO cdao = new CommentDAO();
            int result = cdao.deleteComment(cdto);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", result > 0);

            Gson gson = new Gson();
            String json = gson.toJson(responseData);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
        } else if (action.equals("/commentLikeToggle.co")) {
             int frc_idx = Integer.parseInt(request.getParameter("frc_idx"));
              String user_id = request.getParameter("user_id");
              System.out.println(frc_idx);
              System.out.println(user_id);
              CommentDAO dao = new CommentDAO();
              boolean liked = dao.toggleLike(frc_idx, user_id);
              int likeCount = dao.getCommentLikeCount(frc_idx);

              Map<String, Object> responseData = new HashMap<>();
              responseData.put("success", true);
              responseData.put("likes", likeCount);
              responseData.put("userLiked", liked);

              response.setContentType("application/json");
              response.setCharacterEncoding("UTF-8");
              response.getWriter().write(new Gson().toJson(responseData));
           
        } else if (action.equals("/checkLikeStatus.co")) {
            int frc_idx = Integer.parseInt(request.getParameter("frc_idx"));
             String user_id = request.getParameter("user_id");

             CommentDAO dao = new CommentDAO();
             boolean userLiked = dao.checkUserLiked(frc_idx, user_id);
             int likes = dao.getLikesCount(frc_idx);
             System.out.println(userLiked);
             Map<String, Object> responseData = new HashMap<>();
             responseData.put("success", true);
             responseData.put("likes", likes);
             responseData.put("userLiked", userLiked);

             response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             response.getWriter().write(new Gson().toJson(responseData));
           
        }
    }
}