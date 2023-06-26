package com.dicoding.storyapp

import com.dicoding.picodiploma.loginwithanimation.data.model.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.model.StoryResponse

object DataDummy {

    fun successStoryResponse(): StoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id =i.toString(),
                name =  "author + $i",
                description = "description $i",
                photoUrl = "photo",
                createdAt = "createdAt",
                lat = -77.0364,
                lon = 38.8951
            )
            items.add(story)
        }
        return StoryResponse(
            error = false,
            message = "success",
            listStory = items
        )
    }


    fun failedStoryResponse(): StoryResponse{
        val error = true
        val message = "Failed"
        val listStory = emptyList<ListStoryItem>()

        return StoryResponse(listStory, error, message)
    }

}