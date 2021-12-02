package com.example.feature_chat_room.controller

import com.example.core.domain.exception.QChatException
import com.example.domain.data.model.Message
import com.example.feature_chat_room.ChatRoomService
import com.example.feature_chat_room.domain.data.model.User
import io.ktor.http.cio.websocket.*

class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {

    fun join(
        sessionId: String,
        username: String,
        webSocket: WebSocketSession
    ): JoinResult = try {
        val user = User(
            sessionId = sessionId,
            username = username,
            webSocket = webSocket
        )
        this.chatRoomService.join(user)
        JoinResult.Success
    } catch (exception: QChatException) {
        JoinResult.Failure
    }

    suspend fun sendMessage(username: String, body: String) {
        val message = Message(
            body = body,
            username = username,
            timestamp = System.currentTimeMillis()
        )
        this.chatRoomService.sendMessage(message)
    }

    suspend fun disconnect(username: String) = this.chatRoomService.disconnect(username)

    suspend fun getAllMessages() = this.chatRoomService.getAllMessages()

    sealed class JoinResult {
        object Success : JoinResult()
        object Failure : JoinResult()
    }
}