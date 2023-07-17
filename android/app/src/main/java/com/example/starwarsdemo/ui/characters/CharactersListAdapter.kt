package com.example.starwarsdemo.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsdemo.R
import com.example.starwarsdemo.databinding.CharacterListItemBinding
import com.example.starwarsdemo.network.model.Character

class CharactersListAdapter: ListAdapter<Character, CharactersListAdapter.CharacterListItemViewHolder>(DiffCallback) {
    class CharacterListItemViewHolder(private var binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.characterImageUrl = character.imageUrl
            binding.executePendingBindings()
        }

        val characterPhoto = binding.characterItem
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        return CharacterListItemViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        val characterItem = getItem(position)
        val transitionName = "Transition: ${characterItem.name}"

        holder.characterPhoto.transitionName = transitionName

        holder.characterPhoto.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(CharacterDetailFragment.characterNameArgument, characterItem.name)

            val extras = FragmentNavigatorExtras(holder.characterPhoto to transitionName)

            it.findNavController().navigate(R.id.action_navigation_characters_to_characterDetailFragment, bundle, null, extras)
        }

        holder.bind(characterItem)
    }

}