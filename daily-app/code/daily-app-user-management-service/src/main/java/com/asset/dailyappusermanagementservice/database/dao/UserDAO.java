package com.asset.dailyappusermanagementservice.database.dao;

import com.asset.dailyappusermanagementservice.models.user.UserModel;
import java.util.List;

public interface UserDAO {
    public int createUser(UserModel user);
    public UserModel retrieveUserById(Integer id);
    public List<UserModel> retrieveUsers();
    public UserModel retrieveUserByUsername(String ntAccount);
    public int updateUser(UserModel user, int id) throws Exception;
    public void deleteUser(int id);
    public void clearUsers();
}
