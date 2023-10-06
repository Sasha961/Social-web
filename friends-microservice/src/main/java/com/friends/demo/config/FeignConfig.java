package com.friends.demo.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }


}