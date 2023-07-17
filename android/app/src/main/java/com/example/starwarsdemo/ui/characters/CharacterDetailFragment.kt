package com.example.starwarsdemo.ui.characters

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionListenerAdapter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import com.example.starwarsdemo.R
import com.example.starwarsdemo.databinding.FragmentCharacterDetailBinding
import com.example.starwarsdemo.network.model.Character
import java.util.concurrent.TimeUnit

private class DefaultContentAnimationListeners(val binding: FragmentCharacterDetailBinding) : AnimationListener {

    override fun onAnimationStart(p0: Animation?) {}

    override fun onAnimationEnd(p0: Animation?) {
        binding.characterDetailName.alpha = 1.0F
        binding.characterDetailDescriptionArea.alpha = 1.0F
    }

    override fun onAnimationRepeat(p0: Animation?) {}
}
private class CharacterPhotoTransitionListeners(
    val binding: FragmentCharacterDetailBinding,
    val defaultTransitionAnimation: Animation
) : TransitionListenerAdapter() {
    override fun onTransitionEnd(p0: Transition?) {
        binding.characterDetailName.startAnimation(defaultTransitionAnimation)
        binding.characterDetailDescriptionArea.startAnimation(defaultTransitionAnimation)
    }
}

class CharacterDetailFragment : Fragment() {
    companion object {
        const val characterNameArgument = "CHARACTER_NAME_NAV_ARGUMENT"
    }

    private val viewModel: CharactersViewModel by activityViewModels()

    private var receivedCharacterName: String? = null
    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receivedCharacterName = it.getString(characterNameArgument)
        }
    }

    private fun setupAnimations(binding: FragmentCharacterDetailBinding, character: Character?) {
        binding.characterItem.transitionName = "Transition: ${character?.name}"

        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.opacity_animation)
        fadeInAnimation.setAnimationListener(DefaultContentAnimationListeners(binding))

        // Shared element animation config
        val animation = TransitionInflater
            .from(requireContext())
            .inflateTransition(android.R.transition.move)
            .addListener(
                CharacterPhotoTransitionListeners(
                    binding,
                    fadeInAnimation
                )
            )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        postponeEnterTransition(100, TimeUnit.MILLISECONDS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCharacterDetailBinding.inflate(inflater)

        character = viewModel.people.value?.find {
            it.name == receivedCharacterName
        }
        binding.character = character

        setupAnimations(binding, character)

        return binding.root
    }

}
