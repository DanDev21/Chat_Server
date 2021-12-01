package com.example.plugins

import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureSockets() {
    this.install(WebSockets) {
        this.pingPeriod = Duration.ofSeconds(15)
        this.timeout = Duration.ofSeconds(15)
        this.maxFrameSize = Long.MAX_VALUE
        this.masking = false
    }

    routing {
        webSocket("/") { // websocketSession
            for (frame in this.incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        this.outgoing.send(Frame.Text("YOU SAID: $text"))
                        if (text.equals("bye", ignoreCase = true)) {
                            close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}
