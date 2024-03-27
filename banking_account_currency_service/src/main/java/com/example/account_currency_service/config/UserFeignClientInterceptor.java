package com.example.account_currency_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@Component
public class UserFeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getBearerTokenHeader() {
         String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
         return token;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());

    }



}
