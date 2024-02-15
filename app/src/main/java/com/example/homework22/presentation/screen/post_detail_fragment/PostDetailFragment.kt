package com.example.homework22.presentation.screen.post_detail_fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework22.databinding.FragmentPostDetailBinding
import com.example.homework22.presentation.base.BaseFragment
import com.example.homework22.presentation.event.post_event.PostDetailEvent
import com.example.homework22.presentation.extension.convertEpochDateToRegularDate
import com.example.homework22.presentation.extension.loadImage
import com.example.homework22.presentation.extension.showSnackBar
import com.example.homework22.presentation.state.post_detail.PostDetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val viewModel: PostDetailViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(PostDetailEvent.FetchPostDetail(id))
    }

    override fun bindViewActionListeners() {
        binding.apply {
            backBtn.setOnClickListener {
                viewModel.onEvent(event = PostDetailEvent.ItemClick)
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postDetailState.collect {
                    handlePostDetailState(it)
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

    private fun handlePostDetailState(state: PostDetailState) {
        binding.progess.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.postDetail?.let {
            binding.apply {
                firstName.text = state.postDetail.owner.firstName
                lastName.text = state.postDetail.owner.lastName
                date.convertEpochDateToRegularDate(state.postDetail.owner.postDate.toLong())
                description.text = state.postDetail.title

                state.postDetail.images.let { images ->
                    if (images.isNotEmpty()) {
                        firstImage.loadImage(images[0])
                    }
                    if (images.size > 1) {
                        secondImage.loadImage(images[1])
                    }
                    if (images.size > 2) {
                        thirdImage.loadImage(images[2])
                    }
                }

                commentCount.text = state.postDetail.comments.toString()
                likeCount.text = state.postDetail.likes.toString()
                shareText.text = state.postDetail.shareContent
            }
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(PostDetailEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: PostDetailViewModel.PostFragmentUiEvent) {
        when (event) {
            is PostDetailViewModel.PostFragmentUiEvent.NavigateToHomeFragment -> {
                findNavController().popBackStack()
            }
        }
    }
}