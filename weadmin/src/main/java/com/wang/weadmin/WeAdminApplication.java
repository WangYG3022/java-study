package com.wang.weadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WANG
 */
@MapperScan("com.wang.weadmin.mapper")
@SpringBootApplication
public class WeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeAdminApplication.class, args);
    }

}
