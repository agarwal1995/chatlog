package com.ofBusiness.chatlog.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "chat_log")
@Data
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted = false")
public class ChatLog {

    @JsonIgnore
    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;

    @Column(name = "message_id", unique = true)
    private String messageId;

    private Instant timestamp;
    private String message;
    private String userId;
    private boolean sendByUser;
    private boolean deleted;
}
