package com.example.androidsafecoroutines.data.repository

import com.example.androidsafecoroutines.model.Repo
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun searchRepos(query: String): Flow<ResultWrapper<List<Repo>>>
}