package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 以图搜图请求
 */
@Data
@ApiModel(value="以图搜图请求对象", description="以图搜图请求")
public class SearchPictureByPictureRequest implements Serializable {

    /**
     * 图片 id
     */
    @ApiModelProperty(value = "图片 id")
    private Long pictureId;

    private static final long serialVersionUID = 1L;
}
