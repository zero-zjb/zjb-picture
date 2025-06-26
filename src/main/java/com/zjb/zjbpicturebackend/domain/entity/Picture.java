package com.zjb.zjbpicturebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图片
 * </p>
 *
 * @author zjb
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("picture")
@ApiModel(value="Picture对象", description="图片")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "图片 url")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "图片名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(value = "分类")
    @TableField("category")
    private String category;

    @ApiModelProperty(value = "标签（JSON 数组）")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "图片体积")
    @TableField("picSize")
    private Long picSize;

    @ApiModelProperty(value = "图片宽度")
    @TableField("picWidth")
    private Integer picWidth;

    @ApiModelProperty(value = "图片高度")
    @TableField("picHeight")
    private Integer picHeight;

    @ApiModelProperty(value = "图片宽高比例")
    @TableField("picScale")
    private Double picScale;

    @ApiModelProperty(value = "图片格式")
    @TableField("picFormat")
    private String picFormat;

    @ApiModelProperty(value = "创建用户 id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "编辑时间")
    @TableField("editTime")
    private LocalDateTime editTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;
}
