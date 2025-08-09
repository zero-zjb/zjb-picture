package com.zjb.zjbpicturebackend.domain.vo.space.analyze;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 空间图片分类统计响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "空间图片分类统计响应")
public class SpaceCategoryAnalyzeResponse implements Serializable {

    /**
     * 图片分类
     */
    private String category;

    /**
     * 图片数量
     */
    private Long count;

    /**
     * 分类图片总大小
     */
    private Long totalSize;

    private static final long serialVersionUID = 1L;
}
