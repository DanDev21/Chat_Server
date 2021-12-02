package com.example.plugins

import com.example.feature_chat_room.di.chatRoomModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureDependencyInjection() {
    this.install(Koin) {
        modules(chatRoomModule)
    }
}
