package com.example.core.domain.exception

sealed class QChatException(
    override val message: String
) : Exception() {
    class UserAlreadyInTheRoomException : QChatException("this username is already taken!")
}
