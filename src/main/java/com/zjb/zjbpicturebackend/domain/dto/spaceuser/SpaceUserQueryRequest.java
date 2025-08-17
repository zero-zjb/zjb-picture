package com.zjb.zjbpicturebackend.domain.dto.spaceuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 空间成员查询请求
 */
@Data
@ApiModel(value = "空间成员查询请求对象", description = "空间成员查询请求对象")
public class SpaceUserQueryRequest implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 空间 ID
     */
    @ApiModelProperty(value = "空间 ID")
    private Long spaceId;

    /**
     * 用户 ID
     */
    @ApiModelProperty(value = "用户 ID")
    private Long userId;

    /**
     * 空间角色：viewer/editor/admin
     */
    @ApiModelProperty(value = "空间角色：viewer/editor/admin")
    private String spaceRole;

    private static final long serialVersionUID = 1L;
}
