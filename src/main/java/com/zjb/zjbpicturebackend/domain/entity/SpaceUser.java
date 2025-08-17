package com.zjb.zjbpicturebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 空间用户关联
 * </p>
 *
 * @author zjb
 * @since 2025-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("space_user")
@ApiModel(value="SpaceUser对象", description="空间用户关联")
public class SpaceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "空间 id")
    @TableField("spaceId")
    private Long spaceId;

    @ApiModelProperty(value = "用户 id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "空间角色：viewer/editor/admin")
    @TableField("spaceRole")
    private String spaceRole;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private LocalDateTime updateTime;


}
