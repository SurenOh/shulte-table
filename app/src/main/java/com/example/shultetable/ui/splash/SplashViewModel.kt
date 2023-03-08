package com.example.shultetable.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.firebase.FirebaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val FIRST_LAUNCH = "first_launch"

class SplashViewModel(private val firebase: FirebaseHandler) : ViewModel() {
    val pairInfo = MutableLiveData<Pair<String, Boolean>>()

    fun loadInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            pairInfo.postValue(firebase.getPairInfo())
        }
    }

}