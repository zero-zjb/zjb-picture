package com.zjb.zjbpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjb.zjbpicturebackend.domain.dto.picture.*;
import com.zjb.zjbpicturebackend.domain.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.domain.vo.PictureVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 图片 服务类
 * </p>
 *
 * @author zjb
 * @since 2025-06-25
 */
public interface IPictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param inputSource
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);


    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );


    /**
     * 获取查询条件
     *
     * @param pictureQueryRequest 查询条件
     * @return 查询条件
     */
    public QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取封装类
     * 获取单个图片封装
     * @param picture
     * @param request
     * @return PictureVO
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取封装类
     * 获取图片列表封装
     * @param picturePage
     * @param request
     * @return Page<PictureVO>
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 审核图片
     *
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     *
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest
     * @param loginUser
     * @return
     */
    Boolean editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 异步删除图片文件
     *
     * @param oldPicture
     */
    @Async
    void clearPictureFile(Picture oldPicture);
}
