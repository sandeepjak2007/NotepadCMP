package com.sandeep.notepad.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(val route: String) {
    @Serializable
    object NoteList : NavigationRoute("noteList")

    @Serializable
    object PdfView : NavigationRoute("pdfView")
}


val bottomNavList = listOf(NavigationRoute.NoteList, NavigationRoute.PdfView)