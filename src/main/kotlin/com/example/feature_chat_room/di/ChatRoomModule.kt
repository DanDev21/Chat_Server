package com.example.feature_chat_room.di

import com.example.core.domain.util.Constants
import com.example.domain.data.source.MessageDataSource
import com.example.domain.data.source.MessageDataSourceImpl
import com.example.feature_chat_room.ChatRoomService
import com.example.feature_chat_room.controller.ChatRoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val chatRoomModule = module {

    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(Constants.DATABASE_NAME)
    }

    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }

    single {
        ChatRoomService(get())
    }

    single {
        ChatRoomController(get())
    }
}