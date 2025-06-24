package com.zjb.zjbpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.zjb.zjbpicturebackend.mapper")
//开启对象代理，将其暴露出来，就能用AopContext获取代理对象，AopContext.currentProxy()获取当前类的代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class ZjbPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZjbPictureBackendApplication.class, args);
    }

}
