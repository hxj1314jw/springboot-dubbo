package com.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by Administrator on 2018/1/9.
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
@EnableAsync
public class AppApplication {
    public static void main(String[] args){
        SpringApplication.run(AppApplication.class, args);
    }
}
