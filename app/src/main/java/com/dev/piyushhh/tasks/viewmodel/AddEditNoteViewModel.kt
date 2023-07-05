package com.dev.piyushhh.tasks.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.piyushhh.tasks.model.InvalidNoteException
import com.dev.piyushhh.tasks.model.Note
import com.dev.piyushhh.tasks.notes.event.AddEditEvent
import com.dev.piyushhh.tasks.use_case.model.NoteUseCases
import com.dev.piyushhh.tasks.use_case.util.NoteTextStateField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel()   {
    private val _noteTitle = mutableStateOf(NoteTextStateField(
        hint = "Enter title ..."
    ))
    val noteTitle:State<NoteTextStateField> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextStateField(
        hint = "Enter some content ..."
    ))
    val noteContent:State<NoteTextStateField> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }
    fun onEvent(event: AddEditEvent){
        when(event){
           is AddEditEvent.EnteredTitle -> {
               _noteTitle.value = noteTitle.value.copy(
                   text = event.value
               )
           }
            is AddEditEvent.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditEvent.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditEvent.SaveNote -> {
                viewModelScope.launch {
                    try{
                        noteUseCases.addNotes(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch(e : InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't be save note"
                            )
                        )
                    }
                }
            }
        }
    }
    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }


}