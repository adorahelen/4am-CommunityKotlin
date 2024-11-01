package com.example.community_4am_kotlin.feature.comment.dto

import com.example.community_4am_kotlin.domain.article.Article
import com.example.community_4am_kotlin.domain.article.Comment

data class AddCommentRequest(
    val commentContent:String,
    val parentConmmentId:Long?=null
){
    fun toEntity(commentAuthor:String, article: Article, parentComment: Comment):Comment{
        return Comment(
            commentAuthor = commentAuthor,
            commentContent = commentContent,
            article = article,
            parentComment = parentComment
        )
    }
}