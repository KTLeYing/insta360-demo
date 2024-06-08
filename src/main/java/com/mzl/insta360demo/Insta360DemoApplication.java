package com.mzl.insta360demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Insta360DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Insta360DemoApplication.class, args);
    }

}
