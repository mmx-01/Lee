package lee.wedding.api.service.impl;

import lee.wedding.api.bo.User;
import lee.wedding.api.mapper.UserMapper;
import lee.wedding.api.service.UserService;
//import lee.wedding.entity.userBO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ ClassName UserServiceImpl
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/5 14:16
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectUserAll();
    }
}
