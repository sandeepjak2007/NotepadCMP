package com.sandeep.notepad.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.sandeep.notepad.db.Notepad
import com.sandeep.notepad.db.NotepadQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class NotepadRepository(
    private val notepadQueries: NotepadQueries
) {
    fun insertNotepad(title: String, bodyHtml: String, createdDate: String): Result<Unit> =
        runCatching {
            notepadQueries.insertNote(
                title,
                bodyHtml,
                createdDate
            )
        }

    fun getAllNotepads(): Result<Flow<List<Notepad>>> = runCatching {
        notepadQueries.selectAllNotes().asFlow().mapToList(Dispatchers.IO)
    }

    fun notepadById(id: Long): Result<Notepad?> = runCatching {
        notepadQueries.selectNoteById(id).executeAsOneOrNull()
    }

    fun updateNote(id: Long, title: String, bodyHtml: String, createdDate: String): Result<Unit> =
        runCatching {
            notepadQueries.updateNoteById(title, bodyHtml, createdDate, id)
        }

    fun deleteNoteById(id: Long): Result<Unit> = runCatching {
        notepadQueries.deleteNoteById(id)
    }
}