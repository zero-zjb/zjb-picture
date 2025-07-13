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
@ApiModel("空间编辑请求对象")
@Data
public class SpaceEditRequest implements Serializable {

    /**
     * 空间 id
     */
    @ApiModelProperty(value = "空间 id")
    private Long id;

    /**
     * 空间名称
     */
    @ApiModelProperty(value = "空间名称")
    private String spaceName;

    private static final long serialVersionUID = 1L;
}
