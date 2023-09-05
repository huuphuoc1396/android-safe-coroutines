package com.example.androidsafecoroutines.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsafecoroutines.data.repository.RepoRepository
import com.example.androidsafecoroutines.model.Repo
import com.example.androidsafecoroutines.safecoroutines.failure.Failure
import com.example.androidsafecoroutines.safecoroutines.functional.onError
import com.example.androidsafecoroutines.safecoroutines.functional.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    val repoListLiveData = MutableLiveData<List<Repo>>()
    val failureLiveData = MutableLiveData<Failure>()

    init {
        fetchRepoList()
    }

    private fun fetchRepoList() {
        viewModelScope.launch {
            repoRepository.searchRepos("Android")
                .onSuccess { repoList ->
                    repoListLiveData.value = repoList
                }
                .onError { failure ->
                    failureLiveData.value = failure
                }
        }
    }
}