package com.dev.piyushhh.tasks.use_case

import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.repository.NoteRepository
import com.dev.piyushhh.tasks.use_case.util.NoteOrder
import com.dev.piyushhh.tasks.use_case.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ) : Flow<List<Note>> {
        return repository.getNotes().map {
            notes ->  when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy {
                            it.title.lowercase()
                        }
                        is NoteOrder.Date -> notes.sortedBy {
                            it.timestamp
                        }
                        is NoteOrder.Color -> notes.sortedBy {
                            it.color
                        }
                    }
                }
            is OrderType.Descending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedByDescending {
                        it.title.lowercase()
                    }
                    is NoteOrder.Date -> notes.sortedByDescending {
                        it.timestamp
                    }
                    is NoteOrder.Color -> notes.sortedByDescending {
                        it.color
                    }
                }
            }
            }
        }
    }
}