package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println("服务启动开始！");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("服务启动结束！");
    }

}
