package com.marine.instamarineapi.config;


import com.marine.instamarinecore.config.CoreConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreConfig.class)
public class ApiConfig {
}
