package com.zjb.zjbpicturebackend.domain.dto.space;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 创建空间请求对象
 * @Author: zjb
 * @Date: 2023/9/27 10:07
 */
@ApiModel(value = "创建空间请求对象", description = "创建空间请求对象")
@Data
public class SpaceAddRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
