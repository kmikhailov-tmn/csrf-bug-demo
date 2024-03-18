package ru.mail.kmikhailov.b.csrfdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;

@Configuration
public class CsrfTokenRepositoryConfig {
    @Bean
    public ServerCsrfTokenRepository serverCsrfTokenRepository() {
        var csrfTokenRepository = new WebSessionServerCsrfTokenRepository();
        csrfTokenRepository.setParameterName("__RequestVerificationToken");
        return csrfTokenRepository;
    }
}
