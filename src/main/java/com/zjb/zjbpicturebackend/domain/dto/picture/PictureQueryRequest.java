package com.zjb.zjbpicturebackend.domain.dto.picture;

import com.zjb.zjbpicturebackend.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 图片查询请求，需要继承公共包中的 PageRequest 来支持分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="图片查询请求对象", description="图片查询请求")
public class PictureQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -754217258462530043L;
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
  
    /**  
     * 文件体积  
     */  
    @ApiModelProperty(value = "文件体积")
    private Long picSize;  
  
    /**  
     * 图片宽度  
     */
    @ApiModelProperty(value = "图片宽度")
    private Integer picWidth;  
  
    /**  
     * 图片高度  
     */
    @ApiModelProperty(value = "图片高度")
    private Integer picHeight;  
  
    /**  
     * 图片比例  
     */
    @ApiModelProperty(value = "图片比例")
    private Double picScale;  
  
    /**  
     * 图片格式  
     */
    @ApiModelProperty(value = "图片格式")
    private String picFormat;  
  
    /**  
     * 搜索词（同时搜名称、简介等）  
     */
    @ApiModelProperty(value = "搜索词")
    private String searchText;  
  
    /**  
     * 用户 id  
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
}
