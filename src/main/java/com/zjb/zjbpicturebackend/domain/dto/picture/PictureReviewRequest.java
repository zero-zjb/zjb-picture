package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "图片审核请求对象", description = "图片审核请求")
public class PictureReviewRequest implements Serializable {

    private static final long serialVersionUID = -7362884839967309257L;
    /**
     * id
     */
    @ApiModelProperty(value = "图片id")
    private Long id;

    /**
     * 状态：0-待审核, 1-通过, 2-拒绝
     */
    @ApiModelProperty(value = "状态：0-待审核; 1-通过; 2-拒绝")
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    @ApiModelProperty(value = "审核信息")
    private String reviewMessage;
}
