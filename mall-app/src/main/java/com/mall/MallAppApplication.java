package com.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication
public class MallAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAppApplication.class, args);
    }

}
