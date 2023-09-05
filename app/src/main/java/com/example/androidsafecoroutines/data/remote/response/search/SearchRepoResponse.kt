package com.example.androidsafecoroutines.data.remote.response.search


import com.google.gson.annotations.SerializedName

data class SearchRepoResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @SerializedName("items")
    val items: List<ItemResponse>? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
)