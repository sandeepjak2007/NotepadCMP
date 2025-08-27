package com.sandeep.notepad

import androidx.compose.ui.window.ComposeUIViewController
import com.sandeep.notepad.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}