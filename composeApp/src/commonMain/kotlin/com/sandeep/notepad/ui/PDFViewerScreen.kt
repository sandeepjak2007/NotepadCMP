package com.sandeep.notepad.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PdfViewerScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PdfScreen("https://qa.pilloo.ai/GeneratedPDF/Companies/202/2025-2026/DL.pdf")
    }
}

@Composable
expect fun PdfScreen(url: String)