package com.ofBusiness.chatlog.model.repository;

import com.ofBusiness.chatlog.model.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    Page<ChatLog> getByUserIdOrderByTimestampDesc(String userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update ChatLog cl set cl.deleted = true where cl.userId = :userId")
    int deleteChatLogForUser(@Param("userId") String userId);

    Optional<ChatLog> getByMessageId(String messageId);
}
