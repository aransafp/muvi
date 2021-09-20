package com.aransafp.muvi.ui.home.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aransafp.muvi.databinding.FragmentMoviesBinding
import com.aransafp.muvi.ui.adapter.PagedFilmAdapter
import com.aransafp.muvi.ui.detail.DetailActivity
import com.aransafp.muvi.utils.TMDBConst
import com.aransafp.muvi.viewmodel.ViewModelFactory
import com.aransafp.muvi.vo.Status

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            this,
            factory
        )[MoviesViewModel::class.java]

        val moviesAdapter = PagedFilmAdapter()

        viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
            when (movies.status) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    hideLoading()
                    moviesAdapter.submitList(movies.data)
                }
                Status.ERROR -> hideLoading()
            }
        })

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        moviesAdapter.setOnItemClickCallback(object : PagedFilmAdapter.OnItemClickCallback {
            override fun onItem(filmId: Int?) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FILM_TYPE, TMDBConst.TYPE_MOVIE)
                intent.putExtra(DetailActivity.EXTRA_ID, filmId)
                startActivity(intent)
            }

        })

    }

    private fun showLoading() {
        binding.shimmerLayout.startShimmerAnimation()
        binding.shimmerLayout.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
    }
}