package lee.wedding.api.mapper;

import lee.wedding.api.bo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface UserMapper {

    List<User> selectUserAll();
}
