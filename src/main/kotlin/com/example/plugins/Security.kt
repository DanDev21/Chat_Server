package com.example.plugins

import com.example.feature_chat_room.domain.data.model.Session
import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.util.*

fun Application.configureSecurity() {

    this.install(Sessions) {
        this.cookie<Session>("SESSION")
    }

    this.intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<Session>() == null) {
            val username = this.call.parameters["username"] ?: "Guest"
            this.call.sessions.set(Session(username, generateNonce()))
        }
    }
}