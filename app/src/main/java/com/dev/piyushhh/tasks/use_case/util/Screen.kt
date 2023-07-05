package com.dev.piyushhh.tasks.use_case.util

sealed class Screen(
    val route : String
) {

    object NoteScreen : Screen("notes_screen")
    object AddNoteScreen:Screen("add_note_screen")
}