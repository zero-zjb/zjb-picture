package com.zjb.zjbpicturebackend.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjb.zjbpicturebackend.annotation.AuthCheck;
import com.zjb.zjbpicturebackend.common.BaseResponse;
import com.zjb.zjbpicturebackend.common.DeleteRequest;
import com.zjb.zjbpicturebackend.common.ResultUtils;
import com.zjb.zjbpicturebackend.constants.UserConstant;
import com.zjb.zjbpicturebackend.domain.dto.picture.PictureQueryRequest;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceAddRequest;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceEditRequest;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceQueryRequest;
import com.zjb.zjbpicturebackend.domain.dto.space.SpaceUpdateRequest;
import com.zjb.zjbpicturebackend.domain.entity.Space;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.enums.SpaceLevelEnum;
import com.zjb.zjbpicturebackend.domain.vo.PictureVO;
import com.zjb.zjbpicturebackend.domain.vo.SpaceVO;
import com.zjb.zjbpicturebackend.domain.vo.SpaceLevel;
import com.zjb.zjbpicturebackend.exception.BusinessException;
import com.zjb.zjbpicturebackend.exception.ErrorCode;
import com.zjb.zjbpicturebackend.exception.ThrowUtils;
import com.zjb.zjbpicturebackend.service.ISpaceService;
import com.zjb.zjbpicturebackend.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 空间 前端控制器
 * </p>
 *
 * @author zjb
 * @since 2025-07-13
 */
@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
@Api(tags = "空间管理接口")
public class SpaceController {

    private final IUserService userService;

    private final ISpaceService spaceService;
    /**
     * 添加空间
     * @param spaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加空间")
    public BaseResponse<Long> addSpace(SpaceAddRequest spaceAddRequest, HttpServletRequest  request){
        ThrowUtils.throwIf(spaceAddRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        long newId = spaceService.addSpace(spaceAddRequest, loginUser);
        return ResultUtils.success(newId);
    }

    /**
     * 获取空间等级列表
     * @return
     */
    @GetMapping("/list/level")
    @ApiOperation("获取空间等级列表")
    public BaseResponse<List<SpaceLevel>> listSpaceLevel() {
        List<SpaceLevel> spaceLevelList = Arrays.stream(SpaceLevelEnum.values()) // 获取所有枚举
                .map(spaceLevelEnum -> new SpaceLevel(
                        spaceLevelEnum.getValue(),
                        spaceLevelEnum.getText(),
                        spaceLevelEnum.getMaxCount(),
                        spaceLevelEnum.getMaxSize()))
                .collect(Collectors.toList());
        return ResultUtils.success(spaceLevelList);
    }

    /**
     * 更新空间（仅管理员使用）
     * @param spaceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("更新空间（仅管理员使用）")
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 删除空间（仅允许空间创建人和管理员删除）
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除空间")
    public BaseResponse<Boolean> deleteSpace(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Space space = spaceService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR);
        //校验权限（仅允许空间创建人和管理员删除）
        User loginUser = userService.getLoginUser(request);
        if (!userService.isAdmin(loginUser) && !space.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限操作该空间");
        }
        boolean result = spaceService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 编辑空间（给用户使用）
     */
    @PostMapping("/edit")
    @ApiOperation("编辑空间")
    public BaseResponse<Boolean> editSpace(@RequestBody SpaceEditRequest spaceEditRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceEditRequest == null || spaceEditRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        Space space = spaceService.getById(spaceEditRequest.getId());
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR, "空间不存在");
        if(!space.getUserId().equals(loginUser.getId())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限操作该空间");
        }
        Space updateSpace = BeanUtil.copyProperties(spaceEditRequest, Space.class);
        boolean result = spaceService.updateById(updateSpace);
        //Boolean result = pictureService.editSpace(pictureEditRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取空间列表（仅管理员可用）
     */
    @PostMapping("/list/page")
    @ApiOperation("分页获取空间列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Space>> listSpaceByPage(@RequestBody SpaceQueryRequest spaceQueryRequest) {
        long current = spaceQueryRequest.getCurrent();
        long size = spaceQueryRequest.getPageSize();
        // 查询数据库
        Page<Space> spacePage = spaceService.page(new Page<>(current, size),
                spaceService.getQueryWrapper(spaceQueryRequest));
        return ResultUtils.success(spacePage);
    }

    /**
     * 分页获取空间列表（封装类）
     */
    @PostMapping("/list/page/vo")
    @ApiOperation("分页获取空间列表（封装类）")
    public BaseResponse<Page<SpaceVO>> listSpaceVOByPage(@RequestBody
                                                             SpaceQueryRequest spaceQueryRequest,
                                                             HttpServletRequest request) {
        Page<SpaceVO> spaceVOPage = spaceService.listSpaceVOByPage(spaceQueryRequest, request);
        // 获取封装类
        return ResultUtils.success(spaceVOPage);
    }

    /**
     * 根据 id 获取空间（封装类）
     */
    @GetMapping("/get/vo")
    @ApiOperation("根据 id 获取空间（封装类）")
    public BaseResponse<SpaceVO> getSpaceVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        Space space = spaceService.getById(id);
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(spaceService.getSpaceVO(space, request));
    }
}
