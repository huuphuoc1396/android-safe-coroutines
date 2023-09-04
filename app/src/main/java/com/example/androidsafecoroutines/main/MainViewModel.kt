package com.example.androidsafecoroutines.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsafecoroutines.model.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val repoListLiveData = MutableLiveData<List<Repo>>()

    init {
        fakeList()
    }

    private fun fakeList() {
        repoListLiveData.value = MutableList(10) { index ->
            Repo(
                id = index.toLong(),
                name = "Name ${index + 1}",
                owner = "Owner ${index + 1}",
                imageUrl = "https://i.imgur.com/6uxVCHq.png",
            )
        }
    }
}