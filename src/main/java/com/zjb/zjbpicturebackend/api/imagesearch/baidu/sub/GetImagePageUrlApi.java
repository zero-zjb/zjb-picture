package com.zjb.zjbpicturebackend.api.imagesearch.baidu.sub;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.zjb.zjbpicturebackend.exception.BusinessException;
import com.zjb.zjbpicturebackend.exception.ErrorCode;
import com.zjb.zjbpicturebackend.exception.ThrowUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 以图搜图获取图片页面的url(Step 1)
 */
@Slf4j
public class GetImagePageUrlApi {

    /**
     * 获取图片页面地址
     *
     * @param imageUrl
     * @return
     */
    public static String getImagePageUrl(String imageUrl) {
        // 1. 准备请求参数
        Map<String, Object> formData = new HashMap<>();
        formData.put("image", imageUrl);
        formData.put("tn", "pc");
        formData.put("from", "pc");
        formData.put("image_source", "PC_UPLOAD_URL");

        //formData.put("imageType", "png");
        // 获取当前时间戳
        long uptime = System.currentTimeMillis();
        // 请求地址
        String url = "https://graph.baidu.com/upload?uptime=" + uptime;
        String acsToken = "1753503605657_1753583328377_OJnzFm6OfKQ3vej6KNvJZ9qpHPoWFEr3x8VlDaPpErsGUMhUCHsIG2zjHTD6FG4fxUMZcv4uIF3jXXwTlIJ9/AZgZDGvt8tSDjDinLZZmeV9x5BECdgU+9Fsy7T2EAyoRnmFnxnXWyRI6SOCvqMZpR/fyW0eKTVlCx9YKrhtwpY2u585TRhxgH1ikVhpG66uFQdUqy4GW0SR/bUbrzVVyhA0o/+ZLVlOdPsQra/GdG09u40b8BF5weo5/U2Bjdf5wmm/yCntbA7F36+tXhdbQZpiBpmpmljn5ZkKC2eM8cRqj2Mk6R2I0C9pXd3yUXjMFnZpMcs9q2CnFD0soHdmyhWetkcL5JBsmNqt+sIy1tRMvJDfG35J10lzyFGzIdor/VA35OE27a4SjRH1Q9rOhMpoQ9AlQ5hgMwkUquv/pGdWWUGS/cbrFBuLkU1/V19O";
        try {
            // 2. 发送 POST 请求到百度接口
            HttpResponse response = HttpRequest.post(url)
                    .form(formData)
                    .header("Acs-Token", acsToken)
                    .timeout(50000)
                    .execute();
            // 判断响应状态
            if (HttpStatus.HTTP_OK != response.getStatus()) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
            }
            // 解析响应
            String responseBody = response.body();
            Map<String, Object> result = JSONUtil.toBean(responseBody, Map.class);

            // 3. 处理响应结果
            if (result == null || !Integer.valueOf(0).equals(result.get("status"))) {
                System.out.println(result.get("status"));
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
            }
            Map<String, Object> data = (Map<String, Object>) result.get("data");
            String rawUrl = (String) data.get("url");
            // 对 URL 进行解码
            String searchResultUrl = URLUtil.decode(rawUrl, StandardCharsets.UTF_8);
            // 如果 URL 为空
            if (searchResultUrl == null) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "未返回有效结果");
            }
            return searchResultUrl;
        } catch (Exception e) {
            log.error("搜索失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "搜索失败");
        }
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://zjbpicture-1365829239.cos.ap-chengdu.myqcloud.com//user_1937748900320407553/2025-06-26_9xK6NPekpstoUMLC.png";
        String result = getImagePageUrl(imageUrl);
        System.out.println("搜索成功，结果 URL：" + result);
    }
}

