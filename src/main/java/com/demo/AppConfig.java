package com.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.demo.resttemplate.GoogleTranslateRestTemplate;
import com.demo.resttemplate.MarvelRestTemplate;

@Configuration
public class AppConfig {
 
    @Bean
    @Scope("singleton")
    public MarvelRestTemplate MarvelRestTemplate1() {
        return new MarvelRestTemplate();
    }
    
    @Bean
    @Scope("singleton")
    public GoogleTranslateRestTemplate GoogleTranslateRestTemplate1() {
        return new GoogleTranslateRestTemplate();
    }
}