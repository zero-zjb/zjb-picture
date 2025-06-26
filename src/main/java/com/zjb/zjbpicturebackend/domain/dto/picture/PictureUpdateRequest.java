package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图片更新请求
 * 给管理员使用。注意要将 tags 的类型改为 List<String>，便于前端上传
 */
@Data
@ApiModel(value="图片更新请求对象", description="图片更新请求")
public class PictureUpdateRequest implements Serializable {
  
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
