package ru.mail.kmikhailov.b.csrfdemo.contoller;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import ru.mail.kmikhailov.b.csrfdemo.config.CsrfTokenRepositoryConfig;
import ru.mail.kmikhailov.b.csrfdemo.config.WebSecurity;

import java.util.concurrent.atomic.AtomicReference;

@WebFluxTest(controllers = CsrfDemoController.class)
@Import({WebSecurity.class, CsrfTokenRepositoryConfig.class})
class CsrfDemoControllerTest {
    private static final String SESSION = "SESSION";
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCsrf() {
        var sessionCookieRef = new AtomicReference<String>();
        byte[] html = webTestClient.get()
                .uri("/test?user=TestUser")
                .exchange()
                .expectCookie().value(SESSION, sessionCookieRef::set)
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();
        var document = Jsoup.parse(new String(html));
        String token = document.select("input[name=__RequestVerificationToken]").attr("value");
        callLogout(token, sessionCookieRef.get());
    }

    private void callLogout(String token, String sessionId) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user", "TestUser");
        map.add("__RequestVerificationToken", token);
        webTestClient
                .post()
                .uri("/test/logout")
                .cookie(SESSION, sessionId)
                .body(BodyInserters.fromFormData(map))
                .exchange()
                .expectStatus().isOk();
    }
}