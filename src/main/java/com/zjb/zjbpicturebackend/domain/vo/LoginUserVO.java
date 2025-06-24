package com.zjb.zjbpicturebackend.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户登录返回对象(脱敏)
 * </p>
 *
 * @author zjb
 * @since 2025-06-21
 */
@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = -3058981896178125465L;
    /**
     * 用户id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 个性签名
     */
    private String userProfile;

    /**
     * 角色
     */
    private String userRole;

    /**
     * 编辑时间
     */
    private LocalDateTime editTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
