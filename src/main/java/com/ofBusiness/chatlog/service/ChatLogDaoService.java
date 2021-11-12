package com.ofBusiness.chatlog.service;

import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.model.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatLogDaoService {

    String save(String userId, ChatLogRequestDto chatLogRequestDto);

    Page<ChatLog> getChatLogs(String userId, Pageable pageable);

    void deleteAllChats(String user);

    void deletedChatLogByMessageId(String messageId);
    
}
