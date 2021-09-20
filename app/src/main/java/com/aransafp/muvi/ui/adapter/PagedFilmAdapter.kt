package com.aransafp.muvi.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.databinding.ItemsFilmBinding
import com.aransafp.muvi.utils.TMDBConst
import com.bumptech.glide.Glide

class PagedFilmAdapter : PagedListAdapter<FilmEntity, PagedFilmAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FilmEntity> =
            object : DiffUtil.ItemCallback<FilmEntity>() {
                override fun areItemsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {
                    return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

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
        val film = getItem(position)
        if (film != null) {
            holder.bind(film)
        }
    }

    inner class ViewHolder(private val binding: ItemsFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(film: FilmEntity) {

            with(binding) {
                tvTitle.text = film.title
                tvYear.text = film.releaseDate
                tvRating.text = film.voteAverage.toString()
                val rating = film.voteAverage?.toFloat()?.div(2)
                if (rating != null) {
                    rtRating.rating = rating
                }

                Glide.with(itemView.context)
                    .load("${TMDBConst.BASE_IMAGE_URL}${film.posterPath}")
                    .into(imgPoster)

                itemView.setOnClickListener { onItemClickCallback.onItem(film.id) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItem(filmId: Int?)
    }
}