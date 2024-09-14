package lee.wedding.api.service;


import lee.wedding.api.bo.User;

import java.util.List;

public interface UserService {

    // 查询用户基本信息
    public List<User> getAllUsers();

}
