package com.aransafp.muvi.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aransafp.muvi.utils.TMDBConst

class FavoritesPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = FavoriteFragment()
        val type = when (position) {
            0 -> TMDBConst.TYPE_MOVIE
            1 -> TMDBConst.TYPE_TV_SHOW
            else -> "Fragment not found"
        }
        fragment.arguments = Bundle().apply {
            putString(FavoriteFragment.EXTRA_TYPE_FILM, type)
        }
        return fragment
    }

}