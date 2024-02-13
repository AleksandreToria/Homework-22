package com.example.homework22.data.model.post

import com.squareup.moshi.Json

data class Owner(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "post_date")
    val postDate: Int?,
    val profile: String?
)