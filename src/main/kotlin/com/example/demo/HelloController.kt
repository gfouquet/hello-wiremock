package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RestController
class HelloController {

    @GetMapping("hello/{name}")
    fun hello(@PathVariable name: String): Mono<Hello> {
        return WebClient.create("http://dev-random-as-a-service.appspot.com/dev/urandom?count=8&io=text")
            .get()

            .retrieve()

            .bodyToMono(String::class.java)
            .map {
                uuid -> Hello(uuid, "Hello, $name !")
            }

    }
}

data class Hello(val uuid: String, val message: String)