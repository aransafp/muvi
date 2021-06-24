package com.aransafp.muvi.utils

import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.data.source.remote.response.*

object DataDummy {

    fun generateListMovie(): List<FilmEntity> {
        val movies = ArrayList<FilmEntity>()

        movies.add(
            FilmEntity(
                337404,
                "Cruella",
                "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
                "2021-05-26",
                8.7
            )
        )

        movies.add(
            FilmEntity(
                503736,
                "Army of the Dead",
                "/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
                "2021-05-14",
                6.6
            )
        )

        return movies
    }


    fun generateDetailMovie(): DetailEntity {

        return DetailEntity(
            337404,
            "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
            "Cruella",
            "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
            "2021-05-26",
            8.7,
            "Comedy, Crime"
        )
    }

    fun generateListTvSeries(): List<FilmEntity> {
        val tvSeries = ArrayList<FilmEntity>()

        tvSeries.add(
            FilmEntity(
                63174,
                "Lucifer",
                "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                "2016-01-25",
                8.5,
            )
        )

        tvSeries.add(
            FilmEntity(
                60735,
                "The Flash",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "2014-10-07",
                7.7
            )
        )

        return tvSeries
    }

    fun generateDetailTvSeries(): DetailEntity {

        return DetailEntity(
            63174,
            "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
            "Lucifer",
            "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
            "2016-01-25",
            8.5,
            "Crime, Sci-Fi, & Fantasy"
        )

    }

    fun generateMoviesResponse(): PopularMovieResponse {

        return PopularMovieResponse(
            listOf(
                MovieResponse(
                    337404,
                    "Cruella",
                    "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
                    "2021-05-26",
                    8.7
                ),
                MovieResponse(
                    503736,
                    "Army of the Dead",
                    "/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
                    "2021-05-14",
                    6.6
                )
            )
        )
    }

    fun generateDetailMovieResponse(): DetailResponse {
        return DetailResponse(
            "Cruella",
            337404,
            "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
            "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
            "2021-05-26",
            listOf(
                GenresItem("Comedy"),
                GenresItem("Crime"),
            ),
            8.7
        )

    }

    fun generateTvSeriesResponse(): PopularTvResponse {

        return PopularTvResponse(
            listOf(
                TvResponse(
                    63174,
                    "Lucifer",
                    "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                    "2016-01-25",
                    8.5,
                ),
                TvResponse(
                    60735,
                    "The Flash",
                    "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                    "2014-10-07",
                    7.7
                )
            )
        )

    }

    fun generateDetailTvSeriesResponse(): DetailResponse {
        return DetailResponse(
            "Lucifer",
            63174,
            "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
            "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
            "2016-01-25",
            listOf(
                GenresItem("Crime"),
                GenresItem("Sci-Fi"),
                GenresItem("Fantasy"),
            ),
            8.5,

            )
    }
}