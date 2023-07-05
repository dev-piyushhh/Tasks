package com.dev.piyushhh.tasks.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.piyushhh.tasks.data_source.NoteDao
import com.dev.piyushhh.tasks.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao : NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}