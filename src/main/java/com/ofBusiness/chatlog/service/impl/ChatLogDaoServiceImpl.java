package com.ofBusiness.chatlog.service.impl;

import com.ofBusiness.chatlog.exceptions.NotFoundException;
import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.model.entity.ChatLog;
import com.ofBusiness.chatlog.model.repository.ChatLogRepository;
import com.ofBusiness.chatlog.service.ChatLogDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ChatLogDaoServiceImpl implements ChatLogDaoService {

    @Autowired
    private ChatLogRepository repository;

    /**
     * convert the chatlogRequestDto to the chat and save to db
     * @param userId
     * @param chatLogRequestDto
     * @return
     */
    @Override
    public String save(String userId, ChatLogRequestDto chatLogRequestDto) {
        ChatLog chatLog = convert(chatLogRequestDto, userId);
        chatLog = repository.save(chatLog);
        return chatLog.getMessageId();
    }

    @Override
    public Page<ChatLog> getChatLogs(String userId, Pageable pageable) {
        return repository.getByUserIdOrderByTimestampDesc(userId, pageable);
    }

    /**
     * delete all the chat for a given user
     */
    @Override
    public void deleteAllChats(String user) {
        repository.deleteChatLogForUser(user);
    }

    /**
     * Fetch the chatlog by the messageId
     * if not present throw NOT_FOUND_EXCEPTION
     * else soft delete the entry with the given messageId
     * @param messageId
     */
    @Override
    public void deletedChatLogByMessageId(String messageId) {
        Optional<ChatLog> optionalChatLog = repository.getByMessageId(messageId);
        if (!optionalChatLog.isPresent()) {
            throw new NotFoundException("messageId");
        }
        ChatLog chatLog = optionalChatLog.get();
        chatLog.setDeleted(true);
        repository.save(chatLog);
    }

    private ChatLog convert(ChatLogRequestDto chatLogRequestDto, String userId) {
        ChatLog chatLog = new ChatLog();
        chatLog.setMessage(chatLogRequestDto.getMessage());
        chatLog.setTimestamp(chatLogRequestDto.getTimestamp());
        chatLog.setSendByUser(chatLogRequestDto.isSent());
        chatLog.setMessageId(userId + UUID.randomUUID());
        chatLog.setUserId(userId);
        return chatLog;
    }
}
