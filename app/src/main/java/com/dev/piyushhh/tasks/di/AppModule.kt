package com.dev.piyushhh.tasks.di

import android.app.Application
import androidx.room.Room
import com.dev.piyushhh.tasks.data_source.db.NoteDatabase
import com.dev.piyushhh.tasks.notes.restore.AddNotes
import com.dev.piyushhh.tasks.repository.NoteRepository
import com.dev.piyushhh.tasks.repository.NoteRepositoryImpl
import com.dev.piyushhh.tasks.use_case.DeleteNotes
import com.dev.piyushhh.tasks.use_case.GetNote
import com.dev.piyushhh.tasks.use_case.GetNotes
import com.dev.piyushhh.tasks.use_case.model.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(noteDatabase: NoteDatabase) : NoteRepository{
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun providesNoeUseCases(noteRepository: NoteRepository) : NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNotes = DeleteNotes(noteRepository),
            addNotes = AddNotes(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}