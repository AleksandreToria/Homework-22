package com.example.homework22.presentation.screen.home_fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework22.databinding.FragmentHomeBinding
import com.example.homework22.presentation.base.BaseFragment
import com.example.homework22.presentation.event.home.HomeEvent
import com.example.homework22.presentation.extension.showSnackBar
import com.example.homework22.presentation.state.home.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var storyAdapter: StoriesRecyclerAdapter
    private lateinit var postAdapter: PostsRecyclerAdapter

    override fun bind() {
        storyAdapter = StoriesRecyclerAdapter()
        postAdapter = PostsRecyclerAdapter()

        binding.apply {
            storiesRecyclerView.adapter = storyAdapter
            postRecyclerView.adapter = postAdapter
        }

        viewModel.onEvent(HomeEvent.FetchStories)
        viewModel.onEvent(HomeEvent.FetchPosts)
    }

    override fun bindViewActionListeners() {
        postAdapter.setOnItemClickListener {
            viewModel.onEvent(HomeEvent.ItemClick(it.id))
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collect {
                    handleHomeState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleHomeState(state: HomeState) {
        binding.progress.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.stories?.let {
            storyAdapter.submitList(it)
        }

        state.posts?.let {
            postAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(HomeEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: HomeFragmentViewModel.HomeFragmentUiEvent) {
        when(event) {
            is HomeFragmentViewModel.HomeFragmentUiEvent.NavigateToPostDetail -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPostDetailFragment(
                        id = event.id
                    )
                )
            }
        }
    }
}