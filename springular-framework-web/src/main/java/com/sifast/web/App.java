package com.sifast.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "com.sifast.model", "com.sifast.web.service.impl", "com.sifast.service",
        "com.sifast.service.impl", "com.sifast.dao", "com.sifast.web.service.api",
        "com.sifast.web.config", "com.sifast.common.mapper", "com.sifast.validator" })
@EnableAspectJAutoProxy
@EnableJpaRepositories({ "com.sifast.dao", "com.sifast.model" })
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableTransactionManagement
@EntityScan("com.sifast.model")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
