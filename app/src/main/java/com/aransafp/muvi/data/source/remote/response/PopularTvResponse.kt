package com.aransafp.muvi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PopularTvResponse(
    @field:SerializedName("results")
    val results: List<TvResponse>
)