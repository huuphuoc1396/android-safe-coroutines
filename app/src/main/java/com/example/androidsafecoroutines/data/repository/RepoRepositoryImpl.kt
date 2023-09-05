package com.example.androidsafecoroutines.data.repository

import com.example.androidsafecoroutines.data.remote.ApiService
import com.example.androidsafecoroutines.data.remote.error.RemoteFailureHandler
import com.example.androidsafecoroutines.data.remote.response.search.toRepo
import com.example.androidsafecoroutines.ext.defaultEmpty
import com.example.androidsafecoroutines.model.Repo
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper
import com.example.androidsafecoroutines.safecoroutines.functional.safeSuspend
import com.example.androidsafecoroutines.safecoroutines.functional.wrapSuccess
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteFailureHandler: RemoteFailureHandler,
) : RepoRepository {

    override suspend fun searchRepos(query: String): ResultWrapper<List<Repo>> {
        return safeSuspend(remoteFailureHandler) {
            val searchRepoResponse = apiService.searchRepos(query)
            val repoList = searchRepoResponse.items?.map { item -> item.toRepo() }
            repoList.defaultEmpty().wrapSuccess()
        }
    }
}