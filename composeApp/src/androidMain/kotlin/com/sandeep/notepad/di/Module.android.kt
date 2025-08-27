package com.sandeep.notepad.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sandeep.notepad.db.NotepadDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(NotepadDatabase.Schema, get(), "notepad.sq")
    }
}