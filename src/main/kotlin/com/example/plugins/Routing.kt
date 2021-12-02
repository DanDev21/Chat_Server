package com.example.plugins

import com.example.feature_chat_room.controller.ChatRoomController
import com.example.feature_chat_room.routes.extension.chatSocket
import com.example.feature_chat_room.routes.extension.getAllMessages
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val chatRoomController by inject<ChatRoomController>()

    this.install(Routing) {
        this.chatSocket(chatRoomController)
        this.getAllMessages(chatRoomController)
    }
}
