package com.dev.piyushhh.tasks.notes.event

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent{
    data class EnteredTitle(val value:String) : AddEditEvent()

    data class ChangeFocusTitle(val focusState: FocusState) : AddEditEvent()
    data class EnteredContent(val value:String) : AddEditEvent()
    data class ChangeFocusContent(val focusState: FocusState) : AddEditEvent()
    data class ChangeColor(val color: Int) : AddEditEvent()
    object SaveNote : AddEditEvent()
}
