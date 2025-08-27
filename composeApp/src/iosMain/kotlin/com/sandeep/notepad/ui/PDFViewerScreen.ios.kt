package com.sandeep.notepad.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun PdfScreen(url: String) {
    UIKitView(
        factory = {
            WKWebView().apply {
                loadRequest(NSURLRequest(NSURL(string = url)))
            }
        },
        modifier = Modifier.fillMaxSize(),
    )
}