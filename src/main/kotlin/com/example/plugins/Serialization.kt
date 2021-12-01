package com.example.plugins

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*

fun Application.configureSerialization() {

    this.install(ContentNegotiation) {
        this.json()
    }
}
