package com.example.homework22.presentation.screen.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework22.databinding.StoriesLayoutBinding
import com.example.homework22.presentation.extension.loadImage
import com.example.homework22.presentation.model.story.Stories

class StoriesRecyclerAdapter :
    ListAdapter<Stories, StoriesRecyclerAdapter.StoriesViewHolder>(StoriesDiffUtil()) {

    inner class StoriesViewHolder(private val binding: StoriesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Stories

        fun bind() {
            model = currentList[adapterPosition]

            binding.apply {
                coverImg.loadImage(model.cover)
                titleTv.text = model.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoriesViewHolder(
        StoriesLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind()
    }

    class StoriesDiffUtil : DiffUtil.ItemCallback<Stories>() {
        override fun areItemsTheSame(oldItem: Stories, newItem: Stories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Stories, newItem: Stories): Boolean {
            return oldItem == newItem
        }
    }
}