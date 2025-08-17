package com.zjb.zjbpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjb.zjbpicturebackend.domain.dto.spaceuser.SpaceUserAddRequest;
import com.zjb.zjbpicturebackend.domain.dto.spaceuser.SpaceUserQueryRequest;
import com.zjb.zjbpicturebackend.domain.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.vo.SpaceUserVO;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 空间用户关联 服务类
 * </p>
 *
 * @author zjb
 * @since 2025-08-10
 */
public interface ISpaceUserService extends IService<SpaceUser> {

    /**
     * 校验空间成员参数
     *
     * @param spaceUser 空间成员对象
     * @param add   是否为创建
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取查询条件
     *
     * @param spaceUserQueryRequest 查询条件
     * @return 查询条件
     */
    public QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 获取封装类
     * 获取单个空间成员封装
     *
     * @param spaceUser
     * @param request
     * @return SpaceUserVO
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 创建空间成员
     *
     * @param spaceUserAddRequest
     * @return
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 获取空间成员列表
     *
     * @param spaceUserList
     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
