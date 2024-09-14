package lee.wedding.manage.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lee.wedding.api.service.UserService;
import lee.wedding.common.utils.LeeWebbingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ClassName UserController
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/5 14:18
 **/
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "int", paramType = "query", value = "页数", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", value = "条数", required = false, defaultValue = "10"),
            @ApiImplicitParam(name = "userName", dataType = "String", paramType = "query", value = "登录名", required = false, defaultValue = ""),
            @ApiImplicitParam(name = "fullName", dataType = "String", paramType = "query", value = "用户姓名", required = false, defaultValue = ""),
            @ApiImplicitParam(name = "userStatus", dataType = "int", paramType = "query", value = "用户状态", required = false, defaultValue = "")

    })
    public LeeWebbingResult getUsers(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "userStatus", required = false) Integer userStatus)
    {
        return LeeWebbingResult.success(userService.getAllUsers());
//        return "";
    }
}
