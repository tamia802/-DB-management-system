package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync //启动异步配置(底层会帮我们创建一个线程池)
@SpringBootApplication
public class DbpmsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbpmsAdminApplication.class, args);
    }

}
