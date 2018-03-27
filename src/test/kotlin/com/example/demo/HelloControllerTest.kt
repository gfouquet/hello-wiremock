package com.example.demo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class HelloControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun should_get_hello() {
        client.get()
                .uri("/hello/World")

                .exchange()

                .expectStatus().isOk()

                .expectBody()
                .jsonPath("$.message").isEqualTo("Hello, World !")
                .jsonPath("$.uuid").isEqualTo("1")
    }

}