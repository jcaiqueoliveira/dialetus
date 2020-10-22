package com.jcaique.dialetus.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.ui.DialetusTheme

private val regionsItems by lazy {
    (0..100).map { index ->
        Region(
            name = "name $index",
            total = index
        )
    }
}

private val dialectsItems by lazy {
    (0..10).map { index ->
        Dialect(
            slug = "slug-$index",
            dialect = "Dialect $index",
            examples = listOf("Example 1", "Example 2", "Example 3"),
            meanings = listOf("Meaning 1", "Meaning 2")
        )
    }
}

class DialetusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialetusTheme {
                val navigator = remember {
                    mutableStateOf(Navigator(Screen.Regions, onBackPressedDispatcher))
                }

                Providers(
                    NavigatorAmbient provides navigator.value
                ) {
                    when (val screen = navigator.value.currentScreen) {
                        is Screen.Regions -> RegionScreen()
                        is Screen.Dialects -> DialectsScreen(screen.region)
                    }
                }
            }
        }
    }
}

@Composable
fun RegionScreen() {
    LazyColumnFor(
        items = regionsItems,
        modifier = Modifier.background(Color.LightGray.copy(alpha = .1f))
    ) { item ->
        DialectRegion(region = item)
        if (item != regionsItems.last())
            Divider()
    }
}

@Composable
fun DialectsScreen(region: Region) {
    Scaffold(
        topBar = {
            AppTop(region)
        },
        bodyContent = {
            AppContent()
        },
        bottomBar = {
            AppBottom()
        }
    )
}

@Composable
fun AppTop(region: Region) {
    val navigator = NavigatorAmbient.current

    Column {
        TopAppBar(
            navigationIcon = {
                Icon(
                    Icons.Filled.ArrowBack,
                    Modifier.clickable(onClick = { navigator.back() })
                )
            },
            title = { Text(text = region.name) },
            backgroundColor = Color.White,
            elevation = 0.dp
        )

        SearchField(
            onValueChange = { value ->
                // TODO filter dialects
            }
        )

        AppDivider()
    }
}

@Composable
fun AppContent() {
    LazyColumnFor(
        items = dialectsItems,
        modifier = Modifier.background(Color.LightGray.copy(alpha = .1f))
    ) { item ->
        Spacer(modifier = Modifier.height(8.dp))
        DialectCard(item)
    }
}

@Composable
fun DialectRegion(region: Region) {
    val navigator = NavigatorAmbient.current

    Text(
        text = region.name,
        color = Color.Black,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigator.navigate(Screen.Dialects(region)) })
            .padding(16.dp)
    )
}

@Composable
fun DialectCard(dialect: Dialect) {
    Card(
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = dialect.dialect,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
            DialectSection(title = "Significado", items = dialect.meanings)
            DialectSection(title = "Exemplos", items = dialect.examples)

            AppDivider()

            Icon(
                asset = Icons.Filled.Share,
                tint = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DialectSection(title: String, items: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title.toUpperCase(),
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        items.forEach { example ->
            Text(
                text = example,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AppDivider() {
    Divider(
        color = Color.LightGray.copy(alpha = .4f),
        thickness = 1.dp,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun AppBottom() {
    // TODO
}

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//    DialectsScreen()
// }
