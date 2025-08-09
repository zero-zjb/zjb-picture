package com.zjb.zjbpicturebackend.domain.dto.space.analyze;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间大小分析请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("空间大小分析请求")
public class SpaceSizeAnalyzeRequest extends SpaceAnalyzeRequest {

}
