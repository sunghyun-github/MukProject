package mpjt.service.user;

import java.util.List;

import mpjt.dao.UserDAO;

public class UserService {
    
    public boolean updateUserPassword(String userId, String currentPassword, String newPassword, String role, String name, String gender) {
        UserDAO userDao = new UserDAO();
        return userDao.update(userId, currentPassword, newPassword, role, name, gender);
    }
    
}