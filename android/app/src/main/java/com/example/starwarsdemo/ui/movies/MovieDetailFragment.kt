package com.example.starwarsdemo.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarsdemo.databinding.FragmentMovieDetailBinding
import com.example.starwarsdemo.network.model.Movie

class MovieDetailFragment : Fragment() {
    companion object {
        const val movieArgument = "MOVIE_NAV_ARGUMENT"
        const val titleArgument = "TITLE_NAV_AGRUMENT"
    }

    private var movie: Movie? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable(movieArgument) as Movie?
            title = it.getString(titleArgument)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieDetailBinding.inflate(inflater)

        binding.movie = movie

        return binding.root
    }
}