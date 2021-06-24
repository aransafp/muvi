package com.aransafp.muvi.ui.home.series

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aransafp.muvi.databinding.FragmentSeriesBinding
import com.aransafp.muvi.ui.adapter.PagedFilmAdapter
import com.aransafp.muvi.ui.detail.DetailActivity
import com.aransafp.muvi.utils.Const
import com.aransafp.muvi.viewmodel.ViewModelFactory
import com.aransafp.muvi.vo.Status

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private lateinit var viewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            this,
            factory
        )[SeriesViewModel::class.java]

        val seriesAdapter = PagedFilmAdapter()

        viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
            when (tvShows.status) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    hideLoading()
                    seriesAdapter.submitList(tvShows.data)
                }
                Status.ERROR -> hideLoading()
            }
        })

        with(binding.rvSeries) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = seriesAdapter
        }

        seriesAdapter.setOnItemClickCallback(object : PagedFilmAdapter.OnItemClickCallback {
            override fun onItem(filmId: Int?) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FILM_TYPE, Const.TYPE_TV_SHOW)
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