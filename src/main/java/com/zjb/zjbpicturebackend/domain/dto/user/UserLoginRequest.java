package com.zjb.zjbpicturebackend.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 */
@Data
@ApiModel(value="用户登录对象", description="用户登录")
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 7941386540987153461L;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    private String userPassword;
}
