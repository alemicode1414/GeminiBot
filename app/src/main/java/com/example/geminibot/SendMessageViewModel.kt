package com.example.geminibot

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SendMessageViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val state = _uiState.asStateFlow()

    val listOfMessage = mutableListOf<MessageModel>()
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    val chat = model.startChat()

    fun sendMessage(message: String) {
        viewModelScope.launch {

            listOfMessage.add(MessageModel(message, true))
            _uiState.value = UiState.Message(listOfMessage.toList())
            delay(200)
            listOfMessage.add(MessageModel("Processing ...."))
            _uiState.value = UiState.Message(listOfMessage.toList())

            try {
                val response = chat.sendMessage(message)
                listOfMessage.removeAt(listOfMessage.lastIndex)

                listOfMessage.add(MessageModel(response.text.toString()))
                _uiState.value = UiState.Message(listOfMessage.toList())
            } catch (e: Exception) {
                _uiState.value = UiState.Error("error please send again")
            }
        }
    }
}

sealed interface UiState {
    data class Message(val messages: List<MessageModel>) : UiState
    data object Loading : UiState
    data class Error(val errorMessage: String) : UiState
}