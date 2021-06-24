package com.aransafp.muvi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.databinding.ItemsFilmBinding
import com.aransafp.muvi.utils.Const
import com.bumptech.glide.Glide

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFilms = ArrayList<FilmEntity>()

    fun films(films: List<FilmEntity>) {
        this.listFilms.clear()
        this.listFilms.addAll(films)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFilms[position])
    }

    override fun getItemCount(): Int = listFilms.size

    inner class ViewHolder(private val binding: ItemsFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(film: FilmEntity) {

            with(binding) {
                tvTitle.text = film.title
                tvYear.text = film.releaseDate
//                tvDuration.text = film.title

                tvRating.text = film.voteAverage.toString()
                val rating = film.voteAverage?.toFloat()?.div(2)
                if (rating != null) {
                    rtRating.rating = rating
                }

                Glide.with(itemView.context)
                    .load("${Const.BASE_IMAGE_URL}${film.posterPath}")
                    .into(imgPoster)

                itemView.setOnClickListener { onItemClickCallback.onItem(film.id) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItem(filmId: Int?)
    }
}