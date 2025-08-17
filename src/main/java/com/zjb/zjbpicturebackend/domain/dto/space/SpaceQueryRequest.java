package com.zjb.zjbpicturebackend.domain.dto.space;

import com.zjb.zjbpicturebackend.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 空间查询请求
 * @Author: zjb
 * @Date: 2023/9/27 14:05
 */
@ApiModel("空间查询请求")
@EqualsAndHashCode(callSuper = true)
@Data
public class SpaceQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 用户 id
     */
    @ApiModelProperty(value = "用户 id")
    private Long userId;

    /**
     * 空间名称
     */
    @ApiModelProperty(value = "空间名称")
    private String spaceName;

    /**
     * 空间级别：0-普通版 1-专业版 2-旗舰版
     */
    @ApiModelProperty(value = "空间级别：0-普通版 1-专业版 2-旗舰版")
    private Integer spaceLevel;

    /**
     * 空间类型：0-私有 1-团队
     */
    @ApiModelProperty(value = "空间类型：0-私有 1-团队")
    private Integer spaceType;

    private static final long serialVersionUID = 1L;
}
