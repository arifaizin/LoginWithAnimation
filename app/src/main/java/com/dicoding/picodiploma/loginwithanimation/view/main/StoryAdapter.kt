package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.model.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemListStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailStoryActivity

class StoryAdapter : ListAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: ListStoryItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(storyItem.photoUrl)
                    .into(binding.itemAvatar)

                itemName.text = storyItem.name
                itemDescription.text = storyItem.description
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_STORY, storyItem)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }
    }
}