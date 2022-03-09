package com.attang.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 教师主启动类
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = ("com.attang"))
//@MapperScan("com.attang.eduservice.mapper.xml")
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
