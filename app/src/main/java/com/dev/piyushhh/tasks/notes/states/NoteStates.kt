package com.dev.piyushhh.tasks.notes.states

import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.use_case.util.NoteOrder
import com.dev.piyushhh.tasks.use_case.util.OrderType

data class NoteStates(
    val notes : List<Note> = emptyList(),
    val order: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false
)
