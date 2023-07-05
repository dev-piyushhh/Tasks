package com.dev.piyushhh.tasks.use_case

import com.dev.piyushhh.tasks.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id:Int) : com.dev.piyushhh.tasks.model.Note? {
        return  repository.getNoteById(id)
    }
}