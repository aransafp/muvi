package com.aransafp.muvi.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aransafp.muvi.databinding.FragmentFavoriteBinding
import com.aransafp.muvi.ui.adapter.PagedFilmAdapter
import com.aransafp.muvi.ui.detail.DetailActivity
import com.aransafp.muvi.utils.Const
import com.aransafp.muvi.viewmodel.ViewModelFactory


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: PagedFilmAdapter

    companion object {
        const val EXTRA_TYPE_FILM = "extra_type_film"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val type = arguments?.getString(EXTRA_TYPE_FILM)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        favoriteAdapter = PagedFilmAdapter()

        val dataFilm =
            if (type == Const.TYPE_MOVIE) viewModel.getListFavoriteMovies() else viewModel.getListFavoriteTvShows()

        dataFilm.observe(viewLifecycleOwner, { films ->
            if (films.isEmpty()) {
                showMessage()
            } else {
                hideMessage()
                favoriteAdapter.submitList(films)
            }
        })

        initRecyclerView()

        favoriteAdapter.setOnItemClickCallback(object : PagedFilmAdapter.OnItemClickCallback {
            override fun onItem(filmId: Int?) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FILM_TYPE, type)
                intent.putExtra(DetailActivity.EXTRA_ID, filmId)
                startActivity(intent)
            }

        })

    }

    private fun initRecyclerView() {
        with(binding.rvFilms) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun hideMessage() {
        binding.tvMessage.visibility = View.GONE
    }

    private fun showMessage() {
        binding.tvMessage.visibility = View.VISIBLE
    }

}