package com.study.shop;

import com.study.shop.sitemesh.SitemeshFilter;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@SpringBootApplication
public class ShopApplication { // Spring Boot의 시작 프로그램

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<SitemeshFilter> sitemeshFilterFilterRegistrationBean() {
        FilterRegistrationBean<SitemeshFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SitemeshFilter());
        return filter;
    }

    @PostConstruct
        // 객체 생성 후에 실행되는 메서드
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
