package mpjt.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mpjt.common.JDBCConnect;
import mpjt.dto.LikedPostDTO;

public class MyLikesDAO {
	 public List<LikedPostDTO> getLikedPosts(String userId) {
	        List<LikedPostDTO> likedPosts = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            conn = JDBCConnect.getConnection();
	            String sql = "SELECT b.fr_idx, b.fr_title, b.fr_cont, b.fr_regd, b.fr_like, b.user_id " +
	                         "FROM user u " +
	                         "JOIN likes l ON u.user_id = l.user_id " +
	                         "JOIN free_board b ON l.fr_idx = b.fr_idx " +
	                         "WHERE u.user_id = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, userId);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	                LikedPostDTO post = new LikedPostDTO();
	                post.setFr_idx(rs.getInt("fr_idx"));
	                post.setFr_title(rs.getString("fr_title"));
	                post.setUser_id(rs.getString("user_id"));
	                post.setFr_cont(rs.getString("fr_cont"));
	                post.setFr_regd(rs.getString("fr_regd"));
	                post.setFr_like(rs.getInt("fr_like"));
	                likedPosts.add(post);
	                System.out.println(post);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            JDBCConnect.close(rs, pstmt, conn);
	        }

	        return likedPosts;
	    }
}
