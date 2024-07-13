package mpjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mpjt.dto.FavoriteDTO;
import mpjt.common.JDBCConnect;

public class FavoriteDAO {

    public boolean addFavorite(String user_id, int rs_idx) {
       if (isFavorite(user_id, rs_idx)) {
            return false;
        }
        String SQL = "INSERT INTO rs_favorite (user_id, rs_idx) VALUES (?, ?)";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, rs_idx);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFavorite(String user_id, int rs_idx) {
        String SQL = "DELETE FROM rs_favorite WHERE user_id = ? AND rs_idx = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, rs_idx);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<FavoriteDTO> getFavoritesByUser(String user_id) {
        List<FavoriteDTO> favoriteList = new ArrayList<>();
        String SQL = "SELECT f.favorite_idx, f.rs_idx, f.user_id, r.rs_name, r.rs_type, r.rs_addr " +
                       "FROM rs_favorite f " +
                       "JOIN restaurant r ON f.rs_idx = r.rs_idx " +
                       "WHERE f.user_id = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FavoriteDTO favorite = new FavoriteDTO(
                        rs.getInt("favorite_idx"),
                        rs.getInt("rs_idx"),
                        rs.getString("user_id"),
                        rs.getString("rs_name"),
                        rs.getString("rs_type"),
                        rs.getString("rs_addr")
                    );
                    favoriteList.add(favorite);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoriteList;
    }

    public boolean isFavorite(String user_id, int rs_idx) {
        String SQL = "SELECT COUNT(*) FROM rs_favorite WHERE user_id = ? AND rs_idx = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, rs_idx);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}