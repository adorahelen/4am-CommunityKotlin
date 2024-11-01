package com.example.community_4am_kotlin.feature.friend.service

import com.example.community_4am_kotlin.domain.friend.FriendStatus
import com.example.community_4am_kotlin.domain.user.User
import com.example.community_4am_kotlin.domain.user.enums.UserStatus
import com.example.community_4am_kotlin.feature.friend.repository.FriendRepository
import com.example.community_4am_kotlin.feature.notification.repository.NotificationRepository
import com.example.community_4am_kotlin.feature.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FriendService (
    val friendRepository: FriendRepository,
    val notificationRepository: NotificationRepository,
    val userRepository : UserRepository

){
    @Transactional
    fun acceptFriendRequest(notificationId: Long) {
        val notification = notificationRepository.findById(notificationId)
            .orElseThrow { IllegalArgumentException("해당 알림을 찾을 수 없습니다.") }

        notification.isRead = true // 읽음 처리
        notificationRepository.save(notification)

        friendRepository.updateFriendStatus(
            userId = notification.user?.id, // sender의 ID를 사용하여 상태 업데이트
            friendId = notification.targetId, // recipient의 ID를 사용하여 상태 업데이트
            status = FriendStatus.ACCEPTED
        )
    }

    // 친구 요청 거절 및 삭제 처리
    @Transactional
    fun rejectFriendRequest(notificationId: Long) {
        val notification = notificationRepository.findById(notificationId)
            .orElseThrow { IllegalArgumentException("해당 알림을 찾을 수 없습니다.") }

        notification.isRead = true // 읽음 처리
        notificationRepository.save(notification)

        // 친구 관계 삭제
        friendRepository.deleteFriend(
            userId = notification.user?.id, // sender의 ID 사용
            friendId = notification.targetId // recipient의 ID 사용
        )
    }

    // 정렬된 친구 목록 가져오기 (온라인 상태인 친구가 우선)
    fun getSortedFriendList(userId: Long?): List<User> {
        return friendRepository.findFriendsByUserId(userId)
    }
 fun deleteFriend(userId:String,friendId: Long){

         val recipientUser = userRepository.findByEmail(userId)
             .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }

         val friendRequest = friendRepository.findById(friendId)
             .orElseThrow { IllegalArgumentException("친구 요청을 찾을 수 없습니다.") }

         // 요청 받은 사람과 로그인한 사용자가 일치하는지 확인합니다.
         if (friendRequest.friend.email == recipientUser.email) {
             friendRepository.delete(friendRequest)
         }
         // 거절된 친구 요청을 삭제합니다.

 }
    fun getAcceptedFriendEmails(user: User): List<String> {
        return friendRepository.findAcceptedFriendEmailsByUser(user)
    }
}