package org.xm.listener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： xinmu
 * @date： 2022/11/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.Identifies;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.resources.Resource;
import org.springframework.stereotype.Controller;

import java.net.URL;

@Controller
@Slf4j
public class BotListenerController {
    /**
     * 群聊接收好友消息 “你好” 回应好友 “你好，@[好友]”
     * GroupMessageEvent 表示一个群信息事件  后边带Event的一般管它叫 “事件”
     * 根据参数事件的不同，我们可以去监听不同的事件。
     * 比如 GroupMessageEvent表示监听所有群的消息，每当群有人说话，那么就触发一次这个方法。
     * @param event
     */
    @SneakyThrows
    @ContentTrim // 去除内容消息前后空格
    @Listener//表示这是一个监听
    @Filter(value = "你好")//过滤信息、支持正则表达式
    public void onEvent(GroupMessageEvent event) {
        String plainText = event.getMessageContent().getPlainText();
        log.info(event.getMessageContent().toString());
        log.info(plainText);
        MessagesBuilder builder = new MessagesBuilder();
        builder.append(" 你好啊! ");//发送消息
        builder.append(new At(event.getAuthor().getId()));//@某人
        builder.append(new Face(Identifies.ID(50)));//发送表情
        //发送图片 上传图片
        ResourceImage resourceImage = Image.of(Resource.of(new URL("https://www.baidu.com/img/flexible/logo/pc/result.png")));
        builder.image(resourceImage.getResource());
//        builder.append(
//                event.getBot().resolveImageBlocking(Identifies.ID("{BADC82A3-216B-84E6-BE60-02C2454F2F5E}.jpg"))
//        );
        event.getSource().sendBlocking(builder.build());
    }

    /**
     * 私聊接收好友消息 “你好” 回应好友 “你好，@[好友]”
     * @param event
     */
    @ContentTrim // 去除内容消息前后空格
    @Listener//表示这是一个监听
    @Filter(value = "你好")//过滤信息、支持正则表达式
    public void onEvent(FriendMessageEvent event) {
        String plainText = event.getMessageContent().getPlainText();
        log.info(plainText);
        MessagesBuilder builder = new MessagesBuilder();
        builder.append("你好");
        builder.append(new At(event.getUser().getId()));
        event.getSource().sendBlocking(builder.build());
    }
}