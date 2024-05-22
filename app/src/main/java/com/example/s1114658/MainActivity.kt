package com.example.s1114658

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.maria), // 將 R.drawable.your_image 替換為你的圖片資源 ID
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
                                text = { Text("簡介") },
                                onClick = {
                                    selectedOption = "簡介"
                                    expanded = false
                                },
                            )
                            DropdownMenuItem(
                                text = { Text("主要機構") },
                                onClick = {
                                    selectedOption = "主要機構"
                                    expanded = false
                                },
                            )
                        }
                    }
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        when (selectedOption) {
                            "簡介" -> Text(
                                text = "簡介",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.Blue,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                            "主要機構" -> Text(
                                text = "主要機構",
                                color = Color.Red,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )


                        }
                    }
                }
            }
        )
    }
}