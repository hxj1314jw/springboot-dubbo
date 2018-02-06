package com.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by Administrator on 2018/1/9.
 */
@SpringBootApplication
@ServletComponentScan
public class WebApplication {

    public static void main(String[] args){
        SpringApplication.run(WebApplication.class, args);
    }
}
