package com.example.s1114658

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DropMenuSample()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true, device = "id:pixel_7_pro", showSystemUi = true, name = "First View")
    @Composable
    private fun DropMenuSample() {
        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf("Home") }
        var currentView by remember { mutableStateOf("服務總覽") }
        var imageAlpha by remember { mutableStateOf(1f) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.maria), // 將 R.drawable.maria 替換為你的圖片資源 ID
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    actions = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("簡介", color = Color.Blue) },
                                onClick = {
                                    selectedOption = "簡介"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("主要機構", color = Color.Red) },
                                onClick = {
                                    selectedOption = "主要機構"
                                    expanded = false
                                }
                            )
                        }
                    }
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        item {
                            when (currentView) {
                                "服務總覽" -> Text(
                                    text = "瑪利亞基金會服務總覽",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                "作者：資管二B黃子鑒" -> Text(
                                    text = "關於App作者",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }

                    val imageRes = when (currentView) {
                        "服務總覽" -> R.drawable.service // 將 R.drawable.service 替換為你的 service.jpg 資源 ID
                        else -> R.drawable.face // 將 R.drawable.your_photo 替換為你的照片資源 ID
                    }

                    val alpha by animateFloatAsState(
                        targetValue = if (imageAlpha == 1f) 1f else 0f,
                        animationSpec = tween(durationMillis = 3000)
                    )

                    LaunchedEffect(currentView) {
                        imageAlpha = 0f
                        delay(300)
                        imageAlpha = 1f
                    }

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .graphicsLayer(alpha = alpha)
                    )

                    Button(onClick = {
                        currentView = if (currentView == "服務總覽") "作者：資管二B黃子鑒" else "服務總覽"
                    }) {
                        Text(text = if (currentView == "服務總覽") "作者：資管二B黃子鑒" else "服務總覽")
                    }
                }
            }
        )
    }
}