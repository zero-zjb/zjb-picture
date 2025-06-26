package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图片编辑请求
 * 一般情况下给普通用户使用，可修改的字段范围小于更新请求
 */
@Data
@ApiModel(value="图片编辑请求对象", description="图片编辑请求")
public class PictureEditRequest implements Serializable {
  
    /**  
     * id  
     */
    @ApiModelProperty(value = "图片id")
    private Long id;  
  
    /**  
     * 图片名称  
     */
    @ApiModelProperty(value = "图片名称")
    private String name;  
  
    /**  
     * 简介  
     */
    @ApiModelProperty(value = "图片简介")
    private String introduction;  
  
    /**  
     * 分类  
     */
    @ApiModelProperty(value = "图片分类")
    private String category;  
  
    /**  
     * 标签  
     */
    @ApiModelProperty(value = "图片标签")
    private List<String> tags;
  
    private static final long serialVersionUID = 1L;  
}
