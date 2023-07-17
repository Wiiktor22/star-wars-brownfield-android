package com.example.starwarsdemo.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsdemo.R
import com.example.starwarsdemo.databinding.MovieListItemBinding
import com.example.starwarsdemo.network.model.Movie

class MoviesListAdapter: ListAdapter<Movie, MoviesListAdapter.MovieListItemViewHolder>(DiffCallback) {
    class MovieListItemViewHolder(private var binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(Movie: Movie) {
            binding.movie = Movie
            binding.executePendingBindings()
        }

        val cardComponent = binding.card
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.director == newItem.director
                    && oldItem.producer == newItem.producer
                    && oldItem.releaseDate == newItem.releaseDate
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListItemViewHolder {
        return MovieListItemViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListItemViewHolder, position: Int) {
        val movieItem = getItem(position)

        holder.cardComponent.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(MovieDetailFragment.titleArgument, movieItem.title)
            bundle.putSerializable(MovieDetailFragment.movieArgument, movieItem)
            it.findNavController().navigate(R.id.movieDetailFragment, bundle)
        }

        holder.bind(movieItem)
    }
}