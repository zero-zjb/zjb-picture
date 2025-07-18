package com.zjb.zjbpicturebackend.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjb.zjbpicturebackend.annotation.AuthCheck;
import com.zjb.zjbpicturebackend.common.BaseResponse;
import com.zjb.zjbpicturebackend.common.DeleteRequest;
import com.zjb.zjbpicturebackend.common.ResultUtils;
import com.zjb.zjbpicturebackend.constants.UserConstant;
import com.zjb.zjbpicturebackend.domain.dto.picture.*;
import com.zjb.zjbpicturebackend.domain.entity.Picture;
import com.zjb.zjbpicturebackend.domain.entity.Space;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.enums.PictureReviewStatusEnum;
import com.zjb.zjbpicturebackend.domain.vo.PictureTagCategory;
import com.zjb.zjbpicturebackend.domain.vo.PictureVO;
import com.zjb.zjbpicturebackend.exception.BusinessException;
import com.zjb.zjbpicturebackend.exception.ErrorCode;
import com.zjb.zjbpicturebackend.exception.ThrowUtils;
import com.zjb.zjbpicturebackend.service.IPictureService;
import com.zjb.zjbpicturebackend.service.ISpaceService;
import com.zjb.zjbpicturebackend.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 图片 前端控制器
 * </p>
 *
 * @author zjb
 * @since 2025-06-25
 */
@RestController
@RequestMapping("/picture")
@RequiredArgsConstructor
@Api(tags = "图片管理接口")
public class PictureController {
    private final IPictureService pictureService;
    private final IUserService userService;
    private final ISpaceService spaceService;



    /**
     * 多级缓存获取图片列表（缓存）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo/cache")
    @ApiOperation("获取图片列表（多级缓存）")
    public BaseResponse<Page<PictureVO>> listPictureVOByPageWithCache(@RequestBody PictureQueryRequest pictureQueryRequest, HttpServletRequest request) {
        Page<PictureVO> pictureVOPage = pictureService.listPictureVOByPageWithCache(pictureQueryRequest, request);
        // 返回结果
        return ResultUtils.success(pictureVOPage);
    }



    /**
     * 上传图片（可重新上传）
     */
    @PostMapping("/upload")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("本地上传图片")
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUploadRequest pictureUploadRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }

    @PostMapping("/upload/batch")
    @ApiOperation("批量抓取上传图片")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> uploadPictureByBatch(
            @RequestBody PictureUploadByBatchRequest pictureUploadByBatchRequest,
            HttpServletRequest request
    ) {
        ThrowUtils.throwIf(pictureUploadByBatchRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        int uploadCount = pictureService.uploadPictureByBatch(pictureUploadByBatchRequest, loginUser);
        return ResultUtils.success(uploadCount);
    }


    /**
     * 通过 URL 上传图片（可重新上传）
     */
    @PostMapping("/upload/url")
    @ApiOperation("通过url上传图片")
    public BaseResponse<PictureVO> uploadPictureByUrl(
            @RequestBody PictureUploadRequest pictureUploadRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        String fileUrl = pictureUploadRequest.getFileUrl();
        PictureVO pictureVO = pictureService.uploadPicture(fileUrl, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }


    /**
     * 删除图片
     */
    @PostMapping("/delete")
    @ApiOperation("删除图片")
    public BaseResponse<Boolean> deletePicture(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        boolean result = pictureService.deletePicture(deleteRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取图片（仅管理员可用）
     */
    @GetMapping("/get")
    @ApiOperation("根据 id 获取图片")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Picture> getPictureById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Picture picture = pictureService.getById(id);
        ThrowUtils.throwIf(picture == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(picture);
    }

    /**
     * 更新图片（仅管理员可用）
     */
    @PostMapping("/update")
    @ApiOperation("更新图片")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePicture(@RequestBody PictureUpdateRequest pictureUpdateRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(pictureUpdateRequest == null || pictureUpdateRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        //将实体类转换成数据库实体类
        Picture picture = BeanUtil.copyProperties(pictureUpdateRequest, Picture.class);
        //将list转换成json
        picture.setTags(JSONUtil.toJsonStr(pictureUpdateRequest.getTags()));
        //校验图片
        pictureService.validPicture(picture);
        //判断图片是否存在
        Picture oldPicture = pictureService.getById(picture.getId());
        ThrowUtils.throwIf(oldPicture == null, ErrorCode.NOT_FOUND_ERROR);
        // 补充审核参数
        User loginUser = userService.getLoginUser(request);
        pictureService.fillReviewParams(picture, loginUser);
        //操作数据库
        boolean result = pictureService.updateById(picture);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取图片（封装类）
     */
    @GetMapping("/get/vo")
    @ApiOperation("根据 id 获取图片（封装类）")
    public BaseResponse<PictureVO> getPictureVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        Picture picture = pictureService.getById(id);
        ThrowUtils.throwIf(picture == null, ErrorCode.NOT_FOUND_ERROR);
        Long spaceId = picture.getSpaceId();
        if(spaceId != null){
            Space space = spaceService.getById(spaceId);
            ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR, "空间不存在");
            User loginUser = userService.getLoginUser(request);
            pictureService.checkPictureAuth(loginUser, picture);
        }
        return ResultUtils.success(pictureService.getPictureVO(picture, request));
    }

    /**
     * 分页获取图片列表（仅管理员可用）
     */
    @PostMapping("/list/page")
    @ApiOperation("分页获取图片列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 查询数据库
        Page<Picture> picturePage = pictureService.page(new Page<>(current, size),
                pictureService.getQueryWrapper(pictureQueryRequest));
        return ResultUtils.success(picturePage);
    }

    /**
     * 分页获取图片列表（封装类）
     */
    @PostMapping("/list/page/vo")
    @ApiOperation("分页获取图片列表（封装类）")
    public BaseResponse<Page<PictureVO>> listPictureVOByPage(@RequestBody
                                                             PictureQueryRequest pictureQueryRequest,
                                                             HttpServletRequest request) {
        Page<PictureVO> pictureVOPage = pictureService.listPictureVOByPage(pictureQueryRequest, request);
        // 获取封装类
        return ResultUtils.success(pictureVOPage);
    }

    /**
     * 编辑图片（给用户使用）
     */
    @PostMapping("/edit")
    @ApiOperation("编辑图片")
    public BaseResponse<Boolean> editPicture(@RequestBody PictureEditRequest pictureEditRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Boolean result = pictureService.editPicture(pictureEditRequest, loginUser);
        return ResultUtils.success(BooleanUtil.isTrue(result));
    }

    /**
     * 获取标签和分类
     */
    @GetMapping("/tag_category")
    @ApiOperation("获取标签和分类")
    public BaseResponse<PictureTagCategory> listPictureTagCategory() {
        PictureTagCategory pictureTagCategory = new PictureTagCategory();
        List<String> tagList = Arrays.asList("热门", "搞笑", "生活", "高清", "艺术", "校园", "背景", "简历", "创意");
        List<String> categoryList = Arrays.asList("模板", "电商", "表情包", "素材", "海报");
        pictureTagCategory.setTagList(tagList);
        pictureTagCategory.setCategoryList(categoryList);
        return ResultUtils.success(pictureTagCategory);
    }

    /**
     * 图片审核（管理员）
     */
    @PostMapping("/review")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("图片审核（管理员）")
    public BaseResponse<Boolean> doPictureReview(@RequestBody PictureReviewRequest pictureReviewRequest,
                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(pictureReviewRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        pictureService.doPictureReview(pictureReviewRequest, loginUser);
        return ResultUtils.success(true);
    }
}
