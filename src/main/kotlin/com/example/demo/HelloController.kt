package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {

    @GetMapping("hello/{name}")
    fun hello(@PathVariable name: String): Mono<Hello> {
        return Mono.just(Hello("1", "Hello, $name !"))
    }
}

data class Hello(val uuid: String, val message: String)