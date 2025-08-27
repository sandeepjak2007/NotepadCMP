package com.sandeep.notepad.di

import com.sandeep.notepad.db.NotepadDatabase
import com.sandeep.notepad.db.NotepadQueries
import com.sandeep.notepad.repo.NotepadRepository
import com.sandeep.notepad.ui.NotepadViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::NotepadViewModel)
    single<NotepadDatabase> {
        NotepadDatabase(get())
    }

    single<NotepadQueries> {
        get<NotepadDatabase>().notepadQueries
    }

    single<NotepadRepository> {
        NotepadRepository(get())
    }
}

expect val platformModule: Module