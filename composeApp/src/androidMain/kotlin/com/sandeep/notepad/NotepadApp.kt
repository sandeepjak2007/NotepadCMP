package com.sandeep.notepad

import android.app.Application
import com.sandeep.notepad.di.platformModule
import com.sandeep.notepad.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotepadApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotepadApp)
            modules(platformModule, sharedModule)
        }
    }
}