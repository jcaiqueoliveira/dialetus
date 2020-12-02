package com.jcaique.dialetus.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.NavigatorAmbient
import com.jcaique.dialetus.presentation.StringsAmbient
import com.jcaique.dialetus.presentation.ui.DialetusDivider
import com.jcaique.dialetus.presentation.ui.DialetusSearchField
import com.jcaique.dialetus.presentation.ui.DialetusTopBar

@Composable
fun DialectsScreen(region: Region, dialects: List<Dialect>) {
    Scaffold(
        topBar = { Header(region) },
        bodyContent = { Content(dialects) }
    )
}

@Composable
private fun Header(region: Region) {
    val navigator = NavigatorAmbient.current

    Column {
        DialetusTopBar(
            title = region.name,
            icon = Icons.Filled.ArrowBack,
            onNavigationClicked = { navigator.back() }
        )

        DialetusSearchField(
            onValueChange = { value ->
                // TODO filter dialects
            }
        )

        DialetusDivider()
    }
}

@Composable
private fun Content(dialects: List<Dialect>) {
    LazyColumnFor(
        items = dialects,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = .1f))
    ) { item ->
        Spacer(modifier = Modifier.height(8.dp))
        DialectCard(item)
    }
}

@Composable
private fun DialectCard(dialect: Dialect) {
    val strings = StringsAmbient.current

    Card(
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = dialect.dialect,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
            DialectCardSection(title = strings.meaning, items = dialect.meanings)
            DialectCardSection(title = strings.examples, items = dialect.examples)

            DialetusDivider()

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
private fun DialectCardSection(title: String, items: List<String>) {
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
