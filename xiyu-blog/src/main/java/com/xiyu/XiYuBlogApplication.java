package com.xiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//定时任务
@EnableScheduling
@MapperScan("com.xiyu.mapper")
public class XiYuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiYuBlogApplication.class);
    }
}
