package com.aransafp.muvi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aransafp.muvi.utils.TMDBConst

@Entity(tableName = "details")
data class DetailEntity(
    @PrimaryKey
    val id: Int,

    val posterPath: String? = null,

    val title: String? = null,

    val overview: String? = null,

    val releaseDate: String? = null,

    val voteAverage: Double? = 0.0,

    val genres: String? = null,

    val filmType: String = TMDBConst.TYPE_MOVIE
)