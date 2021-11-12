package com.ofBusiness.chatlog.service.impl;

import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.model.entity.ChatLog;
import com.ofBusiness.chatlog.service.ChatLogDaoService;
import com.ofBusiness.chatlog.service.ChatLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatLogServiceImpl implements ChatLogService {

    @Autowired
    private ChatLogDaoService daoService;

    @Override
    public String saveChatLog(String user, ChatLogRequestDto chatLogRequestDto) {
        return daoService.save(user, chatLogRequestDto);
    }

    @Override
    public Page<ChatLog> getChatLogs(String userId, Pageable pageable) {
        return daoService.getChatLogs(userId, pageable);
    }


    @Override
    public void deleteAllChatLogs(String userId) {
        daoService.deleteAllChats(userId);
    }

    @Override
    public void deleteByMessageId(String messageId) {
        daoService.deletedChatLogByMessageId(messageId);
    }
}
