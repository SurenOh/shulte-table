package com.example.shultetable.di

import androidx.room.Room
import com.example.shultetable.App
import com.example.shultetable.database.AppDatabase
import com.example.shultetable.mappers.RecordMapper
import com.example.shultetable.repository.RecordRepository
import com.example.shultetable.repository.RecordRepositoryImpl
import com.example.shultetable.ui.game.GameViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single { androidApplication() as App }

    //Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }
    //Mappers
    single { RecordMapper() }

    //Repositories
    single<RecordRepository> { RecordRepositoryImpl(get(), get()) }


    //ViewModels
    viewModel { GameViewModel(get()) }
}