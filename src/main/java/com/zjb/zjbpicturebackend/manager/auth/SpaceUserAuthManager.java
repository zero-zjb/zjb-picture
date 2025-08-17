package com.zjb.zjbpicturebackend.manager.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zjb.zjbpicturebackend.domain.entity.Space;
import com.zjb.zjbpicturebackend.domain.entity.SpaceUser;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.enums.SpaceRoleEnum;
import com.zjb.zjbpicturebackend.domain.enums.SpaceTypeEnum;
import com.zjb.zjbpicturebackend.exception.ThrowUtils;
import com.zjb.zjbpicturebackend.manager.auth.domain.SpaceUserAuthConfig;
import com.zjb.zjbpicturebackend.manager.auth.domain.SpaceUserPermissionConstant;
import com.zjb.zjbpicturebackend.manager.auth.domain.SpaceUserRole;
import com.zjb.zjbpicturebackend.service.ISpaceUserService;
import com.zjb.zjbpicturebackend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 空间成员权限管理
 */
@Component
@RequiredArgsConstructor
public class SpaceUserAuthManager {

    private static final SpaceUserAuthConfig spaceUserAuthConfig;

    private final IUserService userService;

    private final ISpaceUserService spaceUserService;

    static {
        String str = ResourceUtil.readUtf8Str("biz/spaceUserAuthConfig.json");
        spaceUserAuthConfig = JSONUtil.toBean(str, SpaceUserAuthConfig.class);
    }

    //根据角色获取权限列表
    public List<String> getPermissionsByRole(String SpaceUserRole) {
        if(StrUtil.isBlank(SpaceUserRole)){
            return new ArrayList<>();
        }
        SpaceUserRole spaceUserRole = spaceUserAuthConfig.getRoles().stream()
                .filter(r -> r.getKey().equals(SpaceUserRole))
                .findFirst()
                .orElse(null);
        if(spaceUserRole == null){
            return new ArrayList<>();
        }
        return spaceUserRole.getPermissions();
    }

    public List<String> getPermissionList(Space space, User loginUser) {
        if (loginUser == null) {
            return new ArrayList<>();
        }
        // 管理员权限
        List<String> ADMIN_PERMISSIONS = getPermissionsByRole(SpaceRoleEnum.ADMIN.getValue());
        // 公共图库
        if (space == null) {
            if (userService.isAdmin(loginUser)) {
                return ADMIN_PERMISSIONS;
            }
            return Collections.singletonList(SpaceUserPermissionConstant.PICTURE_VIEW);
        }
        SpaceTypeEnum spaceTypeEnum = SpaceTypeEnum.getEnumByValue(space.getSpaceType());
        if (spaceTypeEnum == null) {
            return new ArrayList<>();
        }
        // 根据空间获取对应的权限
        switch (spaceTypeEnum) {
            case PRIVATE:
                // 私有空间，仅本人或管理员有所有权限
                if (space.getUserId().equals(loginUser.getId()) || userService.isAdmin(loginUser)) {
                    return ADMIN_PERMISSIONS;
                } else {
                    return new ArrayList<>();
                }
            case TEAM:
                // 团队空间，查询 SpaceUser 并获取角色和权限
                SpaceUser spaceUser = spaceUserService.lambdaQuery()
                        .eq(SpaceUser::getSpaceId, space.getId())
                        .eq(SpaceUser::getUserId, loginUser.getId())
                        .one();
                if (spaceUser == null) {
                    return new ArrayList<>();
                } else {
                    return getPermissionsByRole(spaceUser.getSpaceRole());
                }
        }
        return new ArrayList<>();
    }
}
