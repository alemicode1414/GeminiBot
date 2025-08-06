package com.example.geminibot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.geminibot.ui.theme.GeminiBotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel by viewModels<SendMessageViewModel>()
        val newsViewModel by viewModels<NewsViewModel>()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ChatScreen(
//                        viewModel = viewModel,
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    NewsList(modifier = Modifier.padding(innerPadding), newsViewModel)
                }
            }
        }
    }
}

@Composable
fun NewsList(modifier: Modifier = Modifier, newsViewModel: NewsViewModel) {

    val news by newsViewModel.uiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            items(news) { data ->

                data?.let { item ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .border(width = 1.dp, color = Color.Cyan),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item.publisher?.faviconUrl?.let {
                                NewsItemCard(it)
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                item.title?.let {
                                    Text(
                                        text = it,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 18.sp
                                        ),
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                item.description?.let {
                                    Text(
                                        text = it,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }

                            }
                        }


                    }

                }
            }
        }
    }
}


@Composable
fun NewsItemCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp)
            .widthIn(max = 180.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }
    }
}