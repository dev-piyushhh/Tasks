package com.dev.piyushhh.tasks.repository

import com.dev.piyushhh.tasks.model.Note

interface NoteRepository {

    fun getNotes() : kotlinx.coroutines.flow.Flow<List<Note>>

    suspend fun getNoteById(id: Int) : Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}