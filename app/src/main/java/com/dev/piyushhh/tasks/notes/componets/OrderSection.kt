package com.dev.piyushhh.tasks.notes.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.piyushhh.tasks.use_case.util.NoteOrder
import com.dev.piyushhh.tasks.use_case.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    orderChange : (NoteOrder) -> Unit
){
    Column(
        modifier = Modifier
    ){
        Row {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelected = {
                    orderChange(
                        NoteOrder.Title(
                            noteOrder.orderType
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelected = {
                    orderChange(
                        NoteOrder.Date(
                            noteOrder.orderType
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelected = {
                    orderChange(
                        NoteOrder.Color(
                            noteOrder.orderType
                        )
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier=Modifier.fillMaxSize()
        ){
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelected = {
                    orderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelected = {
                    orderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
        }
    }
}