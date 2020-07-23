package com.jojoidu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
