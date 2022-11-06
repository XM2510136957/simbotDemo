package org.xm.botScheduleTask;


import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.bot.OriginBotManager;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： xinmu
 * @date： 2022/11/6 13:33
 * @description：机器人定时任务发送
 * @modifiedBy：
 * @version: 1.0
 */
@Component
@Slf4j
public class SaticScheduleTask {

    /**
     * 每分钟执行一次
     * 发送群消息
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    private void sendGroupInfo() {
        log.info("执行发送群消息定时任务！");
        //机器人管理器
        OriginBotManager manager = OriginBotManager.INSTANCE;
        //获取所有管理器
        manager.forEach(bots -> {
            //获取所有机器人
            bots.all().forEach(bot -> {
                //获取机器人所在所有群
                bot.getGroups().asStream().forEach(group -> {

                    try {
                        //如果群号等于xxx群才发送信息
                        if (group.getId().toString().equals("645790152")) {
                            //消息构建器
                            MessagesBuilder builder = new MessagesBuilder();
                            //发送文字内容
                            builder.append(" 你好啊! ");
                            //发送图片 上传图片
                            ResourceImage resourceImage = Image.of(Resource.of(new URL("https://www.baidu.com/img/flexible/logo/pc/result.png")));
                            builder.image(resourceImage.getResource());
                            //最后发送构建好的消息内容
                            group.sendBlocking(builder.build());
                        }

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                });

            });
        });
    }

    /**
     * 定时发送好友信息
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    private void sendFriendInfo() {
        log.info("执行发送好友消息定时任务！");
        //机器人管理器
        OriginBotManager manager = OriginBotManager.INSTANCE;
        //获取所有管理器
        manager.forEach(bots -> {
            //获取所有机器人
            bots.all().forEach(bot -> {

                //好友消息发送
                bot.getContacts().asStream().forEach(friend -> {
                    //如果QQ号等于xxxx就发送信息，不然会集体发送给所以好友，容易被风控
                    if (friend.getId().toString().equals("2510136957")) {
                        //消息构建器
                        MessagesBuilder builder = new MessagesBuilder();
                        //获取好友名称
                        String username = friend.getUsername();
                        builder.append(username).append("\r\n").append("你好呀!");
                        //发送私聊信息
                        friend.sendBlocking(builder.build());
                    }

                });

            });

        });

    }

}
