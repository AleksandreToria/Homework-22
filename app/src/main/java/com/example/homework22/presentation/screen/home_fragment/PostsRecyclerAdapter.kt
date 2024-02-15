package com.example.homework22.presentation.screen.home_fragment

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

    private var onItemClick: ((Posts) -> Unit)? = null

    fun setOnItemClickListener(listener: (Posts) -> Unit) {
        this.onItemClick = listener
    }

    inner class PostsViewHolder(private val binding: PostsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Posts

        @SuppressLint("SetTextI18n")
        fun bind() {
            model = currentList[adapterPosition]

            binding.apply {
                ivAvatar.loadImage(model.owner.profile)
                firstName.text = model.owner.firstName
                tvLastName.text = model.owner.lastName
                date.convertEpochDateToRegularDate(model.owner.postDate.toLong())
                description.text = model.title
                likeCount.text = model.likes.toString()
                shareText.text = model.shareContent
                commentCount.text = "${model.comments} comments"

                model.images.let { images ->
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

                root.setOnClickListener {
                    onItemClick?.invoke(model)
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