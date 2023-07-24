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
        var savedCharactersFragmentBinding = (activity as MainActivity).charactersFragmentBinding

        if (savedCharactersFragmentBinding == null) {
            savedCharactersFragmentBinding = FragmentCharactersBinding.inflate(inflater)

            savedCharactersFragmentBinding.charactersList.adapter = CharactersListAdapter()
        }

        savedCharactersFragmentBinding.charactersList.addOnScrollListener(CharactersListOnScrollListener(viewModel, context))

        savedCharactersFragmentBinding.viewModel = viewModel

        savedCharactersFragmentBinding.lifecycleOwner = this

        (activity as MainActivity).charactersFragmentBinding = savedCharactersFragmentBinding

        return savedCharactersFragmentBinding.root
    }

}