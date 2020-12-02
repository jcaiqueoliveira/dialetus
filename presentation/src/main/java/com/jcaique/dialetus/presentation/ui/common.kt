package com.jcaique.dialetus.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp

@Composable
fun DialetusTopBar(
  title: String,
  icon: VectorAsset,
  onNavigationClicked: (() -> Unit)? = null
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                asset = icon,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = { onNavigationClicked?.invoke() })
            )
        },
        title = {
            Text(text = title)
        },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
fun DialetusSearchField(onValueChange: (String) -> Unit) {
    val query = remember { mutableStateOf("") }

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
                asset = Outlined.Search,
                tint = Color.Gray
            )
        },
        onValueChange = {
            query.value = it
            onValueChange(it)
        },
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
        backgroundColor = Color.LightGray.copy(alpha = .2f),
        activeColor = Color.Transparent
    )
}

@Composable
fun DialetusDivider() {
    Divider(
        color = Color.LightGray.copy(alpha = .4f),
        thickness = 1.dp,
        modifier = Modifier.padding(top = 16.dp)
    )
}
