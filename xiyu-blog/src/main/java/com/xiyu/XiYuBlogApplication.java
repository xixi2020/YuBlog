package com.xiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiyu.mapper")
public class XiYuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiYuBlogApplication.class);
    }
}
