package com.xuleyan.finals.web;

import com.xuleyan.frame.tracer.annotation.EnableTracer;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

// 启动类
// , "com.xuleyan.frame.extend.filter.*"
// 其本身就是一个JavaConfig，即可以充当Spring配置文件

// , @ComponentScan("com.xuleyan.frame.web"),
//        , @ComponentScan("com.test.xly"),
@SpringBootApplication
@ComponentScans({@ComponentScan("com.xuleyan.finals"), @ComponentScan("com.xuleyan.frame.extend"),
        @ComponentScan("com.xuleyan.frame.mask")})
@DubboComponentScan(basePackages = {"com.xuleyan.first.service.*"})
@Slf4j
@EnableTracer
public class Application {

    public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }

}
