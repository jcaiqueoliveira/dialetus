package com.jcaique.dialetus.presentation

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(onValueChange: (String) -> Unit) {
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
