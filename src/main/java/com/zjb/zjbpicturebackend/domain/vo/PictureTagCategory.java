package com.zjb.zjbpicturebackend.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 图片标签分类
 * @Author: zjb
 * @Date: 2025/6/27 10:05
 */
@Data
public class PictureTagCategory {
    private List<String> tagList;
    private List<String> categoryList;
}
