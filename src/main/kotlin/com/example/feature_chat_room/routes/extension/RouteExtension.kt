package com.example.feature_chat_room.routes.extension

import com.example.feature_chat_room.controller.ChatRoomController
import com.example.feature_chat_room.domain.data.model.Session
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(chatRoomController: ChatRoomController) =
    this.webSocket(path = "/chat-socket") {
        val session = this.call.sessions.get<Session>()
        if (session == null) {
            this.close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }

        when (chatRoomController.join(
            sessionId = session.sessionId,
            username = session.username,
            webSocket = this)
        ) {
            is ChatRoomController.JoinResult.Failure -> {
                this.call.respond(HttpStatusCode.Conflict)
            }
            else -> Unit
        }

        this.incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                chatRoomController.sendMessage(session.username, frame.readText())
            }
        }

        chatRoomController.disconnect(session.username)
    }

fun Route.getAllMessages(chatRoomController: ChatRoomController) = this.get("/messages") {
    val messages = chatRoomController.getAllMessages()
    call.respond(HttpStatusCode.OK, messages)
}