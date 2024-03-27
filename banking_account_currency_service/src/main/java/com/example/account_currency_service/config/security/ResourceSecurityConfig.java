<<<<<<<< HEAD:banking_account_currency_service/src/main/java/com/example/account_currency_service/config/security/ResourceSecurityConfig.java
package com.example.account_currency_service.config.security;
========
package com.example.account_currency_service.config;
>>>>>>>> minhdev:banking_account_currency_service/src/main/java/com/example/account_currency_service/config/ResourceSecurityConfig.java

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class ResourceSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
<<<<<<<< HEAD:banking_account_currency_service/src/main/java/com/example/account_currency_service/config/security/ResourceSecurityConfig.java
                        .antMatchers("/account-currency-service/asu/**").hasAnyRole("Admin", "Staff", "User")
                        .antMatchers("/account-currency-service/as/**").hasAnyRole("Admin", "Staff")
                        .antMatchers("/account-currency-service/a/**").hasRole("Admin")
========
                        .antMatchers("/account-currency-service/asu/**").hasAnyAuthority("Admin","Staff","User")
                        .antMatchers("/account-currency-service/su/**").hasAnyAuthority("Staff","User")
                        .antMatchers("/account-currency-service/a/**").hasAuthority("Admin")
>>>>>>>> minhdev:banking_account_currency_service/src/main/java/com/example/account_currency_service/config/ResourceSecurityConfig.java
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return authenticationConverter;
    }
}
