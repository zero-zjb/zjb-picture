package com.zjb.zjbpicturebackend.domain.dto.space;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 修改空间请求对象
 * @Author: zjb
 * @Date: 2023/9/1 16:05
 */
@ApiModel(value="修改空间请求对象", description="修改空间请求")
@Data
public class SpaceUpdateRequest implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 空间名称
     */
    @ApiModelProperty(value = "空间名称")
    private String spaceName;

    /**
     * 空间级别：0-普通版 1-专业版 2-旗舰版
     */
    @ApiModelProperty(value = "空间级别：0-普通版 1-专业版 2-旗舰版")
    private Integer spaceLevel;

    /**
     * 空间图片的最大总大小
     */
    @ApiModelProperty(value = "空间图片的最大总大小")
    private Long maxSize;

    /**
     * 空间图片的最大数量
     */
    @ApiModelProperty(value = "空间图片的最大数量")
    private Long maxCount;

    private static final long serialVersionUID = 1L;
}
