package com.sandeep.notepad.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddEditScreen(
    noteId: Long?,
    viewModel: NotepadViewModel,
    onDone: () -> Unit
) {
    val note by viewModel.currentNote.collectAsState(initial = null)
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val statusEvent by viewModel.status.collectAsState(initial = null)
    val showSuccess = remember { mutableStateOf(false) }
    val showError = remember { mutableStateOf<String?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember {
        mutableStateOf(
            datePickerState.selectedDateMillis?.let { millisToLocalDate(it).formatDate() } ?: ""
        )
    }

    LaunchedEffect(noteId) {
        noteId?.let { viewModel.getNoteById(it) }
    }
    LaunchedEffect(note) {
        note?.let {
            title = it.title
            body = it.bodyHtml
            selectedDate = it.createdDate
        } ?: run {
            title = ""
            body = ""
            selectedDate = ""
        }
    }

    LaunchedEffect(statusEvent) {
        if (statusEvent == null) return@LaunchedEffect
        when (val event = statusEvent) {
            is NoteEvent.NoteAdded,
            is NoteEvent.NoteUpdated -> {
                showSuccess.value = true
                showError.value = null
                delay(1500)
                showSuccess.value = false
                onDone()
            }

            is NoteEvent.NoteError -> {
                showError.value = event.message
                showSuccess.value = false
                delay(3000)
                showError.value = null
            }

            else -> {}
        }
    }

    if (noteId != -1L && note == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {
            OutlinedTextField(
                label = { Text("Title") },
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                label = { Text("Body (HTML)") },
                value = body,
                onValueChange = { body = it },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    label = { Text("Created Date") },
                    value = selectedDate,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Date Picker")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    if (note?.id != null && note?.id != -1L) {
                        viewModel.updateNote(
                            note!!.id,
                            title,
                            body,
                            selectedDate
                        )
                    } else {
                        viewModel.addNote(title, body, selectedDate)
                    }
                },
                enabled = title.isNotBlank() && body.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = barColor,
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
            Spacer(Modifier.height(16.dp))
            AnimatedVisibility(visible = showSuccess.value) {
                Text(
                    "Note saved successfully!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
            }
            AnimatedVisibility(visible = showError.value != null) {
                Text(
                    "Error: ${showError.value}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}