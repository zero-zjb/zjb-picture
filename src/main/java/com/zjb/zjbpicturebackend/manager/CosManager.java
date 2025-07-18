package com.zjb.zjbpicturebackend.manager;

import cn.hutool.core.io.FileUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.zjb.zjbpicturebackend.config.CosClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CosManager {


    private final CosClientConfig cosClientConfig;

    private final COSClient cosClient;

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象（附带图片信息）
     *
     * @param key  唯一键
     * @param file 文件
     */
    public PutObjectResult putPictureObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        // 对图片进行处理（获取基本信息也被视作为一种图片的处理）
        PicOperations picOperations = new PicOperations();
        // 0 不返回原图信息，1返回原图信息，默认为0
        picOperations.setIsPicInfo(1);
        // 图片处理规则列表
        List<PicOperations.Rule> ruleList = new ArrayList<>();
        //图片压缩（转成webp格式）
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(cosClientConfig.getBucket());
        rule.setRule("imageMogr2/format/webp");
        String webpKey = FileUtil.mainName(key) + ".webp";
        rule.setFileId(webpKey);
        ruleList.add(rule);
        //缩略图处理
        //图片要大于2kb才进行缩略图处理

        if(file.length() > 2 * 1024){
            PicOperations.Rule thumbnailRule = new PicOperations.Rule();
            thumbnailRule.setBucket(cosClientConfig.getBucket());
            thumbnailRule.setRule("imageMogr2/thumbnail/256x256");
            String thumbnailKey = FileUtil.mainName(key) + "_thumbnail.webp";
            thumbnailRule.setFileId(thumbnailKey);
            ruleList.add(thumbnailRule);
        }
        // 构造处理参数
        picOperations.setRules(ruleList);
        putObjectRequest.setPicOperations(picOperations);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key 唯一键
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

    /**
     * 删除对象
     *
     * @param key 文件 key
     */
    public void deleteObject(String key) throws CosClientException {
        cosClient.deleteObject(cosClientConfig.getBucket(), key);
    }

}


