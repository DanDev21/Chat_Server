package com.example.feature_chat_room

import com.example.core.domain.exception.QChatException
import com.example.domain.data.model.Message
import com.example.domain.data.source.MessageDataSource
import com.example.feature_chat_room.domain.data.model.User
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatRoomService(
    private val messageDataSource: MessageDataSource
) {
    private val users = ConcurrentHashMap<String, User>()

    fun join(user: User) {
        if (users.containsKey(user.username)) {
            throw QChatException.UserAlreadyInTheRoomException()
        }
        this.users[user.username] = user
    }

    suspend fun sendMessage(message: Message) {
        this.messageDataSource.insert(message)
        this.broadcastMessage(message)
    }

    private suspend fun broadcastMessage(message: Message) {
        val serializedMessage = Json.encodeToString(message)
        this.users.values.forEach { user ->
            user.webSocket.send(Frame.Text(serializedMessage))
        }
    }

    suspend fun disconnect(username: String) {
        if (users.containsKey(username)) {
            this.users[username]?.webSocket?.close()
            this.users.remove(username)
        }
    }

    suspend fun getAllMessages() = this.messageDataSource.getAll()
}