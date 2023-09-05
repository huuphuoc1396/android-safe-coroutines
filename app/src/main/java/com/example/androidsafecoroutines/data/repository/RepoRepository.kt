package com.example.androidsafecoroutines.data.repository

import com.example.androidsafecoroutines.model.Repo
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper

interface RepoRepository {
    suspend fun searchRepos(query: String): ResultWrapper<List<Repo>>
}