package com.example.plugins

import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureSecurity() {

    data class MySession(val count: Int = 0)

    this.install(Sessions) {
        this.cookie<MySession>("MY_SESSION") {
            this.cookie.extensions["SameSite"] = "lax"
        }
    }

    routing {
        get("/session/increment") {
            val session = this.call.sessions.get<MySession>() ?: MySession()
            this.call.sessions.set(session.copy(count = session.count + 1))
            this.call.respondText("Counter is ${session.count}. Refresh to increment.")
        }
    }
}
