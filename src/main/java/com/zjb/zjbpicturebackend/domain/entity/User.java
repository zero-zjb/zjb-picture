package com.zjb.zjbpicturebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 宝哥
 * @since 2025-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "账号")
    @TableField("userAccount")
    private String userAccount;

    @ApiModelProperty(value = "密码")
    @TableField("userPassword")
    private String userPassword;

    @ApiModelProperty(value = "用户昵称")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    @TableField("userAvatar")
    private String userAvatar;

    @ApiModelProperty(value = "用户简介")
    @TableField("userProfile")
    private String userProfile;

    @ApiModelProperty(value = "用户角色：user/admin")
    @TableField("userRole")
    private String userRole;

    @ApiModelProperty(value = "编辑时间")
    @TableField("editTime")
    private LocalDateTime editTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;


}
