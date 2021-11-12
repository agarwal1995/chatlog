package com.ofBusiness.chatlog.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class ChatLogRequestDto {
    private Instant timestamp;
    private String message;
    private boolean isSent;
}
