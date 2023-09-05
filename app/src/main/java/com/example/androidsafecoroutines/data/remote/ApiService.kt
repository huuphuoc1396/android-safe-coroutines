package com.example.androidsafecoroutines.data.remote

import com.example.androidsafecoroutines.data.remote.response.search.SearchRepoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories")
   suspend fun searchRepos(@Query("q") query: String): SearchRepoResponse
}