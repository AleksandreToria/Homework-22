package com.example.homework22.presentation.screen.home_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework22.databinding.HostRecyclerLayoutBinding
import com.example.homework22.presentation.model.post.Posts
import com.example.homework22.presentation.model.story.Stories
import com.google.android.datatransport.runtime.logging.Logging.d

class HomeAdapter(
    private var stories: List<Stories>,
    private var posts: List<Posts>,
//    private val onPostClick: (Posts) -> Unit // Assuming you have a click listener for posts
) : RecyclerView.Adapter<HomeAdapter.HostViewHolder>() {

    companion object {
        private const val VIEW_TYPE_STORIES = 0
        private const val VIEW_TYPE_POSTS = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_STORIES
            else -> VIEW_TYPE_POSTS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostViewHolder {
        val binding =
            HostRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HostViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_STORIES -> holder.bindStories(stories)
            VIEW_TYPE_POSTS -> holder.bindPosts(posts)
//            VIEW_TYPE_POSTS -> holder.bindPosts(posts, onPostClick)
        }
    }

    override fun getItemCount(): Int = 2

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newStories: List<Stories>, newPosts: List<Posts>) {
        this.stories = newStories
        this.posts = newPosts
        notifyDataSetChanged()
    }

    inner class HostViewHolder(private val binding: HostRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindStories(stories: List<Stories>) {
            val storyAdapter = StoriesRecyclerAdapter()
            storyAdapter.submitList(stories)
            binding.storiesRecyclerView.apply {
                adapter = storyAdapter
            }
        }

        fun bindPosts(posts: List<Posts>) {
            val postAdapter = PostsRecyclerAdapter()
            postAdapter.submitList(posts)
            binding.postRecyclerView.apply {
                adapter = postAdapter
            }
        }
    }
}
