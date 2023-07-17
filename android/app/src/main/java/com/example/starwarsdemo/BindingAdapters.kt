package com.example.starwarsdemo

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.starwarsdemo.network.model.Character
import com.example.starwarsdemo.network.model.Movie
import com.example.starwarsdemo.ui.characters.CharactersListAdapter
import com.example.starwarsdemo.ui.movies.MoviesListAdapter

@BindingAdapter("moviesListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MoviesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("movieImageUrl")
fun bindImage(imgView: ImageView, movieImageUrl: String?) {
    movieImageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}

@BindingAdapter("charactersListData")
fun bindCharactersRecyclerView(recyclerView: RecyclerView, data: List<Character>?) {
    val adapter = recyclerView.adapter as CharactersListAdapter
    adapter.submitList(data)
}

@BindingAdapter("characterImageUrl")
fun bindCharacterImage(imgView: ImageView, characterImageUrl: String?) {
    characterImageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}