package com.dev.piyushhh.tasks.use_case.model

import com.dev.piyushhh.tasks.notes.restore.AddNotes
import com.dev.piyushhh.tasks.use_case.DeleteNotes
import com.dev.piyushhh.tasks.use_case.GetNote
import com.dev.piyushhh.tasks.use_case.GetNotes

data class NoteUseCases(
    val  getNotes: GetNotes,
    val deleteNotes: DeleteNotes,
    val addNotes: AddNotes,
    val getNote: GetNote
)
