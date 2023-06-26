package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.add.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        setupViewModel()
        setupAction()

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

//        mainViewModel.getStories()
//        mainViewModel.uiState.observe(this) { uiState ->
//            when (uiState) {
//                is StoryUiState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//
//                is StoryUiState.Success -> {
//                    binding.progressBar.visibility = View.GONE
//                    val adapter = StoryAdapter()
//                    adapter.submitData(lifecycle, uiState.data)
//                    binding.rvStory.adapter = adapter
//                }
//
//                is StoryUiState.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    AlertDialog.Builder(this).apply {
//                        setTitle("Oopps!")
//                        setMessage(uiState.errorMessage)
//                        setPositiveButton("Coba Lagi") { _, _ ->
//                        }
//                        create()
//                        show()
//                    }
//                }
//            }
//        }

        mainViewModel.stories.observe(this) {
            val adapter = StoryAdapter()
            adapter.submitData(lifecycle, it)
            binding.rvStory.adapter = adapter
        }
    }

    private fun setupAction() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout_menu -> {
                    mainViewModel.logout()
                    true
                }

                R.id.maps_menu -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }
}