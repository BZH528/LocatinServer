package com.runone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.runone")
@EnableAutoConfiguration
@SpringBootApplication
public class LocationServeraMain {
    public static void main(String[] args) {
        SpringApplication.run(LocationServeraMain.class, args);
    }
}
