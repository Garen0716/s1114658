package com.example.s1114658

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.maria),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    actions = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(onClick = { currentView = "台中市愛心家園" }) {
                            Text("台中市愛心家園")
                        }
                        Button(onClick = { currentView = "瑪利亞學園" }) {
                            Text("瑪利亞學園")
                        }
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        item {
                            when (currentView) {
                                "台中市愛心家園" -> {
                                    Text(
                                        text = "台中市愛心家園經市政府公開評選後，委託瑪利亞基金會經營管理，於91年啟用，整棟建築物有四個樓層，目前開辦就醫、就養、就學、就業四大領域的十項業務，提供身心障礙者全方位的服務。",
                                        color = Color.Black,
                                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "長按以下圖片，可以觀看愛心家園地圖",
                                        color = Color.Blue,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                "瑪利亞學園" ->{
                                    Text(
                                        text = "「瑪利亞學園」提供重度以及極重度多重障礙者日間照顧服務，以健康照護為基礎，支持生活多面向參與及學習概念，輔助發展重度身心障礙者自我概念為最終服務目標。",
                                        color = Color.Black,
                                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "雙擊以下圖片，可以觀看瑪利亞學園地圖",
                                        color = Color.Blue,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                }



                            }
                        }
                    }

                    val imageRes = when (currentView) {
                        "台中市愛心家園" -> R.drawable.lovehome
                        else -> R.drawable.campus // 預設圖片資源
                    }
                    val ImageRes = when (currentView) {
                        "瑪利亞學園" -> R.drawable.campus
                        else -> R.drawable.lovehome // 預設圖片資源
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
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        if (currentView == "台中市愛心家園") {
                                            coroutineScope.launch {
                                                val uri =
                                                    Uri.parse("geo:0,0?q=台中市南屯區東興路一段450號")
                                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                                intent.setPackage("com.google.android.apps.maps")
                                                context.startActivity(intent)
                                            }
                                        }
                                    }
                                )
                            }
                    )

                }
            }
        )
    }
}