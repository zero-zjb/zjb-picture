package com.zjb.zjbpicturebackend.domain.dto.space.analyze;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 空间用户上传分析请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("空间用户上传分析请求")
public class SpaceUserAnalyzeRequest extends SpaceAnalyzeRequest {

    /**
     * 用户 ID
     */
    @ApiModelProperty("用户 ID")
    private Long userId;

    /**
     * 时间维度：day / week / month
     */
    @ApiModelProperty("时间维度：day / week / month")
    private String timeDimension;
}
