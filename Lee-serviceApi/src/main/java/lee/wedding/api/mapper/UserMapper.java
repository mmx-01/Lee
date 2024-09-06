package lee.wedding.api.mapper;

//import lee.wedding.entity.userBO.User;
import lee.wedding.entity.userBO.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    List<User> selectUserAll();
}
