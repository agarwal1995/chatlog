package com.ofBusiness.chatlog.service;

import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.model.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatLogService {

    String saveChatLog(String user, ChatLogRequestDto chatLogRequestDto);

    Page<ChatLog> getChatLogs(String userId, Pageable pageable);

    void deleteAllChatLogs(String userId);

    void deleteByMessageId(String messageId);

}
