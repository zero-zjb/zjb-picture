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
 * 空间
 * </p>
 *
 * @author zjb
 * @since 2025-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("space")
@ApiModel(value="Space对象", description="空间")
public class Space implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "空间名称")
    @TableField("spaceName")
    private String spaceName;

    @ApiModelProperty(value = "空间级别：0-普通版 1-专业版 2-旗舰版")
    @TableField("spaceLevel")
    private Integer spaceLevel;

    @ApiModelProperty(value = "空间图片的最大总大小")
    @TableField("maxSize")
    private Long maxSize;

    @ApiModelProperty(value = "空间图片的最大数量")
    @TableField("maxCount")
    private Long maxCount;

    @ApiModelProperty(value = "当前空间下图片的总大小")
    @TableField("totalSize")
    private Long totalSize;

    @ApiModelProperty(value = "当前空间下的图片数量")
    @TableField("totalCount")
    private Long totalCount;

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

    /**
     * 空间类型：0-私有 1-团队
     */
    @ApiModelProperty(value = "空间类型：0-私有 1-团队")
    @TableField("spaceType")
    private Integer spaceType;

    @ApiModelProperty(value = "是否删除")
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;


}
