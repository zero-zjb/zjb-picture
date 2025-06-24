package com.zjb.zjbpicturebackend.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 */
@Data
@ApiModel(value="用户注册对象", description="用户注册")
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -3881201470351501113L;
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

    /**
     * 校验密码
     */
    @ApiModelProperty(value = "校验密码")
    private String checkPassword;
}
