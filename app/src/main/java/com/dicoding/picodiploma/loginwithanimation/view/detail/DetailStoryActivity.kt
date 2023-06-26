package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.model.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailTourism =
            IntentCompat.getParcelableExtra(intent, EXTRA_STORY, ListStoryItem::class.java)
        Glide.with(this)
            .load(detailTourism?.photoUrl)
            .into(binding.itemAvatar)
        binding.itemName.text = detailTourism?.name
        binding.itemDescription.text = detailTourism?.description
    }


    companion object {
        const val EXTRA_STORY = "extra_data"
    }
}