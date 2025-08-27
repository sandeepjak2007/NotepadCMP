package com.sandeep.notepad.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandeep.notepad.db.Notepad

@Composable
fun NoteListScreen(
    noteList: List<Notepad>,
    onNoteClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (noteList.isEmpty()) {
            Text(
                "No notes available",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            return@Column
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 28.dp)
        ) {
            items(noteList) {
                NoteListItem(
                    title = it.title,
                    summary = it.bodyHtml,
                    date = it.createdDate,
                    onClick = { onNoteClick(it.id) }
                )
                HorizontalDivider()
            }
        }
    }
}