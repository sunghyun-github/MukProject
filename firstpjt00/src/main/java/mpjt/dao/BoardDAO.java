package mpjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import mpjt.dto.BoardDTO;
import mpjt.dto.FriendDTO;
import mpjt.common.JDBCConnect;

public class BoardDAO {
   
   // 제목 내용 글 검색
   public List<BoardDTO> selectList(Map<String, String> map){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;            

      // search 여부
      boolean isSearch = false;
      if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
         isSearch = true;
      }   

      List<BoardDTO> bbs = new ArrayList<BoardDTO>();
      String sql = "select fr_idx, user_id,fr_title, fr_cont, fr_visitnum, fr_like, fr_ofile,fr_sfile, fr_regd from free_board ";
      if(isSearch) {
         sql += " where " + map.get("searchField") + " like ? ";
      }
      sql += " order by fr_idx desc ";
      sql += " limit ? offset ?"; // 2page
      
      conn = JDBCConnect.getConnection();

      try {
         pstmt = conn.prepareStatement(sql);
         if(isSearch) {
            pstmt.setString(1, "%" + map.get("searchWord") + "%");
            pstmt.setInt(2, Integer.parseInt(map.get("amount")));
            pstmt.setInt(3, Integer.parseInt(map.get("offset")));
         }else {
            pstmt.setInt(1, Integer.parseInt(map.get("amount")));
            pstmt.setInt(2, Integer.parseInt(map.get("offset")));
         }
         
         rs = pstmt.executeQuery();

         while(rs.next()) {
            int num = rs.getInt("fr_idx");
            String id = rs.getString("user_id");
            String title = rs.getString("fr_title");
            String cont = rs.getString("fr_cont");
            int visitcount = rs.getInt("fr_visitnum");
            int like = rs.getInt("fr_like");
            String regd = rs.getString("fr_regd");
            String ofile = rs.getString("fr_ofile");
            String sfile = rs.getString("fr_sfile");
            
            BoardDTO dto = new BoardDTO(num, id, title, cont, visitcount, like, regd);
            dto.setFr_ofile(ofile);
            dto.setFr_sfile(sfile);
            bbs.add(dto);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }
      return bbs;
   }
   
   // 전체 글 갯수 출력
   public int selectCount(Map<String, String> map){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;            

      int totalCount = 0;

      // search 여부
      boolean isSearch = false;
      if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
         isSearch = true;
      }      

      String sql = "select count(fr_idx) as cnt from free_board ";
      if(isSearch) {
         //sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
         sql += " where " + map.get("searchField") + " like ? ";
      }
      conn = JDBCConnect.getConnection();

      try {
         pstmt = conn.prepareStatement(sql);
         if(isSearch) {
            //pstmt.setString(1, map.get("searchWord"));
            pstmt.setString(1, "%" + map.get("searchWord") + "%");
         }
         rs = pstmt.executeQuery();

         if(rs.next()) {
            totalCount = rs.getInt("cnt");
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }      

      return totalCount;
   }
   
   // 게시판 글쓰기
   public int insertWrite(BoardDTO dto) {
      
      Connection conn = null;
       PreparedStatement pstmt = null;  
       int rs = 0;
       
      // search 여부
      boolean isFile = false;
      if(dto.getFr_ofile() != null && dto.getFr_sfile() != null) {
         isFile = true;
      }
       
       try {
          // 2. conn
          conn = JDBCConnect.getConnection();
          
          // 3. sql + 쿼리창
          String sql = "insert into free_board(fr_title, fr_cont, user_id) values(?,?,?)";
          
          if(isFile) {
              sql = "insert into free_board(fr_title, fr_cont, user_id, fr_ofile, fr_sfile) values(?,?,?,?,?)";
         }
          
          pstmt = conn.prepareStatement(sql);
          
         if(isFile) {
             pstmt.setString(1, dto.getFr_title());
              pstmt.setString(2, dto.getFr_cont());
              pstmt.setString(3, dto.getUser_id());
              pstmt.setString(4, dto.getFr_ofile());
              pstmt.setString(5, dto.getFr_sfile());
         }else {
             pstmt.setString(1, dto.getFr_title());
              pstmt.setString(2, dto.getFr_cont());
              pstmt.setString(3, dto.getUser_id());
             
         }
          
          // 4. ? 세팅
         
          
          // 5. execute 실행
          rs = pstmt.executeUpdate();
          
       } catch (Exception e) {
          e.printStackTrace();
       }finally {
          JDBCConnect.close(pstmt, conn);
       }
       return rs;
   }
   
   // 게시글 보기
   public BoardDTO selectView(BoardDTO dto){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;            

      String sql = "select fr_idx, fr_title, fr_cont,A.user_id, fr_regd, fr_visitnum, fr_like, fr_ofile, fr_sfile";
      sql += " from free_board A, user B ";
      sql += " where fr_idx = ? and A.user_id = B.user_id";
      conn = JDBCConnect.getConnection();

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, dto.getFr_idx());
         rs = pstmt.executeQuery();
         
         dto = null;
         if(rs.next()) {
            int num = rs.getInt("fr_idx");
            String title = rs.getString("fr_title");
            String content = rs.getString("fr_cont");
            String id = rs.getString("user_id");
            String postdate = rs.getString("fr_regd");
            int visitcount = rs.getInt("fr_visitnum");
            int like = rs.getInt("fr_like");
            String ofile = rs.getString("fr_ofile");
            String sfile = rs.getString("fr_sfile");
            dto = new BoardDTO(num, title, content, id, postdate, visitcount, like, ofile, sfile);      
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }
      
      return dto;
   }
   
   // 최근 게시물 3개 가져오기
   public List<BoardDTO> getRecentPosts() {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       List<BoardDTO> postList = new ArrayList<>();
       
       String sql = "SELECT fr_idx, fr_title, fr_cont, A.user_id, fr_regd, fr_visitnum, fr_like, fr_ofile, fr_sfile " +
                    "FROM free_board A, user B " +
                    "WHERE A.user_id = B.user_id " +
                    "ORDER BY fr_regd DESC " +
                    "LIMIT 3";
       conn = JDBCConnect.getConnection();

       try {
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           
           while (rs.next()) {
               int num = rs.getInt("fr_idx");
               String title = rs.getString("fr_title");
               String content = rs.getString("fr_cont");
               String id = rs.getString("user_id");
               String postdate = rs.getString("fr_regd");
               int visitcount = rs.getInt("fr_visitnum");
               int like = rs.getInt("fr_like");
               String ofile = rs.getString("fr_ofile");
               String sfile = rs.getString("fr_sfile");
               
               BoardDTO dto = new BoardDTO(num, title, content, id, postdate, visitcount, like, ofile, sfile);
               postList.add(dto);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           JDBCConnect.close(rs, pstmt, conn);
       }
       
       return postList;
   }


   // 조회수
   public int updateVisitcount(BoardDTO dto) {
      Connection conn = null;
       PreparedStatement pstmt = null;  
       int rs = 0;
       try {
          // 2. conn
          conn = JDBCConnect.getConnection();
          
          // 3. sql + 쿼리창
          String sql = "update free_board set fr_visitnum = fr_visitnum + 1 ";
          sql += " where fr_idx = ?";
          pstmt = conn.prepareStatement(sql);
          
          // 4. ? 세팅
          pstmt.setInt(1, dto.getFr_idx());
          
          // 5. execute 실행
          rs = pstmt.executeUpdate();
          
       } catch (Exception e) {
          e.printStackTrace();
       }finally {
          JDBCConnect.close(pstmt, conn);
       }
       return rs;
   }
   
   // 게시글 업데이트
   public int updateWrite(BoardDTO dto) {
      Connection conn = null;
       PreparedStatement pstmt = null;  
       int rs = 0;
       try {
          // 2. conn
          conn = JDBCConnect.getConnection();
          
          // 3. sql + 쿼리창
          String sql = "update free_board set fr_title = ?, fr_cont = ? ";
          sql += " where fr_idx = ?";
          pstmt = conn.prepareStatement(sql);
          
          // 4. ? 세팅
          pstmt.setString(1, dto.getFr_title());
          pstmt.setString(2, dto.getFr_cont());
          pstmt.setInt(3, dto.getFr_idx());
          
          // 5. execute 실행
          rs = pstmt.executeUpdate();
          
       } catch (Exception e) {
          e.printStackTrace();
       }finally {
          JDBCConnect.close(pstmt, conn);
       }
       return rs;
   }
   
   // 게시글 삭제
   public int deleteWrite(BoardDTO dto) {
      Connection conn = null;
       PreparedStatement pstmt = null;  
       int rs = 0;
       try {
          // 2. conn
          conn = JDBCConnect.getConnection();
          
          // 3. sql + 쿼리창
          String sql = "delete from free_board ";
          sql += " where fr_idx = ?";
          pstmt = conn.prepareStatement(sql);
          
          // 4. ? 세팅
          pstmt.setInt(1, dto.getFr_idx());
          
          // 5. execute 실행
          rs = pstmt.executeUpdate();
          
       } catch (Exception e) {
          e.printStackTrace();
       }finally {
          JDBCConnect.close(pstmt, conn);
       }
       return rs;
   }
   
   // 좋아요
   // 사용자가 해당 게시물에 이미 좋아요를 눌렀는지 확인하는 메서드
   public boolean hasLiked(String userId, int boardIdx) {
       String query = "SELECT COUNT(*) FROM likes WHERE user_id = ? AND fr_idx = ?";
       try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
           pstmt.setString(1, userId);
           pstmt.setInt(2, boardIdx);
           try (ResultSet rs = pstmt.executeQuery()) {
               if (rs.next()) {
                   return rs.getInt(1) > 0;
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }

   // 좋아요 수 조회
   public int getLikes(int idx) {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       int likes = 0;

       try {
           conn = JDBCConnect.getConnection();
           String sql = "SELECT fr_like FROM free_board WHERE fr_idx = ?";
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, idx);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               likes = rs.getInt("fr_like");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           JDBCConnect.close(rs, pstmt, conn);
       }
       return likes;
   }      

   // 좋아요 체크
   public boolean checkLikeStatus(int idx, String userId) {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       boolean userLiked = false;

       try {
           conn = JDBCConnect.getConnection();

           // 사용자가 이미 좋아요를 눌렀는지 확인
           String checkLikeSql = "SELECT * FROM likes WHERE fr_idx = ? AND user_id = ?";
           pstmt = conn.prepareStatement(checkLikeSql);
           pstmt.setInt(1, idx);
           pstmt.setString(2, userId);
           rs = pstmt.executeQuery();

           // 결과가 존재하면 사용자가 이미 좋아요를 눌렀음을 나타냄
           if (rs.next()) {
               userLiked = true;
           }

       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           JDBCConnect.close(rs, pstmt, conn);
       }
       return userLiked;
   }
// 좋아요 처리
public boolean toggleLike(int idx, String userId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean userLiked = false;

    try {
        conn = JDBCConnect.getConnection();

        // 사용자가 이미 좋아요를 눌렀는지 확인
        String checkLikeSql = "SELECT * FROM likes WHERE fr_idx = ? AND user_id = ?";
        pstmt = conn.prepareStatement(checkLikeSql);
        pstmt.setInt(1, idx);
        pstmt.setString(2, userId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            // 이미 좋아요를 누른 상태라면, 좋아요 취소
            String deleteLikeSql = "DELETE FROM likes WHERE fr_idx = ? AND user_id = ?";
            pstmt = conn.prepareStatement(deleteLikeSql);
            pstmt.setInt(1, idx);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

            // free_board 테이블의 좋아요 수 감소
            String decreaseLikeSql = "UPDATE free_board SET fr_like = fr_like - 1 WHERE fr_idx = ?";
            pstmt = conn.prepareStatement(decreaseLikeSql);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

            userLiked = false;
        } else {
            // 좋아요를 누르지 않은 상태라면, 좋아요 추가
            String insertLikeSql = "INSERT INTO likes (fr_idx, user_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertLikeSql);
            pstmt.setInt(1, idx);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

            // free_board 테이블의 좋아요 수 증가
            String increaseLikeSql = "UPDATE free_board SET fr_like = fr_like + 1 WHERE fr_idx = ?";
            pstmt = conn.prepareStatement(increaseLikeSql);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

            userLiked = true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        JDBCConnect.close(rs, pstmt, conn);
    }
    return userLiked;
}

   
}