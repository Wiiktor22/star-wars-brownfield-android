package com.example.starwarsdemo.ui.characters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsdemo.MainActivity
import com.example.starwarsdemo.databinding.FragmentCharactersBinding

private class CharactersListOnScrollListener(
    val viewModel: CharactersViewModel,
    val context: Context?
    ) : RecyclerView.OnScrollListener() {
    var indexOfLastFetchedItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (recyclerView.layoutManager as GridLayoutManager).let { layoutManager ->
            viewModel.people.value?.let { characters ->
                val numberOfItems = characters.size
                val currentPosition = layoutManager.findLastCompletelyVisibleItemPosition() + 1

                if(!recyclerView.canScrollVertically(1) && currentPosition == numberOfItems && dy > 0 && indexOfLastFetchedItem != currentPosition){
                    println("The end.... $currentPosition")
                    indexOfLastFetchedItem = currentPosition
                    fetchMoreData()
                }
            }
        }

        super.onScrolled(recyclerView, dx, dy)
    }

    private fun fetchMoreData() {
        viewModel.currentIndex.value?.let {currentIndex ->
            if (currentIndex >= viewModel.maxIndex) {
                context?.let {
                    Toast.makeText(it, "You have fetched all characters", Toast.LENGTH_LONG).show()
                }
            } else {
                viewModel.fetchNextPageData()
            }
        }

    }
}

class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedCharactersView = (activity as MainActivity).charactersView

        if (savedCharactersView == null) {
            val binding = FragmentCharactersBinding.inflate(inflater)

            binding.charactersList.adapter = CharactersListAdapter()

            binding.charactersList.addOnScrollListener(CharactersListOnScrollListener(viewModel, context))

            binding.viewModel = viewModel

            binding.lifecycleOwner = this

            (activity as MainActivity).charactersView = binding.root

            return binding.root
        }

        // NOTE: Read from the activity
        return savedCharactersView
    }

}