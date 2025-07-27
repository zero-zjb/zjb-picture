package com.zjb.zjbpicturebackend.api.imagesearch.baidu.domain;

import lombok.Data;

@Data
public class ImageSearchResult {  
  
    /**  
     * 缩略图地址  
     */  
    private String thumbnailUrl;  
  
    /**  
     * 来源地址  
     */  
    private String fromUrl;  
}

