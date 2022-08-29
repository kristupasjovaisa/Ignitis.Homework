package ignitis_homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ignitis_homework.dto.*;
import ignitis_homework.services.ChatService;
import ignitis_homework.services.MessageService;
import ignitis_homework.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApiController.class)
class UserApiControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ChatService chatService;
    @MockBean
    private MessageService messageService;
    @InjectMocks
    UserApiController userApiController;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllUsers() throws Exception {
        var users = List.of(UserResponse.builder().build());
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(users)));
    }

    @Test
    void getUser() throws Exception {
        var response = UserResponse.builder().id(1l).build();
        when(userService.getUser(1l)).thenReturn(Optional.of(response));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void getChats() throws Exception {
        var chats = List.of(ChatResponse.builder().id(1l).build());
        when((chatService.getChats(1l))).thenReturn(chats);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/chats", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(chats)));
    }

    @Test
    void addChat() throws Exception {
        var response = ChatResponse.builder().id(1l).build();
        var addChat = AddChatRequest.builder().build();
        when(chatService.addChat(addChat, 1l)).thenReturn(Optional.of(response));
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/chats", 1)
                        .content(asJsonString(addChat))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void getAllMessages() throws Exception {
        var response = MessageResponse.builder().id(1l).build();
        var messages = List.of(response);
        when(messageService.getAllMessages(1l, 2l)).thenReturn(messages);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/chats/{chatId}/messages", 1, 2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(messages)));
    }

    @Test
    void addMessage() throws Exception {
        var request = AddMessageRequest.builder().text("text").build();
        var response = MessageResponse.builder().id(1l).build();
        when(messageService.addMessage(request, 1l, 2l))
                .thenReturn(Optional.of(response));
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/chats/{chatId}/messages", 1, 2)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}