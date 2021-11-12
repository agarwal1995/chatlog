package com.ofBusiness.chatlog.controller;

import com.ofBusiness.chatlog.exceptions.NotFoundException;
import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.service.ChatLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/chatlogs")
public class ChatLogController {

    @Autowired
    private ChatLogService chatLogService;

    @PostMapping("{userId}")
    public ResponseEntity<Object> create(@PathVariable("userId") String userId,
                                         @RequestBody ChatLogRequestDto requestDto) {
        log.info("create chatLog called :: user : {}", userId);
        return ResponseEntity.ok().body(chatLogService.saveChatLog(userId, requestDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> fetch(@PathVariable("userId") String userId,
                                        Pageable pageable) {
        return ResponseEntity.ok(chatLogService.getChatLogs(userId, pageable));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteAllChats(@PathVariable("userId") String userId) {
        try {
            chatLogService.deleteAllChatLogs(userId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userId}/{messageId}")
    public ResponseEntity<Object> delete(@PathVariable("userId") String userId,
                                         @PathVariable("messageId") String messageId) {
        try {
            chatLogService.deleteByMessageId(messageId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
