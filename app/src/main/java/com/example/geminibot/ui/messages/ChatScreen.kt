package com.example.geminibot.ui.messages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geminibot.data.model.MessageModel


@Composable
fun ChatScreen(viewModel: SendMessageViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsState()
    var messageText by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TopBarSection()

            when (state) {
                is UiState.Message -> {
                    ChatList(messages = (state as UiState.Message).messages)
                }

                is UiState.Error -> {
                    val errorMessage = (state as UiState.Error).errorMessage
                    if (errorMessage.isNotEmpty()) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                else -> {
                }
            }
        }
        MessageBox(
            message = messageText,
            onMessageChange = { messageText = it },
            onMessageSend = {
                if (it.isNotBlank()) {
                    viewModel.sendMessage(it)
                    messageText = ""
                }
            }
        )
    }
}

@Composable
fun ChatList(messages: List<MessageModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(messages, key = { it.id }) { message ->
            val backgroundColor = if (message.isYou) {
                Color.Green.copy(alpha = 0.5f)
            } else {
                Color.Blue.copy(alpha = 0.5f)
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(backgroundColor, shape = RoundedCornerShape(6.dp))
            ) {
                Text(
                    text = message.message,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun TopBarSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Easy Bot", fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun MessageBox(
    message: String,
    onMessageChange: (String) -> Unit,
    onMessageSend: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("Type a message") }
        )
        IconButton(
            onClick = {
                if (message.isNotBlank()) {
                    onMessageSend(message)
                    onMessageChange("")
                }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}
