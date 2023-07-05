package com.dev.piyushhh.tasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.piyushhh.tasks.ui.theme.BabyBlue
import com.dev.piyushhh.tasks.ui.theme.LightGreen
import com.dev.piyushhh.tasks.ui.theme.RedOrange
import com.dev.piyushhh.tasks.ui.theme.RedPink
import com.dev.piyushhh.tasks.ui.theme.Violet

@Entity
data class Note(
    val title:String,
    val content:String,
    val timestamp: Long,
    val color:Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message : String) : Exception(message)
