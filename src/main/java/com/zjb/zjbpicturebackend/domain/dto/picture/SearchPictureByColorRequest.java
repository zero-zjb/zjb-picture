package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据颜色搜索图片
 */
@Data
@ApiModel(value="图片编辑请求对象" , description = "根据颜色搜索图片")
public class SearchPictureByColorRequest implements Serializable {

    /**
     * 图片主色调
     */
    @ApiModelProperty("图片主色调")
    private String picColor;

    /**
     * 空间 id
     */
    @ApiModelProperty("空间id")
    private Long spaceId;

    private static final long serialVersionUID = 1L;
}
