package com.hotdogshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HotDogShopApplication

fun main(args: Array<String>) {
    runApplication<HotDogShopApplication>(*args)
}