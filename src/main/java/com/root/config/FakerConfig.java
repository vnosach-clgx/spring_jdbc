package com.root.config;

import com.github.javafaker.*;
import com.github.javafaker.Number;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfig {
    @Bean
    public Name fakeName() {
        return new Faker().name();
    }

    @Bean
    public Lorem fakeLorem() {
        return new Faker().lorem();
    }

    @Bean
    public DateAndTime fakeDateTime() {
        return new Faker().date();
    }

    @Bean
    public Number fakeNumber() {
        return new Faker().number();
    }
}
