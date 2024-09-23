package com.sparta.outsourcing_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class OutsourcingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcingProjectApplication.class, args);
    }

}
