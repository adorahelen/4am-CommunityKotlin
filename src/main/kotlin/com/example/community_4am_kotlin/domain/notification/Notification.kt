package com.example.community_4am_kotlin.domain.notification

import com.example.community_4am_kotlin.domain.user.User
import com.example.community_4am_kotlin.feature.notification.AlarmType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "notification")
@EntityListeners(AuditingEntityListener::class)
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Enumerated(EnumType.STRING)
    var alarmType: AlarmType,
    @CreatedDate
    var createdAt: LocalDateTime?=null,
    var isRead: Boolean,
    var message:String,
    var recipient:String?=null,
    var targetId:Long?=null,
    var makeId:String,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.MERGE)])
    @JoinColumn(name="user_id",nullable=false)
    var user: User?=null
)
{
    fun changeisRead(isRead: Boolean) {this.isRead=isRead}
}
