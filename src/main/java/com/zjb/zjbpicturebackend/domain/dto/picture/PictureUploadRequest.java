package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="图片上传对象", description="图片上传")
public class PictureUploadRequest implements Serializable {
    private static final long serialVersionUID = -1908183709490661349L;
    /**
     * 图片id
     */
    @ApiModelProperty(value = "图片id")
    private Long id;

    /**
     * 文件地址
     */
    @ApiModelProperty(value = "图片上传地址")
    private String fileUrl;

    /**
     * 空间 id
     */
    private Long spaceId;

    /**
     * 图片名称
     */
    @ApiModelProperty(value = "图片名称")
    private String picName;
}
