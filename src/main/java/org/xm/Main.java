package org.xm;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author XinMu
 */
@SpringBootApplication
@EnableSimbot// 开启simbo
@EnableScheduling  // 开启定时任务
@EnableAsync // 开启异步任务
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}