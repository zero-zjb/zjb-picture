package com.zjb.zjbpicturebackend.domain.dto.space.analyze;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间标签分析请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("空间标签分析请求")
public class SpaceTagAnalyzeRequest extends SpaceAnalyzeRequest {

}
