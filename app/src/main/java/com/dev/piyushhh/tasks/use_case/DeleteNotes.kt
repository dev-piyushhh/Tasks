package com.dev.piyushhh.tasks.use_case

import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.repository.NoteRepository

class DeleteNotes(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke (note: Note){
        noteRepository.deleteNote(note)
    }
}