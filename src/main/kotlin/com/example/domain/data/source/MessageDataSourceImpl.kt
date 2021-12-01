package com.example.domain.data.source

import com.example.domain.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImpl(
    private val database: CoroutineDatabase
) : MessageDataSource {

    private val messages = this.database.getCollection<Message>()

    override suspend fun insert(message: Message) {
        this.messages.insertOne(message)
    }

    override suspend fun getAll(): List<Message> = this.messages.find()
        .descendingSort(Message::timestamp)
        .toList()
}