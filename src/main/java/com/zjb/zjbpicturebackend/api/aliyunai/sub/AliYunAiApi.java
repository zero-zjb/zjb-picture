package com.zjb.zjbpicturebackend.api.aliyunai.sub;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.zjb.zjbpicturebackend.api.aliyunai.domain.CreateOutPaintingTaskRequest;
import com.zjb.zjbpicturebackend.api.aliyunai.domain.CreateOutPaintingTaskResponse;
import com.zjb.zjbpicturebackend.api.aliyunai.domain.GetOutPaintingTaskResponse;
import com.zjb.zjbpicturebackend.exception.BusinessException;
import com.zjb.zjbpicturebackend.exception.ErrorCode;
import com.zjb.zjbpicturebackend.exception.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AliYunAiApi {
    // 读取配置文件
    @Value("${aliYun.apiKey}")
    private String apiKey;

    // 创建任务地址
    public static final String CREATE_OUT_PAINTING_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/out-painting";

    // 查询任务状态
    public static final String GET_OUT_PAINTING_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/tasks/%s";

    /**
     * 创建任务
     *
     * @param createOutPaintingTaskRequest
     * @return
     */
    public CreateOutPaintingTaskResponse createOutPaintingTask(CreateOutPaintingTaskRequest createOutPaintingTaskRequest){
        ThrowUtils.throwIf(createOutPaintingTaskRequest == null, ErrorCode.PARAMS_ERROR, "扩图参数为空");
        //发送请求
        HttpRequest httpRequest = HttpRequest.post(CREATE_OUT_PAINTING_TASK_URL)
                .header(Header.AUTHORIZATION, "Bearer " + apiKey)
                .header("X-DashScope-Async", "enable")
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .body(JSONUtil.toJsonStr(createOutPaintingTaskRequest));
        try(HttpResponse httpResponse = httpRequest.execute()){
            if(!httpResponse.isOk()){
                log.error("创建任务失败：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI扩图失败");
            }
            CreateOutPaintingTaskResponse response = JSONUtil.toBean(httpResponse.body(), CreateOutPaintingTaskResponse.class);
            String errorCode = response.getCode();
            if(StrUtil.isNotBlank(errorCode)){
                String errorMessage = response.getMessage();
                log.error("AI扩图失败：errorCode: {}, errorMessage: {}",errorCode, errorMessage);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI扩图失败");
            }
            return response;
        }
    }
    /**
     * 查询创建的任务
     *
     * @param taskId
     * @return
     */
    public GetOutPaintingTaskResponse getOutPaintingTask(String taskId){
        ThrowUtils.throwIf(taskId == null, ErrorCode.PARAMS_ERROR, "任务id为空");
        //发送请求

        try(HttpResponse httpResponse = HttpRequest.get(String.format(GET_OUT_PAINTING_TASK_URL, taskId))
                .header(Header.AUTHORIZATION, "Bearer " + apiKey)
                .execute()){
            if(!httpResponse.isOk()){
                log.error("查询任务失败：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI扩图失败");
            }
            return JSONUtil.toBean(httpResponse.body(), GetOutPaintingTaskResponse.class);
        }
    }
}

