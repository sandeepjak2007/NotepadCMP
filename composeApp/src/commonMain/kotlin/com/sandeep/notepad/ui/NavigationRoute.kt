package com.sandeep.notepad.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(val route: String) {
    @Serializable
    object NoteList : NavigationRoute("noteList")

    @Serializable
    object PdfView : NavigationRoute("pdfView")

    @Serializable
    data class NoteEdit(val noteId: Long?) : NavigationRoute("note_edit?noteId={noteId}") {
        fun createRoute(noteId: Long?) = "note_edit?noteId=${noteId ?: ""}"
    }
}


val bottomNavList = listOf(NavigationRoute.NoteList, NavigationRoute.PdfView)