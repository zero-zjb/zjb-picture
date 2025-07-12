package com.zjb.zjbpicturebackend.domain.dto.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "图片批量抓取上传对象", description = "图片批量上传")
public class PictureUploadByBatchRequest implements Serializable {
    private static final long serialVersionUID = -1908183709490661349L;
    /**
     * 图片id
     */
    @ApiModelProperty(value = "图片搜索词")
    private String searchText;

    /**
     * 文件地址
     */
    @ApiModelProperty(value = "图片抓取数量")
    private Integer count = 30;

    /**
     * 名称前缀
     */
    @ApiModelProperty(value = "名称前缀")
    private String namePrefix;

}
