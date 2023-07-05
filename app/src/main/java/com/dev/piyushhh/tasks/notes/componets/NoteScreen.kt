package com.dev.piyushhh.tasks.notes.componets

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dev.piyushhh.tasks.notes.NotesViewModel
import com.dev.piyushhh.tasks.notes.event.NoteEvents
import com.dev.piyushhh.tasks.use_case.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){

    val state = viewModel.states.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) {}
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddNoteScreen.route)
            },
                containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier =  Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Your Note", style = MaterialTheme.typography.headlineMedium)
                IconButton(onClick = {
                    viewModel.onEvent(NoteEvents.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                }
            }
        }
        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()) {
            OrderSection(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
                noteOrder = state.order,orderChange = {
                    viewModel.onEvent(NoteEvents.Order(it))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.notes) {
                note ->
                NoteItem(note = note,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(Screen.AddNoteScreen.route +
                            "?noteId=${note.id}&noteColor=${note.color}")
                        },
                    onDeleteClick = {
                        viewModel.onEvent(NoteEvents.DeleteNote(note))
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Your note deleted",
                                actionLabel = "Undo"
                            )

                            if(result == SnackbarResult.ActionPerformed){
                                viewModel.onEvent(NoteEvents.RestoreNote)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}