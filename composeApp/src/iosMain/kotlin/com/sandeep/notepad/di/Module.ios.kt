package com.sandeep.notepad.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sandeep.notepad.db.NotepadDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(NotepadDatabase.Schema, "notepad.sq")
    }
}