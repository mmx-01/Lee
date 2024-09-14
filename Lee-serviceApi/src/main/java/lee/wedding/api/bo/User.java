package lee.wedding.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ ClassName User
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/5 11:37
 **/
@Data
public class User {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("登录名")
    private String userName;

    @ApiModelProperty("密码")
    private String userPassword;

    @ApiModelProperty("用户姓名")
    private String fullName;

    @ApiModelProperty("邮箱")
    private String userEmail;

    @ApiModelProperty("手机号")
    private Integer userPhoneCode;
    
    @ApiModelProperty("身份证号")
    private String userCard;
    
    @ApiModelProperty("是否删除；0：未删除；1：删除")
    private Integer isDelete;
    
    @ApiModelProperty("用户状态；0：正常；1：过期；2：冻结；3：黑名单")
    private Integer userStatus;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;




}
