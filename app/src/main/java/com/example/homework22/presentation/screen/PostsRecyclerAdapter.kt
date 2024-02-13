package com.example.homework22.presentation.screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework22.databinding.PostsLayoutBinding
import com.example.homework22.presentation.extension.convertEpochDateToRegularDate
import com.example.homework22.presentation.extension.loadImage
import com.example.homework22.presentation.model.post.Posts

class PostsRecyclerAdapter :
    ListAdapter<Posts, PostsRecyclerAdapter.PostsViewHolder>(PostsDiffUtil()) {

    inner class PostsViewHolder(private val binding: PostsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Posts

        @SuppressLint("SetTextI18n")
        fun bind() {
            model = currentList[adapterPosition]

            binding.apply {
                ivAvatar.loadImage(model.owner.profile)
                tvName.text = model.owner.firstName
                tvLastName.text = model.owner.lastName
                tvDate.convertEpochDateToRegularDate(model.owner.postDate.toLong())
                tvDescription.text = model.title
                tvLikes.text = model.likes.toString()
                tvShare.text = model.shareContent
                tvComments.text = "${model.comments} comments"

                model.images.let { images ->
                    if (images.isNotEmpty()) {
                        ivImagePrimary.loadImage(images[0])
                    }
                    if (images.size > 1) {
                        ivImageSecond.loadImage(images[1])
                    }
                    if (images.size > 2) {
                        ivImageThird.loadImage(images[2])
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        PostsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind()
    }

    class PostsDiffUtil : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }
}