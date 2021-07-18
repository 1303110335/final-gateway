package com.xuleyan.finals.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

// 启动类
// , "com.xuleyan.frame.extend.filter.*"
// 其本身就是一个JavaConfig，即可以充当Spring配置文件
@SpringBootApplication
@ComponentScans({@ComponentScan("com.xuleyan.finals"), @ComponentScan("com.xuleyan.frame.web"),
        @ComponentScan("com.xuleyan.frame.extend"), @ComponentScan("com.test.xly")})
@DubboComponentScan(basePackages = {"com.xuleyan.first.service.*"})
@Slf4j
public class Application {

    public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }

}
