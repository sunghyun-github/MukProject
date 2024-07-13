package mpjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mpjt.dto.CommentDTO;
import mpjt.common.JDBCConnect;

public class CommentDAO {

    // 댓글 목록 가져오기
   public List<CommentDTO> getComment(CommentDTO cdto, String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDTO> commentList = new ArrayList<>();
       

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT frc_idx, fr_idx, user_id, frc_cont, frc_like, frc_regd " +
                         "FROM free_board_comment WHERE fr_idx = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cdto.getFr_idx());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CommentDTO cto = new CommentDTO();
                cto.setFrc_idx(rs.getInt("frc_idx"));
                cto.setFr_idx(rs.getInt("fr_idx"));
                cto.setUser_id(rs.getString("user_id"));
                cto.setFrc_cont(rs.getString("frc_cont"));
                cto.setFrc_like(rs.getInt("frc_like"));
                cto.setFrc_regd(rs.getString("frc_regd"));

                // 좋아요 상태 설정
                commentList.add(cto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }
        return commentList;
    }

    // 댓글 달기
    public List<CommentDTO> insertWrite(CommentDTO cdto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDTO> cbs = new ArrayList<CommentDTO>();

        try {
            conn = JDBCConnect.getConnection();
            String sql = "INSERT INTO free_board_comment (fr_idx, user_id, frc_cont) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cdto.getFr_idx());
            pstmt.setString(2, cdto.getUser_id());
            pstmt.setString(3, cdto.getFrc_cont());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("댓글 추가 실패: 행이 영향을 받지 않음");
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int frc_idx = rs.getInt(1);
                CommentDTO cto = new CommentDTO(frc_idx, cdto.getFr_idx(), cdto.getUser_id(), cdto.getFrc_cont(), 0, null);
                cbs.add(cto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }
        return cbs;
    }

    // 댓글 삭제
    public int deleteComment(CommentDTO cdto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rs = 0;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "DELETE FROM free_board_comment WHERE frc_idx = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cdto.getFrc_idx());
            rs = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }
        return rs;
    }

// // 댓글 좋아요 토글 기능을 추가하여 댓글의 좋아요 상태를 변경하는 메서드 수정
//    public boolean toggleLike(int frc_idx, String user_id) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        boolean liked = false;
//
//        try {
//            // 1. Connection
//            conn = JDBCConnect.getConnection();
//            conn.setAutoCommit(false); // 트랜잭션 시작
//
//            // 댓글 좋아요 여부 확인
//            String query = "SELECT COUNT(*) FROM comment_like WHERE frc_idx = ? AND user_id = ?";
//            pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, frc_idx);
//            pstmt.setString(2, user_id);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                if (count > 0) {
//                    // 이미 좋아요를 누른 상태이면 좋아요 취소
//                    query = "DELETE FROM comment_like WHERE frc_idx = ? AND user_id = ?";
//                    pstmt = conn.prepareStatement(query);
//                    pstmt.setInt(1, frc_idx);
//                    pstmt.setString(2, user_id);
//                    int deletedRows = pstmt.executeUpdate();
//
//                    if (deletedRows > 0) {
//                        // 댓글 테이블의 좋아요 수 감소
//                        query = "UPDATE free_board_comment SET frc_like = frc_like - 1 WHERE frc_idx = ?";
//                        pstmt = conn.prepareStatement(query);
//                        pstmt.setInt(1, frc_idx);
//                        pstmt.executeUpdate();
//
//                        liked = false;
//                    }
//                } else {
//                    // 좋아요를 누르지 않은 상태이면 좋아요 추가
//                    query = "INSERT INTO comment_like (frc_idx, user_id) VALUES (?, ?)";
//                    pstmt = conn.prepareStatement(query);
//                    pstmt.setInt(1, frc_idx);
//                    pstmt.setString(2, user_id);
//                    int insertedRows = pstmt.executeUpdate();
//
//                    if (insertedRows > 0) {
//                        // 댓글 테이블의 좋아요 수 증가
//                        query = "UPDATE free_board_comment SET frc_like = frc_like + 1 WHERE frc_idx = ?";
//                        pstmt = conn.prepareStatement(query);
//                        pstmt.setInt(1, frc_idx);
//                        pstmt.executeUpdate();
//
//                        liked = true;
//                    }
//                }
//            }
//
//            conn.commit(); // 트랜잭션 커밋
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                if (conn != null) {
//                    conn.rollback(); // 롤백
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            // 자원 해제
//            JDBCConnect.close(rs, pstmt, conn);
//        }
//
//        return liked;
//    }
//
//
// // 좋아요 갯수 계산
//    public int getLikesCount(int frc_idx) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        int likesCount = 0;
//
//        try {
//            conn = JDBCConnect.getConnection();
//            String query = "SELECT COUNT(*) AS like_count FROM comment_like WHERE frc_idx = ?";
//            pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, frc_idx);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                likesCount = rs.getInt("like_count");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JDBCConnect.close(rs, pstmt, conn);
//        }
//
//        return likesCount;
//    }
 // 좋아요 토글 메서드
    public boolean toggleLike(int frc_idx, String user_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean liked = false;

        try {
            conn = JDBCConnect.getConnection();

            // 댓글 좋아요 여부 확인
            String query = "SELECT COUNT(*) FROM comment_like WHERE frc_idx = ? AND user_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, frc_idx);
            pstmt.setString(2, user_id);
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // 이미 좋아요를 누른 상태이면 좋아요 취소
                query = "DELETE FROM comment_like WHERE frc_idx = ? AND user_id = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, frc_idx);
                pstmt.setString(2, user_id);
                pstmt.executeUpdate();
                liked = false;
            } else {
                // 좋아요를 누르지 않은 상태이면 좋아요 추가
                query = "INSERT INTO comment_like (frc_idx, user_id) VALUES (?, ?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, frc_idx);
                pstmt.setString(2, user_id);
                pstmt.executeUpdate();
                liked = true;
            }

            // 좋아요 수 업데이트
            query = "UPDATE free_board_comment SET frc_like = (SELECT COUNT(*) FROM comment_like WHERE frc_idx = ?) WHERE frc_idx = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, frc_idx);
            pstmt.setInt(2, frc_idx);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return liked;
    }

    // 댓글의 좋아요 수 가져오기
    public int getCommentLikeCount(int frc_idx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int likeCount = 0;

        try {
            conn = JDBCConnect.getConnection();
            String query = "SELECT frc_like FROM free_board_comment WHERE frc_idx = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, frc_idx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                likeCount = rs.getInt("frc_like");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return likeCount;
    }

    public boolean checkUserLiked(int frc_idx, String user_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCConnect.getConnection();
            String query = "SELECT COUNT(*) FROM comment_like WHERE frc_idx = ? AND user_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, frc_idx);
            pstmt.setString(2, user_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return false;
    }

    public int getLikesCount(int frc_idx) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCConnect.getConnection();
            String query = "SELECT COUNT(*) FROM comment_like WHERE frc_idx = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, frc_idx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return 0;
    }


}