package com.dev.piyushhh.tasks.notes.restore

import com.dev.piyushhh.tasks.model.InvalidNoteException
import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.repository.NoteRepository

class AddNotes(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of note can't be empty !")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of note can't be empty !")
        }
        repository.insertNote(note)
    }
}