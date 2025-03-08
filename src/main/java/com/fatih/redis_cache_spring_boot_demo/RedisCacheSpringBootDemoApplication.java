package com.fatih.redis_cache_spring_boot_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisCacheSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCacheSpringBootDemoApplication.class, args);
    }

}
