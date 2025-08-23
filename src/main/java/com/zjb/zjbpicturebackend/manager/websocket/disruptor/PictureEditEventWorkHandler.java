package com.zjb.zjbpicturebackend.manager.websocket.disruptor;

import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.WorkHandler;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.manager.websocket.PictureEditHandler;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditMessageTypeEnum;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditRequestMessage;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditResponseMessage;
import com.zjb.zjbpicturebackend.service.IUserService;
import groovy.lang.Lazy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
/**
 * 定义事件处理器(消费者)
 */
@Slf4j
@Component
public class PictureEditEventWorkHandler implements WorkHandler<PictureEditEvent> {

    @Resource
    @Lazy
    private PictureEditHandler pictureEditHandler;

    @Resource
    private IUserService userService;

    @Override
    public void onEvent(PictureEditEvent event) throws Exception {
        PictureEditRequestMessage pictureEditRequestMessage = event.getPictureEditRequestMessage();
        WebSocketSession session = event.getSession();
        User user = event.getUser();
        Long pictureId = event.getPictureId();
        // 获取到消息类别
        String type = pictureEditRequestMessage.getType();
        PictureEditMessageTypeEnum pictureEditMessageTypeEnum = PictureEditMessageTypeEnum.valueOf(type);
        // 调用对应的消息处理方法
        switch (pictureEditMessageTypeEnum) {
            case ENTER_EDIT:
                pictureEditHandler.handleEnterEditMessage(session, pictureEditRequestMessage, pictureId, user);
                break;
            case EDIT_ACTION:
                pictureEditHandler.handleEditActionMessage(session, pictureEditRequestMessage, pictureId, user);
                break;
            case EXIT_EDIT:
                pictureEditHandler.handleExitEditMessage(session, pictureEditRequestMessage, pictureId, user);
                break;
            default:
                PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
                pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.ERROR.getValue());
                pictureEditResponseMessage.setMessage("消息类型错误");
                pictureEditResponseMessage.setUser(userService.getUserVO(user));
                session.sendMessage(new TextMessage(JSONUtil.toJsonStr(pictureEditResponseMessage)));
        }
    }
}
