package com.aransafp.muvi.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aransafp.muvi.R
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.databinding.ActivityDetailBinding
import com.aransafp.muvi.utils.Const
import com.aransafp.muvi.viewmodel.ViewModelFactory
import com.aransafp.muvi.vo.Status
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var detailEntity: DetailEntity

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_FILM_TYPE = "extra_film_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filmType = intent.getStringExtra(EXTRA_FILM_TYPE)
        val filmId = intent.getIntExtra(EXTRA_ID, 0)
        var isFavorite = false

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        val detailData = when (filmType) {
            Const.TYPE_MOVIE -> viewModel.getDetailsMovie(filmId)
            else -> viewModel.getDetailsTvShow(filmId)
        }

        detailData.observe(this, { detail ->
            when (detail.status) {
                Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (detail.data != null) {
                        populateFilm(detail.data)
                        detailEntity = detail.data
                    }
                }
                Status.ERROR -> binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.isFilmFavorite(filmId).observe(this, {
            isFavorite = it
            val icon =
                if (it) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_unfavorite_grey_24px

            binding.btnFavorite.setImageResource(icon)
        })

        binding.btnFavorite.setOnClickListener {
            val film: FilmEntity
            if (filmType != null) {
                film = toFilm(detailEntity, filmType)
                if (isFavorite) {
                    viewModel.deleteFromFavorite(film)
                    Toast.makeText(this, "Successfully removed to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addToFavorite(film)
                    Toast.makeText(this, "Successfully added to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun populateFilm(film: DetailEntity?) {

        with(binding) {
            if (film != null) {
                tvTitle.text = film.title
                tvYear.text = film.releaseDate
                tvOverview.text = film.overview
                tvGenres.text = film.genres

                //rating
                tvRating.text = film.voteAverage.toString()
                val rating = film.voteAverage?.toFloat()?.div(2)
                if (rating != null) {
                    rtRating.rating = rating
                }

                Glide.with(this@DetailActivity)
                    .load("${Const.BASE_IMAGE_URL}${film.posterPath}")
                    .into(imgPoster)

            }
        }
    }

    private fun toFilm(detailEntity: DetailEntity, movieType: String): FilmEntity {
        return FilmEntity(
            detailEntity.id,
            detailEntity.overview,
            detailEntity.title,
            detailEntity.posterPath,
            detailEntity.voteAverage,
            movieType
        )
    }
}