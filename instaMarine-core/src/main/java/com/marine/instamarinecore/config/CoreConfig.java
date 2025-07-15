package com.marine.instamarinecore.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.marine.instamarinecore.entity")
@ComponentScan(basePackages = "com.marine.instamarinecore.service")
@EnableJpaRepositories(basePackages = "com.marine.instamarinecore.dao")
public class CoreConfig {
}
