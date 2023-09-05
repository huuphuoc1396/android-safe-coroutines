package com.example.androidsafecoroutines.data.repository

import com.example.androidsafecoroutines.data.remote.ApiService
import com.example.androidsafecoroutines.data.remote.error.RemoteFailureHandler
import com.example.androidsafecoroutines.data.remote.response.search.toRepo
import com.example.androidsafecoroutines.ext.defaultEmpty
import com.example.androidsafecoroutines.model.Repo
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper
import com.example.androidsafecoroutines.safecoroutines.functional.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteFailureHandler: RemoteFailureHandler,
) : RepoRepository {

    override fun searchRepos(query: String): Flow<ResultWrapper<List<Repo>>> {
        return apiService.searchRepos(query).map { response ->
            response.items?.map { item -> item.toRepo() }.defaultEmpty()
        }.asResult(remoteFailureHandler)
    }
}