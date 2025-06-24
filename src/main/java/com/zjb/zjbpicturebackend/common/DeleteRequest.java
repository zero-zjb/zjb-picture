package com.zjb.zjbpicturebackend.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 通用删除请求类
 */
@Data
@ApiModel(value="通用删除对象", description="删除")
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    private static final long serialVersionUID = 1L;
}

