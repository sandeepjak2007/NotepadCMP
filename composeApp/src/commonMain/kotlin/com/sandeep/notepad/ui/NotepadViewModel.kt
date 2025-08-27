package com.sandeep.notepad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.notepad.db.Notepad
import com.sandeep.notepad.repo.NotepadRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotepadViewModel(
    private val notepadRepository: NotepadRepository
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Notepad>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _status = MutableSharedFlow<NoteEvent>(extraBufferCapacity = 1)
    val status = _status.asSharedFlow()

    private val _currentNote = MutableStateFlow<Notepad?>(null)
    val currentNote = _currentNote.asStateFlow()

    init {
        viewModelScope.launch {
            notepadRepository.getAllNotepads().onSuccess { notesList ->
                notesList.collect {
                    println("Notes fetched successfully $it.")
                    _notes.value = it
                }

            }.onFailure { exception ->
                println("Failed to fetch notes: ${exception.message}")
            }
        }
    }

    fun getNoteById(id: Long) = viewModelScope.launch {
        notepadRepository.notepadById(id).onSuccess {
            _currentNote.value = it
        }.onFailure { e ->
            _status.emit(NoteEvent.NoteError("Failed to fetch note: ${e.message}"))
        }
    }

    fun addNote(title: String, bodyHtml: String, createdDate: String) = viewModelScope.launch {
        notepadRepository.insertNotepad(title, bodyHtml, createdDate).onSuccess {
            _status.emit(NoteEvent.NoteAdded)
        }.onFailure { e ->
            _status.emit(NoteEvent.NoteError("Failed to add note: ${e.message}"))
        }
    }

    fun updateNote(id: Long, title: String, bodyHtml: String, createdDate: String) =
        viewModelScope.launch {
            notepadRepository.updateNote(id, title, bodyHtml, createdDate).onSuccess {
                _status.emit(NoteEvent.NoteUpdated)
            }.onFailure { e ->
                _status.emit(NoteEvent.NoteError("Failed to update note: ${e.message}"))
            }
        }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            notepadRepository.deleteNoteById(id).onSuccess {
                _status.emit(NoteEvent.NoteDeleted)
            }.onFailure { e ->
                _status.emit(NoteEvent.NoteError("Failed to delete note: ${e.message}"))
            }
        }
    }
}