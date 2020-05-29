package com.wang.dubbox.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboxProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboxProviderApplication.class, args);
    }

}
