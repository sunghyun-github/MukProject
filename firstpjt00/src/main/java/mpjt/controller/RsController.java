package mpjt.controller;

import mpjt.dao.FavoriteDAO;
import mpjt.dao.RsDAO;
import mpjt.dto.FavoriteDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/map/deleteRestaurant", "/favorite", "/map/RsController"})
public class RsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FavoriteDAO favoriteDAO;

    @Override
    public void init() throws ServletException {
        favoriteDAO = new FavoriteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");

        if ("/map/deleteRestaurant".equals(path)) {
            deleteRestaurant(request, response);
        } else if ("/favorite".equals(path)) {
            getFavorites(request, response);
        } else if ("/map/RsController".equals(path) && "delete".equals(action)) {
            deleteRestaurant(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");

        if ("/map/RsController".equals(path)) {
            if ("saveRating".equals(action)) {
                saveRating(request, response);
            } else {
                doGet(request, response);
            }
        } else {
            handleFavoriteActions(request, response, action);
        }
    }

    private void saveRating(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String rs_idx = request.getParameter("rs_idx");
        String rating = request.getParameter("rating");

        RsDAO dao = new RsDAO();
        boolean success = dao.updateRating(rs_idx, Integer.parseInt(rating));

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(success ? "success" : "failure");
    }

    private void handleFavoriteActions(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        String user_id = request.getParameter("user_id");
        int rs_id;

        try {
            rs_id = Integer.parseInt(request.getParameter("rs_id"));
        } catch (NumberFormatException e) {
            response.getWriter().write("fail");
            return;
        }

        boolean result = false;
        if ("add".equals(action)) {
            result = favoriteDAO.addFavorite(user_id, rs_id);
            response.getWriter().write(result ? "success" : "exists");
        } else if ("remove".equals(action)) {
            result = favoriteDAO.removeFavorite(user_id, rs_id);
            response.getWriter().write(result ? "success" : "fail");
        }
    }

    private void deleteRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rs_idx = request.getParameter("idx");
        if (rs_idx != null && !rs_idx.isEmpty()) {
            RsDAO dao = new RsDAO();
            try {
                int result = dao.deleteRestaurant(Integer.parseInt(rs_idx));
                if (result > 0) {
                    response.sendRedirect("rsList.jsp");
                } else {
                    response.getWriter().print("삭제할 수 없습니다.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().print("유효하지 않은 레스토랑 ID입니다.");
            }
        } else {
            response.getWriter().print("유효하지 않은 레스토랑 ID입니다.");
        }
    }

    private void getFavorites(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        List<FavoriteDTO> favoriteList = favoriteDAO.getFavoritesByUser(user_id);
        request.setAttribute("favoriteList", favoriteList);
        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
    }
}