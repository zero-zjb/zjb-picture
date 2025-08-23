package com.zjb.zjbpicturebackend.manager.websocket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zjb.zjbpicturebackend.domain.entity.User;
import com.zjb.zjbpicturebackend.manager.websocket.disruptor.PictureEditEventProducer;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditActionEnum;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditMessageTypeEnum;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditRequestMessage;
import com.zjb.zjbpicturebackend.manager.websocket.domain.PictureEditResponseMessage;
import com.zjb.zjbpicturebackend.service.IUserService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定义 WebSocket 处理器类，在连接成功、连接关闭、接收到客户端消息时进行相应的处理。
 */
@Component
@RequiredArgsConstructor
public class PictureEditHandler extends TextWebSocketHandler {


    private final IUserService userService;
    // 每张图片的编辑状态，key: pictureId, value: 当前正在编辑的用户 ID
    private final Map<Long, Long> pictureEditingUsers = new ConcurrentHashMap<>();

    // 保存所有连接的会话，key: pictureId, value: 用户会话集合
    private final Map<Long, Set<WebSocketSession>> pictureSessions = new ConcurrentHashMap<>();

    @Resource
    @Lazy
    private PictureEditEventProducer pictureEditEventProducer;
    /**
     * 广播图片编辑消息(排除自身的会话)
     * @param pictureId 图片 ID
     * @param pictureEditResponseMessage  图片编辑响应消息
     * @param excludeSession 排除的会话
     */
    private void broadcastToPicture(Long pictureId, PictureEditResponseMessage pictureEditResponseMessage, WebSocketSession excludeSession) throws IOException {
        Set<WebSocketSession> webSocketSessions = pictureSessions.get(pictureId);
        if(CollUtil.isNotEmpty(webSocketSessions)){
            //创建ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();
            //配置序列化，将Long类型转换为字符串，解决精度丢失问题
            SimpleModule module = new SimpleModule();
            module.addSerializer(Long.class, ToStringSerializer.instance);
            module.addSerializer(Long.TYPE, ToStringSerializer.instance);//支持long基本类型
            objectMapper.registerModule(module);
            //序列化为JSON字符串
            String message = objectMapper.writeValueAsString(pictureEditResponseMessage);
            TextMessage textMessage = new TextMessage(message);
            for (WebSocketSession webSocketSession : webSocketSessions) {
                //跳过排除的会话
                if(excludeSession != null && webSocketSession.equals(excludeSession)){
                    continue;
                }
                if(webSocketSession.isOpen()){
                    webSocketSession.sendMessage(textMessage);
                }
            }
        }
    }

    // 全部广播
    private void broadcastToPicture(Long pictureId, PictureEditResponseMessage pictureEditResponseMessage) throws Exception {
        broadcastToPicture(pictureId, pictureEditResponseMessage, null);
    }

    /**
     * 连接成功时，将当前用户加入到图片的编辑会话中
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 保存会话到集合中
        User user = (User) session.getAttributes().get("user");
        Long pictureId = (Long) session.getAttributes().get("pictureId");
        pictureSessions.putIfAbsent(pictureId, ConcurrentHashMap.newKeySet());
        pictureSessions.get(pictureId).add(session);

        // 构造响应
        PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
        pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.INFO.getValue());
        String message = String.format("%s加入编辑", user.getUserName());
        pictureEditResponseMessage.setMessage(message);
        pictureEditResponseMessage.setUser(userService.getUserVO(user));
        // 广播给同一张图片的用户
        broadcastToPicture(pictureId, pictureEditResponseMessage);
    }

    /**
     * 编写接收客户端消息的方法，根据消息类别执行不同的处理
     * @param session
     * @param message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 将消息解析为 PictureEditMessage
        PictureEditRequestMessage pictureEditRequestMessage = JSONUtil.toBean(message.getPayload(), PictureEditRequestMessage.class);
        // 从 Session 属性中获取公共参数
        Map<String, Object> attributes = session.getAttributes();
        User user = (User) attributes.get("user");
        Long pictureId = (Long) attributes.get("pictureId");
        // 生产消息
        pictureEditEventProducer.publishEvent(pictureEditRequestMessage, session, user, pictureId);
    }


    /**
     * 处理进入编辑状态的消息
     * @param session
     * @param pictureEditRequestMessage
     * @param pictureId
     * @param user
     */
    public void handleEditActionMessage(WebSocketSession session, PictureEditRequestMessage pictureEditRequestMessage, Long pictureId, User user) throws IOException {
        Long editingUserId = pictureEditingUsers.get(pictureId);
        String editAction = pictureEditRequestMessage.getEditAction();
        PictureEditActionEnum pictureEditActionEnum = PictureEditActionEnum.getEnumByValue(editAction);
        if(pictureEditActionEnum == null){
            return;
        }
        // 确认当前用户是否正在编辑
        if(editingUserId != null && editingUserId.equals(user.getId())){
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.EDIT_ACTION.getValue());
            String message = String.format("用户%s执行%s操作", user.getUserName(), pictureEditActionEnum.getText());
            pictureEditResponseMessage.setMessage(message);
            pictureEditResponseMessage.setEditAction(editAction);
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            //广播给除了当前客户端之外的其他用户，否则会造成重复编辑
            broadcastToPicture(pictureId,pictureEditResponseMessage, session);
        }
    }

    /**
     * 处理进入编辑状态的消息
     * @param session
     * @param pictureEditRequestMessage
     * @param pictureId
     * @param user
     */
    public void handleEnterEditMessage(WebSocketSession session, PictureEditRequestMessage pictureEditRequestMessage, Long pictureId, User user) throws Exception {
        //没有其他用户正在编辑，才能进行编辑
        if(!pictureEditingUsers.containsKey(pictureId)){
            //设置当前用户正在编辑
            pictureEditingUsers.put(pictureId, user.getId());
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.ENTER_EDIT.getValue());
            String message = String.format("用户%s进入编辑状态", user.getUserName());
            pictureEditResponseMessage.setMessage(message);
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            broadcastToPicture(pictureId, pictureEditResponseMessage);
        }
    }

    /**
     * 处理退出编辑状态的消息
     * @param session
     * @param pictureEditRequestMessage
     * @param pictureId
     * @param user
     */
    public void handleExitEditMessage(WebSocketSession session, PictureEditRequestMessage pictureEditRequestMessage, Long pictureId, User user) throws Exception {
        Long editingUserId = pictureEditingUsers.get(pictureId);
        if (editingUserId != null && editingUserId.equals(user.getId())) {
            // 移除当前用户的编辑状态
            pictureEditingUsers.remove(pictureId);
            // 构造响应，发送退出编辑的消息通知
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.EXIT_EDIT.getValue());
            String message = String.format("%s退出编辑图片", user.getUserName());
            pictureEditResponseMessage.setMessage(message);
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            broadcastToPicture(pictureId, pictureEditResponseMessage);
        }
    }

    /**
     * 广播给pictureId对应的所有用户
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Long pictureId = (Long) attributes.get("pictureId");
        User user = (User) attributes.get("user");
        // 移除当前用户的编辑状态
        handleExitEditMessage(session,null, pictureId, user);

        // 删除会话
        Set<WebSocketSession> sessionSet = pictureSessions.get(pictureId);
        if (sessionSet != null) {
            sessionSet.remove(session);
            if (sessionSet.isEmpty()) {
                pictureSessions.remove(pictureId);
            }
        }

        // 响应
        PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
        pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.INFO.getValue());
        String message = String.format("%s离开编辑", user.getUserName());
        pictureEditResponseMessage.setMessage(message);
        pictureEditResponseMessage.setUser(userService.getUserVO(user));
        broadcastToPicture(pictureId, pictureEditResponseMessage);
    }
}
