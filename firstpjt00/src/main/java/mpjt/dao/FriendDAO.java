package mpjt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mpjt.common.JDBCConnect;
import mpjt.dto.BoardDTO;
import mpjt.dto.FriendDTO;

public class FriendDAO {
	// 친구 추가 메서드
    public boolean addFriend(FriendDTO fdto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCConnect.getConnection();

            // 친구 관계 중복 확인
            String checkSql = "SELECT COUNT(*) FROM Friends WHERE user_id = ? AND friend_id = ?";
            ps = conn.prepareStatement(checkSql);
            // DTO 에서  user_id, friend_id 값을 받아옴
            ps.setString(1, fdto.getUser_id());
            ps.setString(2, fdto.getFriend_id());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return false; // 중복된 친구 관계가 있는 경우
            }
            ps.close();

            // 친구 관계 추가
            String insertSql = "INSERT INTO Friends (user_id, friend_id) VALUES (?, ?)";
            ps = conn.prepareStatement(insertSql);
            ps.setString(1, fdto.getUser_id());
            ps.setString(2, fdto.getFriend_id());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 친구 목록 조회 메서드
    public List<FriendDTO> getFriends(String userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 친구 목록 리스트 생성
        List<FriendDTO> friends = new ArrayList<>();

        try {
            conn = JDBCConnect.getConnection();
            String selectSql = "SELECT friend_id FROM Friends WHERE user_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                String friend_id = rs.getString("friend_id");
                friends.add(new FriendDTO(friend_id));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return friends;
    }
    
    // 친구가 이미 추가된 상태인지 확인하는 메서드
    public boolean isAlreadyFriend(String userId, String friendId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCConnect.getConnection();
            String checkSql = "SELECT COUNT(*) FROM Friends WHERE user_id = ? AND friend_id = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, userId);
            ps.setString(2, friendId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 해당 아이디가 존재하는지 확인하는 메서드
    public boolean friendExists(String friendId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCConnect.getConnection();
            String checkSql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, friendId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean removeFriend(String userId, String friendId) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = JDBCConnect.getConnection();
            String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	JDBCConnect.close(pstmt, conn);
        }
        
        return result;
    }
    
    
}