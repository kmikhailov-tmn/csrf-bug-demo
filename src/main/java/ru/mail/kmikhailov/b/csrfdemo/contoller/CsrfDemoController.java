package ru.mail.kmikhailov.b.csrfdemo.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.mail.kmikhailov.b.csrfdemo.contoller.request.LogoutRequest;

import java.util.Map;

@Controller
public class CsrfDemoController {

    @GetMapping("/test")
    public Mono<Rendering> home(@RequestParam final String user) {
        return Mono.just(Rendering.view("home")
                .model(Map.of("user", user))
                .build());
    }

    @PostMapping("/test/logout")
    public Mono<Rendering> logout(final LogoutRequest logoutRequest) {
        return Mono.just(Rendering.view("logout-success")
                .model(Map.of("user", logoutRequest.user()))
                .build());
    }
}
