package com.aransafp.muvi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aransafp.muvi.utils.TMDBConst

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey
    val id: Int,

    val title: String? = null,

    val posterPath: String? = null,

    val releaseDate: String? = null,

    val voteAverage: Double? = null,

    val filmType: String = TMDBConst.TYPE_MOVIE,

    val isFavorite: Boolean? = false

)
