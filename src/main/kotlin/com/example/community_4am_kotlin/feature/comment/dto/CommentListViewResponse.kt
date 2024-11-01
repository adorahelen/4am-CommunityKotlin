package com.example.community_4am_kotlin.feature.comment.dto

import com.example.community_4am_kotlin.domain.article.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


data class CommentListViewResponse (
    val commentId: Long?,
    val commentAuthor: String?,
    val commentContent: String?,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    val commentCreatedAt: LocalDateTime?,
    val articleId: Long,
    val parentCommentId: Long?
){
    constructor(comment: Comment): this(
        commentId=comment.commentId,
        commentAuthor=comment.getCommentAuthors(),
        commentContent=comment.getCommentContents(),
        commentCreatedAt=comment.getCreatedDates(),
        articleId=comment.getArticles().id!!,
        parentCommentId=comment.getParentComments()?.commentId
    )
}