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

    /**
     * 状态：0-待审核; 1-通过; 2-拒绝
     */
    @ApiModelProperty(value = "状态：0-待审核; 1-通过; 2-拒绝")
    @TableField("reviewStatus")
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    @ApiModelProperty(value = "审核信息")
    @TableField("reviewMessage")
    private String reviewMessage;

    /**
     * 审核人 id
     */
    @ApiModelProperty(value = "审核人 id")
    @TableField("reviewerId")
    private Long reviewerId;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("reviewTime")
    private LocalDateTime reviewTime;


    @ApiModelProperty(value = "图片 url")
    @TableField("url")
    private String url;

    /**
     * 缩略图 url
     */
    @ApiModelProperty(value = "缩略图 url")
    @TableField("thumbnailUrl")
    private String thumbnailUrl;


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

    @ApiModelProperty(value = "图片主色调")
    @TableField("picColor")
    private String picColor;


    @ApiModelProperty(value = "空间 id")
    @TableField("spaceId")
    private Long spaceId;

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
