package com.sandeep.notepad.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import kotlinx.coroutines.launch

@Composable
fun NoteListItem(
    title: String,
    summary: String,
    date: String,
    snackBarHostState: SnackbarHostState,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navigator = rememberWebViewNavigator()
    val jsBridge = rememberWebViewJsBridge(navigator)
    LaunchedEffect(jsBridge) {
        jsBridge.register(object : IJsMessageHandler {
            override fun methodName(): String = "showInfo"

            override fun handle(
                message: JsMessage,
                navigator: WebViewNavigator?,
                callback: (String) -> Unit
            ) {
                println("📩 Received from JS: ${message.params}")
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message.params,
                        duration = SnackbarDuration.Short
                    )
                }
                callback("ok")
            }
        })
    }
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 4.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(4.dp))
        WebView(
            state = rememberWebViewStateWithHTMLData(summary),
            navigator = navigator,
            webViewJsBridge = jsBridge
        )
        Spacer(Modifier.height(2.dp))
        Text(date, style = MaterialTheme.typography.labelSmall)
    }
}