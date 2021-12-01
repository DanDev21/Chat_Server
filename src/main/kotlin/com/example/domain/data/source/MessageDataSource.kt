package com.example.domain.data.source

import com.example.domain.data.model.Message

interface MessageDataSource {

    suspend fun insert(message: Message)

    suspend fun getAll(): List<Message>
}