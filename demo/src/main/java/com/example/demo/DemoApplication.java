package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println("服务启动开始！");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("服务启动结束！");
    }

}
