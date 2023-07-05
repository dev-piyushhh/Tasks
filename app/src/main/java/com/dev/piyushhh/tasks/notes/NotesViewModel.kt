package com.dev.piyushhh.tasks.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.notes.event.NoteEvents
import com.dev.piyushhh.tasks.notes.states.NoteStates
import com.dev.piyushhh.tasks.use_case.model.NoteUseCases
import com.dev.piyushhh.tasks.use_case.util.NoteOrder
import com.dev.piyushhh.tasks.use_case.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteStates())
    val states : State<NoteStates> = _state

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob : Job? = null

    init {
        getNotes(NoteOrder.Date(orderType = OrderType.Descending))
    }

    fun onEvent(noteEvents: NoteEvents){
        when(noteEvents){
            is NoteEvents.Order -> {
                if(states.value.order == noteEvents.noteOrder &&
                    states.value.order.orderType == noteEvents.noteOrder.orderType){
                    return
                     }
                getNotes(noteEvents.noteOrder)
            }
            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotes(noteEvents.note)
                    recentlyDeletedNote = noteEvents.note
                }
            }
            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNotes(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvents.ToggleOrderSection -> {
                _state.value = states.value.copy(
                    isOrderSectionVisible = !states.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach {
                notes ->
                _state.value = states.value.copy(
                    notes = notes,
                    order = noteOrder
                )
            }
            .launchIn(
                viewModelScope
            )
    }
}