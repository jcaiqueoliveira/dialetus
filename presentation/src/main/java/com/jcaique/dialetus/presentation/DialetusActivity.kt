package com.jcaique.dialetus.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.jcaique.dialetus.presentation.ui.DialetusTheme

class DialetusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    DialetusTheme {
        Scaffold(
            topBar = {
                AppTop()
            },
            bodyContent = {
                AppContent()
            },
            bottomBar = {
                AppBottom()
            }
        )
    }
}

@Composable
fun AppTop() {
    Column {
        val query = remember { mutableStateOf("") }

        TopAppBar(
            title = {
                Text(text = "Baianes")
            },
            backgroundColor = Color.White,
            elevation = 0.dp
        )
        TextField(
            value = query.value,
            placeholder = {
                Text(
                    text = "Pesquisar",
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    asset = Icons.Outlined.Search,
                    tint = Color.Gray
                )
            },
            onValueChange = {
                query.value = it
            },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50)),
            backgroundColor = Color.LightGray.copy(alpha = .2f),
            activeColor = Color.Transparent
        )
        Divider(
            color = Color.LightGray.copy(alpha = .4f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun AppContent() {
    // TODO
}

@Composable
fun AppBottom() {
    // TODO
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App()
}
