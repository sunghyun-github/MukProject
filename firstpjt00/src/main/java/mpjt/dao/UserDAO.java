package mpjt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

import mpjt.common.JDBCConnect;
import mpjt.dto.UserDTO;

public class UserDAO {
   private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String URL = "jdbc:mysql://localhost:3307/mpjt?serverTimezone=UTC";
   private static final String USER = "root";
   private static final String PASSWORD = "rpass";
   private Connection conn;


   public UserDTO getUser(UserDTO dto) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null; // select , 회원가입은 insert할 것이므로 주석 !

      try {
         // 2. connection
         conn = JDBCConnect.getConnection();

         // 3. sql 창
         String sql = "select user_idx, user_id, user_password, user_name, user_role, user_regd from user where user_id=?";
         pstmt = conn.prepareStatement(sql);
         // 문자니까 setString, 날짜면 setDate 등등 ...
         pstmt.setString(1, dto.getUser_id());
         // 4. execute
         rs = pstmt.executeQuery(); // select

         // 있는지 판단
         dto = null;
         if (rs.next()) { // id 존재
            int idx = rs.getInt("user_idx");
            String id = rs.getString("user_id");
            String password = rs.getString("user_password");
            String name = rs.getString("user_name");
            String role = rs.getString("user_role");
            String regDate = rs.getString("user_regd");
            dto = new UserDTO(idx, id, password, name, regDate, role);
         } 

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         JDBCConnect.close(rs, pstmt, conn);
      }
      return dto;
   }

   public List<UserDTO> getUsers() {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       List<UserDTO> userList = new ArrayList<>();

       try {
           // 2. connection
           conn = JDBCConnect.getConnection();

           // 3. SQL 쿼리문
           String sql = "SELECT user_idx, user_id, user_password, user_name, user_email, user_gen, user_upd, user_regd, user_role FROM user";
           pstmt = conn.prepareStatement(sql);
           
           // 4. execute
           rs = pstmt.executeQuery();
           
           // 5. rs 처리
           while (rs.next()) {
               int idx = rs.getInt(1);
               String id = rs.getString(2);
               String password = rs.getString(3);
               String name = rs.getString(4);
               String email = rs.getString(5);
               String gen = rs.getString(6);
               String update = rs.getString(7);
               String regdate = rs.getString(8);
               String role = rs.getString(9);

               UserDTO dto = new UserDTO(idx, id, password, name, email, gen, update, regdate, role);
               userList.add(dto);
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           JDBCConnect.close(rs, pstmt, conn);
       }

       return userList;
   }



   public String getUserRole(String userId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String userRole = null;

      try {
         // 2. Connection
         conn = JDBCConnect.getConnection();

         // 3. SQL 문 작성
         String sql = "SELECT user_role FROM user WHERE user_id = ?";
         pstmt = conn.prepareStatement(sql);

         // 4. 파라미터 설정
         pstmt.setString(1, userId);

         // 5. 쿼리 실행
         rs = pstmt.executeQuery();

         // 6. 결과 처리
         if (rs.next()) {
            userRole = rs.getString("user_role");
         }
      } catch (Exception e) {
         e.printStackTrace();
         return "-2"; // 데이터베이스 오류
      } finally {
         // 7. 리소스 해제
         JDBCConnect.close(rs, pstmt, conn);
      }

      return userRole;
   }

   public List<UserDTO> getUsers(int listNum, int offset){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;      

      List<UserDTO> userList = new ArrayList<>();

      try{
         // 2. connection
         conn = JDBCConnect.getConnection();         

         // 3. sql창
         String sql = "select user_idx, user_id, user_password, user_name, user_gen, user_upd, user_regd, user_role from user";
         sql += " limit ? offset ?"; // 2page
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, listNum);
         pstmt.setInt(2, offset);
         // 4. execute
         rs = pstmt.executeQuery();
         // 5. rs처리 : id값만 list에 저장
         while(rs.next()) {
            int idx = rs.getInt("user_idx");
            String id = rs.getString("user_id");
            String password = rs.getString("user_password");
            String name = rs.getString("user_name");
            String gen = rs.getString("user_gen");
            String update = rs.getString("user_upd");
            String regdate = rs.getString("user_regd");
            String role = rs.getString("user_role");
            UserDTO dto = new UserDTO(idx, id, password, name, gen, update, regdate, role);
            userList.add(dto);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }finally {
         JDBCConnect.close(rs, pstmt, conn);
      }
      return userList;
   }

   public int login(String user_id, String user_password) { // 내가 sql에 지정한 변수로 써야함 
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;   

      String SQL = "SELECT user_password FROM user WHERE user_id = ?";
      try {
         //connection
         conn = JDBCConnect.getConnection();

         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, user_id);
         rs = pstmt.executeQuery();
         if(rs.next()) {
            if(rs.getString(1).equals(user_password)) 
               return 1; // 로그인 성공 
            else 
               return 0; //비밀번호 불일치  
         }
         return -1; // 아이디가 없음  
      } catch(Exception e) {
         e.printStackTrace();
      }
      return -2; // 데이터베이스 오류 
   }
   public boolean registerUser(String id, String password, String name, String gender, String email, String role) throws Exception {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       String driver = "com.mysql.cj.jdbc.Driver";
       String url = "jdbc:mysql://localhost:3307/mpjt?serverTimezone=UTC";
       String user = "root";
       String pw = "rpass";

       boolean isDuplicate = false;
       boolean isSuccess = false; // 회원가입 성공 여부를 저장할 변수

       try {
           Class.forName(driver);   
           conn = DriverManager.getConnection(url, user, pw);

           String checkSql = "SELECT user_id FROM user WHERE user_id=?";
           pstmt = conn.prepareStatement(checkSql);
           pstmt.setString(1, id);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               isDuplicate = true;
           }

           if (!isDuplicate) {
               String insertSql = "INSERT INTO user(user_id, user_role, user_password, user_name, user_gen, user_email) VALUES (?, ?, ?, ?, ?, ?)";
               pstmt = conn.prepareStatement(insertSql);
               pstmt.setString(1, id);
               pstmt.setString(2, role);
               pstmt.setString(3, password);
               pstmt.setString(4, name);
               pstmt.setString(5, gender);
               pstmt.setString(6, email); // 이메일 추가
               pstmt.executeUpdate();

               // 회원가입이 성공했음을 표시
               isSuccess = true;
           }
       } finally {
           if (rs != null) {
               rs.close();
           }
           if (pstmt != null) {
               pstmt.close();
           }
           if (conn != null) {
               conn.close();
           }
       }

       return isSuccess; // 회원가입 성공 여부 반환
   }


   public int join(UserDTO user) {
      Connection conn = null;
      PreparedStatement pstmt = null;  
      int rs = 0;
      try {
         // 2. conn
         conn = JDBCConnect.getConnection();

         // 3. sql + 쿼리창
         String sql = "insert into user(user_id, user_password, user_name, user_gen, user_role) values(?,?,?,?,?)";
         pstmt = conn.prepareStatement(sql);

         // 4. ? 세팅
         pstmt.setString(1, user.getUser_id());
         pstmt.setString(2, user.getUser_password());
         pstmt.setString(3, user.getUser_name());
         pstmt.setString(4, user.getUser_gen());
         pstmt.setString(5, user.getUser_role());

         // 5. execute 실행
         rs = pstmt.executeUpdate();
         System.out.println("rs>>>>>>>>>>>>>>"+rs);
         System.out.println("회원가입 성공");
         rs = 1;
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         JDBCConnect.close(pstmt, conn);
      }
      System.out.println("회원가입 실패");
      return rs;
   }
   public boolean deleteUser(String userId, String password) {
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASSWORD);

         String sql = "SELECT user_password FROM user WHERE user_id = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         ResultSet rs = pstmt.executeQuery();

         if (rs.next()) {
            if (password.equals(rs.getString("user_password"))) {
               sql = "DELETE FROM user WHERE user_id = ?";
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, userId);
               pstmt.executeUpdate();
               return true;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      return false;
   }
   public boolean update(String userId, String currentPassword, String newPassword, String role, String name, String gender) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASSWORD);

         String sql = "SELECT user_password FROM user WHERE user_id = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         rs = pstmt.executeQuery();

         if (rs.next()) {
            String storedPassword = rs.getString("user_password");
            if (currentPassword.equals(storedPassword)) {
               if (newPassword != null && !newPassword.isEmpty()) {
                  sql = "UPDATE user SET user_role=?, user_password=?, user_name=?, user_gen=? WHERE user_id = ?";
                  pstmt = conn.prepareStatement(sql);
                  pstmt.setString(1, role);
                  pstmt.setString(2, newPassword);
                  pstmt.setString(3, name);
                  pstmt.setString(4, gender);
                  pstmt.setString(5, userId);
                  pstmt.executeUpdate();

                  return true;
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // close resources
      }

      return false;
   }
   public boolean updateUserRole(String userId, String newRole) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 데이터베이스 연결 설정
            conn = JDBCConnect.getConnection();

            // 사용자의 역할을 업데이트하는 SQL 쿼리 작성
            String sql = "UPDATE user SET user_role = ? WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newRole);
            pstmt.setString(2, userId);

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 쿼리가 성공적으로 실행되면 isSuccess를 true로 설정
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스 해제
            JDBCConnect.close(pstmt, conn);
        }

        return isSuccess;
    }
    public List<UserDTO> searchUsersById(String userId) {
           List<UserDTO> users = new ArrayList<>();
           String query = "SELECT * FROM user WHERE user_id LIKE ?";
           
           try (Connection conn = JDBCConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
               ps.setString(1, "%" + userId + "%");
               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       UserDTO user = new UserDTO();
                       user.setUser_idx(rs.getInt("user_idx"));
                       user.setUser_id(rs.getString("user_id"));
                       user.setUser_role(rs.getString("user_role"));
                       user.setUser_password(rs.getString("user_password"));
                       user.setUser_name(rs.getString("user_name"));
                       user.setUser_gen(rs.getString("user_gen"));
                       user.setUser_regd(rs.getString("user_regd"));
                       user.setUser_upd(rs.getString("user_upd"));
                       users.add(user);
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return users;
       }
    public String findUserIdByEmailAndGender(String name, String gender, String email) throws SQLException {
        String userId = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        
        try {
           Connection conn = JDBCConnect.getConnection(); // getConnection 메서드는 데이터베이스 연결을 위한 메서드입니다. 해당 메서드는 생략했습니다.

            // 사용자 입력 값에 해당하는 아이디를 데이터베이스에서 조회합니다.
            String sql = "SELECT user_id FROM user WHERE user_name = ? AND user_gen = ? AND user_email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, email);
            rs = pstmt.executeQuery();

            // 조회된 결과가 있다면 해당 아이디를 userId 변수에 저장합니다.
            if (rs.next()) {
                userId = rs.getString("user_id");
                System.out.println(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
//            if (conn != null) conn.close();
        }

        return userId;
    }
    // 비번
    public String findUserPasswordByEmailAndUserid(String userId, String email) throws SQLException {
        String password = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        
        try {
           Connection conn = JDBCConnect.getConnection(); // getConnection 메서드는 데이터베이스 연결을 위한 메서드입니다. 해당 메서드는 생략했습니다.

            // 사용자 입력 값에 해당하는 아이디를 데이터베이스에서 조회합니다.
            String sql = "SELECT user_password FROM user WHERE user_id = ?  AND user_email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, email);
            rs = pstmt.executeQuery();

            // 조회된 결과가 있다면 해당 아이디를 userId 변수에 저장합니다.
            if (rs.next()) {
               password = rs.getString("user_password");
                System.out.println(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return password;
    }
    
 // 아이디중복체크 메서드
    public UserDTO getUserById(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUser_idx(rs.getInt("user_idx"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_gen(rs.getString("user_gen"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_role(rs.getString("user_role"));
                user.setUser_regd(rs.getString("user_regd"));
                user.setUser_upd(rs.getString("user_upd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return user;
    }

    // 이메일 중복 체크 메서드
    public UserDTO getUserByEmail(String userEmail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM user WHERE user_email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUser_idx(rs.getInt("user_idx"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_gen(rs.getString("user_gen"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_role(rs.getString("user_role"));
                user.setUser_regd(rs.getString("user_regd"));
                user.setUser_upd(rs.getString("user_upd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return user;
    }
 }

    