package ru.mail.kmikhailov.b.csrfdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;

@Configuration
public class WebSecurity {
    @Autowired
    private ServerCsrfTokenRepository serverCsrfTokenRepository;

    @Bean
    public SecurityWebFilterChain filterChain(final ServerHttpSecurity http) {
        return http.authorizeExchange(cfg -> cfg.anyExchange().permitAll())
                .csrf(csrfSpec -> csrfSpec.csrfTokenRepository(serverCsrfTokenRepository))
                .build();
    }
}
