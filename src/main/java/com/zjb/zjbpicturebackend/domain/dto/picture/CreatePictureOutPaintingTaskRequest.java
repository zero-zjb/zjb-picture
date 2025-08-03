package com.zjb.zjbpicturebackend.domain.dto.picture;

import com.zjb.zjbpicturebackend.api.aliyunai.domain.CreateOutPaintingTaskRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 创建图片外扩任务请求对象
 */
@Data
@ApiModel(value = "创建图片外扩任务请求对象", description = "创建图片外扩任务请求对象")
public class CreatePictureOutPaintingTaskRequest implements Serializable {

    /**
     * 图片 id
     */
    @ApiModelProperty(value = "图片 id")
    private Long pictureId;

    /**
     * 扩图参数
     */
    @ApiModelProperty(value = "扩图参数")
    private CreateOutPaintingTaskRequest.Parameters parameters;

    private static final long serialVersionUID = 1L;
}
