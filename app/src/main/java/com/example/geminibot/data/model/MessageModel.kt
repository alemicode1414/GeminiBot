package com.example.geminibot.data.model

data class MessageModel(
    val message: String,
    val isYou: Boolean = false,
    val id: Long = System.currentTimeMillis()
)
