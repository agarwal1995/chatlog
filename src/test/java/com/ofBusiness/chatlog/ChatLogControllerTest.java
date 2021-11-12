package com.ofBusiness.chatlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ofBusiness.chatlog.controller.ChatLogController;
import com.ofBusiness.chatlog.exceptions.NotFoundException;
import com.ofBusiness.chatlog.model.dto.ChatLogRequestDto;
import com.ofBusiness.chatlog.service.ChatLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.Instant;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@EnableSpringDataWebSupport
public class ChatLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChatLogService chatLogService;

    @InjectMocks
    private ChatLogController chatLogController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String VALID_MESSAGE_ID = "validMessageId";
    private final String INVALID_MESSAGE_ID = "invalidMessageId";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatLogController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();

        Mockito.doNothing().when(chatLogService).deleteByMessageId(VALID_MESSAGE_ID);
        Mockito.doThrow(NotFoundException.class).when(chatLogService).deleteByMessageId(INVALID_MESSAGE_ID);
    }

    @Test
    public void test_create() throws Exception {
        ChatLogRequestDto chatLogRequestDto = new ChatLogRequestDto();
        chatLogRequestDto.setMessage("message");
        chatLogRequestDto.setTimestamp(Instant.now());
        chatLogRequestDto.setSent(true);

        String userId = "2332123212uadas";
        mockMvc.perform(post("/chatlogs/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(chatLogRequestDto))
        ).andExpect(status().isOk());
        Mockito.verify(chatLogService, Mockito.times(1)).saveChatLog(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void test_deleteByMessageId() throws Exception {
        mockMvc.perform(delete("/chatlogs/{userId}/{messageId}", "user", VALID_MESSAGE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void test_deleteByMessageIdWithException() throws Exception {
        mockMvc.perform(delete("/chatlogs/{userId}/{messageId}", "user", INVALID_MESSAGE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
