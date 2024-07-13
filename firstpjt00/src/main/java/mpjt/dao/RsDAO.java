package mpjt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mpjt.dto.BoardDTO;
import mpjt.dto.RsDTO;
import mpjt.common.JDBCConnect;

public class RsDAO {
   public List<RsDTO> rsList(){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;            

      List<RsDTO> rsList = new ArrayList<>();
      
      try {
         // 2. connection
           conn = JDBCConnect.getConnection();
           
           // 3. sql창
           String sql = "select rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img from restaurant;";
         pstmt = conn.prepareStatement(sql);
         
         // 4. execute
            rs = pstmt.executeQuery();
         
         while(rs.next()) {
            int rs_idx = rs.getInt("rs_idx");
            String rs_name = rs.getString("rs_name");
            String rs_addr = rs.getString("rs_addr");
            String rs_num = rs.getString("rs_num");
            String rs_hour = rs.getString("rs_hour");
            String rs_menu = rs.getString("rs_menu");
            String rs_price = rs.getString("rs_price");
            String rs_type = rs.getString("rs_type");
            String rs_rating = rs.getString("rs_rating");
            String rs_comment = rs.getString("rs_comment");
            String rs_img = rs.getString("rs_img");
            RsDTO dto = new RsDTO(rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img);
            rsList.add(dto);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }

      return rsList;
   }
   
   public int addRestaurant(RsDTO dto) {
      Connection conn = null;
       PreparedStatement pstmt = null;  
       int rs = 0;
       
       try {
             // 2. conn
             conn = JDBCConnect.getConnection();
             
             // 3. sql + 쿼리창
             String sql = "insert into restaurant ( rs_name, rs_addr, rs_num, rs_hour, rs_type) values(?,?,?,?,?)";
             pstmt = conn.prepareStatement(sql);
             
             // 4. ? 세팅
             pstmt.setString(1, dto.getRs_name());
             pstmt.setString(2, dto.getRs_addr());
             pstmt.setString(3, dto.getRs_num());
             pstmt.setString(4, dto.getRs_hour());
             pstmt.setString(5, dto.getRs_type());
             
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
   public RsDTO selectView(RsDTO dto){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;            


      String sql = "select rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img";
      sql += " from restaurant ";
      sql += " where rs_idx = ?";
      conn = JDBCConnect.getConnection();

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, dto.getRs_idx());
         rs = pstmt.executeQuery();
         
         dto = null;
         if(rs.next()) {
            int idx = rs.getInt("rs_idx");
            String rs_name = rs.getString("rs_name");
         String rs_addr = rs.getString("rs_addr");
         String rs_num = rs.getString("rs_num");
         String rs_hour = rs.getString("rs_hour");
         String rs_menu = rs.getString("rs_menu");
         String rs_price = rs.getString("rs_price");
         String rs_type = rs.getString("rs_type");
         String rs_rating = rs.getString("rs_rating");
         String rs_comment = rs.getString("rs_comment");
         String rs_img = rs.getString("rs_img");
            dto = new RsDTO(idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img);            
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }
      
      return dto;
   }

   public boolean updateRating(String rs_idx, int rating) {
       Connection conn = null;
       PreparedStatement pstmt = null;
       String sql = "UPDATE restaurant SET rs_rating = ? WHERE rs_idx = ?";
       
       try {
          conn = JDBCConnect.getConnection();
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, rating);
           pstmt.setString(2, rs_idx);
           
           int result = pstmt.executeUpdate();
           
           return result > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       } finally {
         JDBCConnect.close(pstmt, conn);;
       }
   }

   public int deleteRestaurant(int rs_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = JDBCConnect.getConnection();
			String sql = "DELETE FROM restaurant WHERE rs_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rs_idx);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
		}

		return result;
	}
   
   public List<RsDTO> rsListByCategory(String category) throws ClassNotFoundException {
	      List<RsDTO> rsList = new ArrayList<>();
	      String sql = "SELECT rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img FROM restaurant WHERE rs_type = ?";

	      try (Connection conn = JDBCConnect.getConnection();
	           PreparedStatement pstmt = conn.prepareStatement(sql)) {

	         pstmt.setString(1, category);
	         try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	               RsDTO dto = new RsDTO(
	                  rs.getInt("rs_idx"),
	                  rs.getString("rs_name"),
	                  rs.getString("rs_addr"),
	                  rs.getString("rs_num"),
	                  rs.getString("rs_hour"),
	                  rs.getString("rs_menu"),
	                  rs.getString("rs_price"),
	                  rs.getString("rs_type"),
	                  rs.getString("rs_rating"),
	                  rs.getString("rs_comment"),
	                  rs.getString("rs_img")
	               );
	               rsList.add(dto);
	            }
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return rsList;
	   }
   
// 별점 상위 맛집 3개 가져오기
   public List<RsDTO> getTopRs() {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       List<RsDTO> getTopRs = new ArrayList<>();

       try {
    	   	conn = JDBCConnect.getConnection();

    	   	String sql = "SELECT rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img " +
	                    "FROM restaurant " +
	                    "ORDER BY rs_rating DESC " +
	                    "LIMIT 3";

           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           
           while (rs.next()) {
            int rs_idx = rs.getInt("rs_idx");
            String rs_name = rs.getString("rs_name");
            String rs_addr = rs.getString("rs_addr");
            String rs_num = rs.getString("rs_num");
            String rs_hour = rs.getString("rs_hour");
            String rs_menu = rs.getString("rs_menu");
            String rs_price = rs.getString("rs_price");
            String rs_type = rs.getString("rs_type");
            String rs_rating = rs.getString("rs_rating");
            String rs_comment = rs.getString("rs_comment");
            String rs_img = rs.getString("rs_img");
               
           RsDTO dto = new RsDTO(rs_idx, rs_name, rs_addr, rs_num, rs_hour, rs_menu, rs_price, rs_type, rs_rating, rs_comment, rs_img);
           getTopRs.add(dto);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           JDBCConnect.close(rs, pstmt, conn);
       }
       
       return getTopRs;
   }
   
   // 맛집 ID검색
   public List<RsDTO> rsListByUserId(String userId) {
       List<RsDTO> list = new ArrayList<RsDTO>();
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       try {
    	   conn = JDBCConnect.getConnection();
           String sql = "SELECT * FROM restaurant WHERE rs_name LIKE ?";
           pstmt = conn.prepareStatement(sql);
           // sql의 ? 부분(1)에 앞뒤로 % 해당값이 포함된 것을 찾아줌
           pstmt.setString(1, "%" + userId + "%");
           rs = pstmt.executeQuery();

           while (rs.next()) {
               RsDTO dto = new RsDTO();
               dto.setRs_idx(rs.getInt("rs_idx"));
               dto.setRs_name(rs.getString("rs_name"));
               dto.setRs_type(rs.getString("rs_type"));
               dto.setRs_addr(rs.getString("rs_addr"));
               dto.setRs_num(rs.getString("rs_num"));
               dto.setRs_rating(String.valueOf(rs.getInt("rs_rating")));
               list.add(dto);
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try { if (rs != null) rs.close(); } catch (Exception e) { }
           try { if (pstmt != null) pstmt.close(); } catch (Exception e) { }
           try { if (conn != null) conn.close(); } catch (Exception e) { }
       }
       return list;
   }
   
   

   
   
}

