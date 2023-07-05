package com.dev.piyushhh.tasks.notes.event

import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.use_case.util.NoteOrder

sealed class NoteEvents {
    data class Order(val noteOrder: NoteOrder) : NoteEvents()

    data class DeleteNote(val note: Note) : NoteEvents()

    object RestoreNote : NoteEvents()

    object ToggleOrderSection : NoteEvents()
}