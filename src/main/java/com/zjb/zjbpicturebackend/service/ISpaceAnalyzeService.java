package com.zjb.zjbpicturebackend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zjb.zjbpicturebackend.domain.dto.space.analyze.*;
import com.zjb.zjbpicturebackend.domain.entity.Space;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.vo.space.analyze.*;

import java.util.List;


/**
 * <p>
 * 空间分析 服务类
 * </p>
 *
 * @author zjb
 * @since 2025-07-13
 */
public interface ISpaceAnalyzeService extends IService<Space> {


    /**
     * 获取空间使用分析数据
     *
     * @param spaceUsageAnalyzeRequest SpaceUsageAnalyzeRequest 请求参数
     * @param loginUser                当前登录用户
     * @return SpaceUsageAnalyzeResponse 分析结果
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    /**
     * 获取空间分类分析数据
     *
     * @param spaceCategoryAnalyzeRequest SpaceCategoryAnalyzeRequest 请求参数
     * @param loginUser                   当前登录用户
     * @return List<SpaceCategoryAnalyzeResponse> 分析结果
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    /**
     * 获取空间标签分析数据
     *
     * @param spaceTagAnalyzeRequest SpaceTagAnalyzeRequest 请求参数
     * @param loginUser              当前登录用户
     * @return List<SpaceTagAnalyzeResponse> 分析结果
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    /**
     * 获取空间大小分析数据
     *
     * @param spaceSizeAnalyzeRequest SpaceSizeAnalyzeRequest 请求参数
     * @param loginUser               当前登录用户
     * @return List<SpaceSizeAnalyzeResponse> 分析结果
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    /**
     * 获取空间用户分析数据
     *
     * @param spaceUserAnalyzeRequest SpaceUserAnalyzeRequest 请求参数
     * @param loginUser               当前登录用户
     * @return List<SpaceUserAnalyzeResponse> 分析结果
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    /**
     * 获取空间排行分析数据
     *
     * @param spaceRankAnalyzeRequest SpaceRankAnalyzeRequest 请求参数
     * @param loginUser               当前登录用户
     * @return List<Space> 排行数据
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
