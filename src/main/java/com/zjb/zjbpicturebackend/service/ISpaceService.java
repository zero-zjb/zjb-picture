package com.zjb.zjbpicturebackend.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceAddRequest;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceQueryRequest;
import com.zjb.zjbpicturebackend.domain.entity.Space;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.vo.PictureVO;
import com.zjb.zjbpicturebackend.domain.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 空间 服务类
 * </p>
 *
 * @author zjb
 * @since 2025-07-13
 */
public interface ISpaceService extends IService<Space> {

    /**
     * 校验空间参数
     *
     * @param space 空间对象
     * @param add   是否为创建
     */
    void validSpace(Space space, boolean add);

    /**
     * 填充空间信息
     *
     * @param space 空间对象
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 获取查询条件
     *
     * @param spaceQueryRequest 查询条件
     * @return 查询条件
     */
    public QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取封装类
     * 获取单个空间封装
     *
     * @param space
     * @param request
     * @return SpaceVO
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取分页空间封装
     *
     * @param spacePage
     * @param request
     * @return Page<SpaceVO>
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 创建空间
     *
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 获取空间列表（封装类）
     *
     * @param spaceQueryRequest
     * @param request
     * @return
     */
    Page<SpaceVO> listSpaceVOByPage(SpaceQueryRequest spaceQueryRequest,
                                        HttpServletRequest request);
}
