package com.info_turrim.polandnews.options.domain.model

data class EditProfile(

    val city: String,
    val country: String?,
    val createdAt: String,
    val email: String,
    val gclid: String?,
    val id: Int,
    val password: String,
    val photo: String?,
    val real: Boolean,
    val sex: Int,
    val username: String,
    val yearOfBirth: Int


//    val city: String,
//    val commented_news: List<CommentedNews>,
//    val created_at: String,
//    val email: String,
//    val followers: List<Follower>,
//    val following: List<Following>,
//    val id: Int,
//    val password: String,
//    val photo: String?,
//    val real: Boolean,
//    val username: String
)
