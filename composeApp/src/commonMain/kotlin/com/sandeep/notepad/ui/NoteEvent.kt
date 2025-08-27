package com.sandeep.notepad.ui

sealed class NoteEvent {
    object NoteAdded : NoteEvent()
    object NoteDeleted : NoteEvent()
    object NoteUpdated : NoteEvent()
    data class NoteError(val message: String) : NoteEvent()
}