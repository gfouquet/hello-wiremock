package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RestController
class HelloController(
    @Value("\${hello.uuid-service.url}")
    val uuidServiceUrl: String
) {

    @GetMapping("hello/{name}")
    fun hello(@PathVariable name: String): Mono<Hello> {
        return WebClient.create(uuidServiceUrl)
            .get()
            .uri("/dev/urandom?count=8&io=text")

            .retrieve()

            .bodyToMono(String::class.java)
            .map { uuid ->
                Hello(uuid, "Hello, $name !")
            }

    }
}

data class Hello(val uuid: String, val message: String)