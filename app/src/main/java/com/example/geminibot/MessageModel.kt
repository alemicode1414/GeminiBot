package com.example.geminibot

data class MessageModel(
    val message: String,
    val isYou: Boolean = false,
    val id: Long = System.currentTimeMillis()
)
