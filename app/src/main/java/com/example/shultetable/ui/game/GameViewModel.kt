package com.example.shultetable.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.RecordModel
import com.example.shultetable.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val EASY_ROW_COUNT = 4
const val HARD_ROW_COUNT = 5
const val EXPERT_ROW_COUNT = 6
const val EASY_COLUMN_COUNT = 8
const val HARD_COLUMN_COUNT = 10
const val EXPERT_COLUMN_COUNT = 12

class GameViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    val currentNumber = MutableLiveData<Int>()

    fun checkNumber(number: Int, maxNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (number == currentNumber.value && (number + 1) <= maxNumber) {
                currentNumber.postValue(number + 1)
            }
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            currentNumber.postValue(1)
        }
    }

    fun saveResultTime(model: RecordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.insert(model)
        }
    }

}