package com.example.shultetable.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.RecordModel
import com.example.shultetable.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val recordRepository: RecordRepository): ViewModel() {
    val records = MutableLiveData<List<RecordModel>>()

    fun getAllRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            records.postValue(recordRepository.getAllRecords())
        }
    }

}