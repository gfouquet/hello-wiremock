package com.example.demo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
internal class HelloControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    companion object {
        val wireMockServer = WireMockServer(
            WireMockConfiguration.options().port(8089)
        )

        @BeforeAll
        @JvmStatic
        fun setupWireMock() {
            wireMockServer.start()
        }

        @AfterAll
        @JvmStatic
        fun cleanupWireMock() {
            wireMockServer.stop()
        }
    }

    @Test
    fun should_get_hello() {
        wireMockServer.stubFor(
            get(urlPathEqualTo("/dev/urandom"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("yolo")
                )
        )

        client.get()
            .uri("/hello/World")

            .exchange()

            .expectStatus().isOk()

            .expectBody()
            .jsonPath("$.message").isEqualTo("Hello, World !")
            .jsonPath("$.uuid").isEqualTo("yolo")
    }

}