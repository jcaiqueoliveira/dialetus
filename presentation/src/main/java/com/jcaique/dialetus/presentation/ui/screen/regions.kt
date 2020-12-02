package com.jcaique.dialetus.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.NavigatorAmbient
import com.jcaique.dialetus.presentation.R
import com.jcaique.dialetus.presentation.Screen.Dialects
import com.jcaique.dialetus.presentation.StringsAmbient
import com.jcaique.dialetus.presentation.ui.DialetusTopBar

@Composable
fun RegionsScreen(regions: List<Region>) {
    Scaffold(
        topBar = { Header() },
        bodyContent = { Content(regions) }
    )
}

@Composable
fun Header() {
    DialetusTopBar(
        title = StringsAmbient.current.appName,
        icon = vectorResource(id = R.drawable.ic_logo)
    )
}

@Composable
private fun Content(regions: List<Region>) {
    LazyColumnFor(
        items = regions,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = .1f))
    ) { region ->
        Region(region)

        if (region != regions.last()) {
            Divider()
        }
    }
}

@Composable
private fun Region(region: Region) {
    val navigator = NavigatorAmbient.current

    Text(
        text = region.name,
        textAlign = TextAlign.Justify,
        color = Color.Black,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigator.navigate(Dialects(region)) })
            .padding(16.dp)
    )
}
