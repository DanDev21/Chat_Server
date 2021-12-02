package com.example.feature_chat_room.domain.data.model

import io.ktor.http.cio.websocket.*

data class User(
    val sessionId: String,
    val username: String,
    val webSocket: WebSocketSession
)
