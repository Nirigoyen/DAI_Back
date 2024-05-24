package com.moviezone.dai_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class DaiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaiApiApplication.class, args);
    }

}
